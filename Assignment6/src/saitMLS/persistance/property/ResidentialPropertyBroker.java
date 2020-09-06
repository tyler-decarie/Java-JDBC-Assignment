package saitMLS.persistance.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import saitMLS.persistance.Broker;
import saitMLS.problemDomain.property.CommercialProperty;
import saitMLS.problemDomain.property.ResidentialProperty;
import utilities.SLL;

public class ResidentialPropertyBroker implements Broker{
	
	private static ResidentialPropertyBroker propertyBroker;
	
	private ArrayList<ResidentialProperty> propertyList;
	private static String INPUT_FILE = "res/resprop.txt";
	private static String SERIALIZED_FILE = "res/resser.ser";
	private SLL resList;
	private Scanner scan;
	private int id;
	private long nextId;
	
	/**
	 * Singleton constructor for ResidentialPropertyBroker
	 */
	private ResidentialPropertyBroker() {
		loadResidentialPropertyLinkedList();
		loadSerializedFile();
		
	}
	
	/**
	 * 
	 * @return returns the broker if it does not exist yet
	 */
	public static ResidentialPropertyBroker getBroker() { 	
			if (propertyBroker == null)
			propertyBroker = new ResidentialPropertyBroker();
			return propertyBroker;
	}

	@Override
	/**
	 * Closes the broker and saves to the ser file
	 */
	public void closeBroker() throws IOException {
		FileOutputStream myFile = new FileOutputStream(SERIALIZED_FILE);
		ObjectOutputStream out = new ObjectOutputStream(myFile);
		out.writeInt(propertyList.size()+1);
		for (ResidentialProperty rp : propertyList) {
			out.writeObject(rp);
		}
		out.close();
		
	}

	@Override
	/**
	 * Saves the new or updated information to propertyList
	 */
	public boolean persist(Object o) {
		ResidentialProperty rp = (ResidentialProperty) o;
		findHighestCurrentId();
		boolean result = false;
		
		ResidentialProperty newRp = new ResidentialProperty();
		if (rp.getId() == 0) {
			newRp.setId(nextId + 1);
			newRp.setAddress(rp.getAddress());
			newRp.setAskingPrice(rp.getAskingPrice());
			newRp.setComments(rp.getComments());
			newRp.setLegalDescription(rp.getLegalDescription());
			newRp.setArea(rp.getArea());
			newRp.setQuadrant(rp.getQuadrant());
			newRp.setBathrooms(rp.getBathrooms());
			newRp.setBedrooms(rp.getBedrooms());
			newRp.setZone(rp.getZone());
			newRp.setGarage(rp.getGarage());
			propertyList.add(newRp);
			result = true;
		}
		else {
			for (ResidentialProperty array : propertyList) {
				long arrayID = array.getId();
				long refID = rp.getId();
				if (arrayID == refID) {
					array.setAddress(rp.getAddress());
					array.setAskingPrice(rp.getAskingPrice());
					array.setComments(rp.getComments());
					array.setLegalDescription(rp.getLegalDescription());
					array.setArea(rp.getArea());
					array.setQuadrant(rp.getQuadrant());
					array.setBathrooms(rp.getBathrooms());
					array.setBedrooms(rp.getBedrooms());
					array.setZone(rp.getZone());
					array.setGarage(rp.getGarage());
					result = true;
				}
				
			}
		}
		return result;
	}

	@Override
	/**
	 * removes the object from propertyList
	 */
	public boolean remove(Object o) {
		ResidentialProperty rp = (ResidentialProperty) o;
		boolean result = false;
		int index = 0;
		for (ResidentialProperty array: propertyList) {
			long arrayID = array.getId();
			long refID = rp.getId();
			if (arrayID == refID) {
				index = (int) array.getId();
				result = true;
			}
		}
		propertyList.remove(index-1);
		return result;
	}

	@Override
	/**
	 * searches through the propertyList and returns any that match
	 */
	public List search(Object o) {
		ResidentialProperty rp = (ResidentialProperty) o;
		List resultArray = new ArrayList<>();
		
		if (rp.getId() != 0L) {
			resultArray = searchId(propertyList, rp.getId());
		}
		else if (rp.getQuadrant() != null) {
			resultArray = searchQuadrant(propertyList, rp.getQuadrant());
		}
		else if (rp.getZone() != null) {
			resultArray = searchZone(propertyList, rp.getZone());
		}
		else if (rp.getAskingPrice() != 0.0D) {
			resultArray = searchAskingPrice(propertyList, rp.getAskingPrice());
		}
		else if (rp.getBedrooms() != 0L) {
			resultArray = searchBedrooms(propertyList, rp.getBedrooms());
		}
		else if (rp.getGarage() != 0) {
			resultArray = searchGarage(propertyList, rp.getGarage());
		}
		
		return resultArray;
	}
	
	private List searchId(ArrayList<ResidentialProperty> array, long id) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			long checkID = rp.getId();
			if (checkID == id) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	private List searchQuadrant(ArrayList<ResidentialProperty> array, String quadrant) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			String checkQuadrant = rp.getQuadrant();
			if (checkQuadrant.equals(quadrant)) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	private List searchZone(ArrayList<ResidentialProperty> array, String zone) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			String checkZone = rp.getZone();
			if (checkZone.equals(zone)) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	private List searchAskingPrice(ArrayList<ResidentialProperty> array, double price) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			double checkPrice = rp.getAskingPrice();
			if (checkPrice >= price) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	private List searchBedrooms(ArrayList<ResidentialProperty> array, int bedroom) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			long checkBedroom = rp.getBedrooms();
			if (checkBedroom == bedroom) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	private List searchGarage(ArrayList<ResidentialProperty> array, char garage) {
		List resultArray = new ArrayList<>();
		
		for (ResidentialProperty rp : array) {
			char checkGarage = rp.getGarage();
			if (checkGarage == garage) {
				resultArray.add(rp);
			}
		}
		return resultArray;
	}
	
	/**
	 * writes the information from the text file to SLL, if a ser file does not exist it will create a ser file
	 */
	private void loadResidentialPropertyLinkedList() {
		id = 1;
		resList =  new SLL();
		File residentialFile =  new File(INPUT_FILE);
		
		try {
			scan = new Scanner(residentialFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File resser = new File(SERIALIZED_FILE);
		if (!(resser.exists())) {
			
			while(scan.hasNext()) {
				ResidentialProperty rp = new ResidentialProperty();
				
				String[] record = scan.nextLine().split(";");
				rp.setId(id);
				rp.setLegalDescription(record[0]);
				rp.setAddress(record[1]);
				rp.setQuadrant(record[2]);
				rp.setZone(record[3]);
				rp.setAskingPrice(Double.parseDouble(record[4]));
				rp.setComments(record[5]);
				rp.setArea(Double.parseDouble(record[6]));
				rp.setBathrooms(Double.parseDouble(record[7]));
				rp.setBedrooms(Integer.parseInt(record[8]));
				rp.setGarage(record[9].charAt(0));
				resList.append(rp);
				id++;
			}
				try {
					FileOutputStream myFile = new FileOutputStream(SERIALIZED_FILE);
					ObjectOutputStream out = new ObjectOutputStream(myFile);
					out.writeInt(id);
					out.writeObject(resList.get());
					for (int i = 1; i < id-1; i++) {
						out.writeObject(resList.get(i));
					}
					
					out.close();
					myFile.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			scan.close();
		}	
	}
	
	/**
	 * loads info from the ser file to the propertyList
	 */
	private void loadSerializedFile() {
		FileInputStream serFile; 
		propertyList = new ArrayList<ResidentialProperty>();
		try {
			serFile = new FileInputStream(SERIALIZED_FILE);
			ObjectInputStream in = new ObjectInputStream(serFile);
			long count = in.readInt();
			for (int i = 1; i < count; i++) {
				ResidentialProperty rp = (ResidentialProperty) in.readObject();
				propertyList.add((ResidentialProperty) rp);
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * goes through the propertyList and sets highId to the highest ID it can find
	 */
	private void findHighestCurrentId() {
		for (ResidentialProperty c : propertyList) {
			if (nextId <= c.getId())
				nextId = c.getId();
		}
	}
	
}
