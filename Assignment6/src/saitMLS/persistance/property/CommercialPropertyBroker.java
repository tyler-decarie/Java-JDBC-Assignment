package saitMLS.persistance.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import saitMLS.persistance.Broker;
import saitMLS.problemDomain.property.CommercialProperty;
import utilities.SLL;

public class CommercialPropertyBroker implements Broker, Serializable{
	

private static CommercialPropertyBroker propertyBroker;
	
	private ArrayList<CommercialProperty> propertyList;
	private static String INPUT_FILE = "res/comprop.txt";
	private static String SERIALIZED_FILE = "res/comser.ser";
	private SLL commList;
	private Scanner scan;
	private int id;
	private long nextId;
	
	/**
	 * Singleton constructor for CommercialPropertyBroker
	 */
	private CommercialPropertyBroker() {
		loadCommercialPropertyLinkedList();
		loadSerializedFile();
		
	}
	
	/**
	 * 
	 * @return returns the broker if it does not exist yet
	 */
	public static CommercialPropertyBroker getBroker() { 	
			if (propertyBroker == null)
			propertyBroker = new CommercialPropertyBroker();
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
		for (CommercialProperty cp : propertyList) {
			out.writeObject(cp);
		}
		out.close();
	}

	@Override
	/**
	 * Saves the new or updated information to propertyList
	 */
	public boolean persist(Object o) {
		CommercialProperty cp = (CommercialProperty) o;
		findHighestCurrentId();
		boolean result = false;
		
		CommercialProperty newCp = new CommercialProperty();
		if (cp.getId() == 0) {
			newCp.setId(nextId + 1);
			newCp.setAddress(cp.getAddress());
			newCp.setAskingPrice(cp.getAskingPrice());
			newCp.setComments(cp.getComments());
			newCp.setLegalDescription(cp.getLegalDescription());
			newCp.setNoFloors(cp.getNoFloors());
			newCp.setQuadrant(cp.getQuadrant());
			newCp.setType(cp.getType());
			newCp.setZone(cp.getZone());
			propertyList.add(newCp);
			result = true;
		}
		else {
			for (CommercialProperty array : propertyList) {
				long arrayID = array.getId();
				long refID = cp.getId();
				if (arrayID == refID) {
					array.setAddress(cp.getAddress());
					array.setAskingPrice(cp.getAskingPrice());
					array.setComments(cp.getComments());
					array.setLegalDescription(cp.getLegalDescription());
					array.setNoFloors(cp.getNoFloors());
					array.setQuadrant(cp.getQuadrant());
					array.setType(cp.getType());
					array.setZone(cp.getZone());
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
		CommercialProperty cp = (CommercialProperty) o;
		boolean result = false;
		int index = 0;
		for (CommercialProperty array: propertyList) {
			long arrayID = array.getId();
			long refID = cp.getId();
			if(arrayID == refID) {
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
		CommercialProperty cp = (CommercialProperty) o;
		List resultArray = new ArrayList<>();
		if (cp.getId() != 0L) {
			resultArray = searchId(propertyList, cp.getId());
		}
		else if (cp.getQuadrant() != null) {
			resultArray = searchQuadrant(propertyList, cp.getQuadrant());
		}
		else if (cp.getZone() != null) {
			resultArray = searchZone(propertyList, cp.getZone());
		}
		else if (cp.getAskingPrice() != 0.0D) {
			resultArray = searchAskingPrice(propertyList, cp.getAskingPrice());
		}
		else if (cp.getType() != null) {
			resultArray = searchType(propertyList, cp.getType());
		}
		else if (cp.getNoFloors() != 0L) {
			resultArray = searchNoFloors(propertyList, cp.getNoFloors());
		}
		return resultArray;
	}
	
	private List searchId(ArrayList<CommercialProperty> array, long id) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			long checkID = cp.getId();
			if (checkID == id) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	private List searchQuadrant(ArrayList<CommercialProperty> array, String quadrant) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			String checkQuadrant = cp.getQuadrant();
			if (checkQuadrant.equals(quadrant)) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	private List searchZone(ArrayList<CommercialProperty> array, String zone) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			String checkZone = cp.getZone();
			if (checkZone.equals(zone)) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	private List searchAskingPrice(ArrayList<CommercialProperty> array, double price) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			double checkPrice = cp.getAskingPrice();
			if (checkPrice >= price) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	private List searchType(ArrayList<CommercialProperty> array, String type) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			String checkType = cp.getType();
			if (checkType.equals(type)) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	private List searchNoFloors(ArrayList<CommercialProperty> array, int floors) {
		List resultArray = new ArrayList<>();
		
		for (CommercialProperty cp : array) {
			int checkFloors = cp.getNoFloors();
			if (checkFloors == floors) {
				resultArray.add(cp);
			}
		}
		return resultArray;
	}
	
	/**
	 * writes the information from the text file to SLL, if a ser file does not exist it will create a ser file
	 */
	private void loadCommercialPropertyLinkedList() {
		id = 1;
        commList = new SLL();
        File commercialFile = new File(INPUT_FILE);
        try {
			 scan = new Scanner(commercialFile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        File comser = new File(SERIALIZED_FILE);
       if(!(comser.exists())) {

            while(scan.hasNext()) {
                CommercialProperty cp = new CommercialProperty();

                String[] record = scan.nextLine().split(";");
                cp.setId(id);
                cp.setLegalDescription(record[0]);
                cp.setAddress(record[1]);
                cp.setQuadrant(record[2]);
                cp.setZone(record[3]);
                cp.setAskingPrice(Double.parseDouble(record[4]));
                cp.setComments(record[5]);
                cp.setType(record[6]);
                cp.setNoFloors(Integer.parseInt(record[7]));
                commList.append(cp);
                id++;
            }
                try {
                    FileOutputStream myFile = new FileOutputStream(SERIALIZED_FILE);
                    ObjectOutputStream out = new ObjectOutputStream(myFile);
                    out.writeInt(id);
                    out.writeObject(commList.get());
                    for (int i = 1; i < id-1; i++) {
                    	out.writeObject(commList.get(i));
                    }
                    

                    out.close();
                    myFile.close();
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
		propertyList = new ArrayList<CommercialProperty>();
		try {
			serFile = new FileInputStream(SERIALIZED_FILE);
			ObjectInputStream in = new ObjectInputStream(serFile);
			long count = in.readInt();
			for (int i = 1; i < count; i++) {
				CommercialProperty cp = (CommercialProperty) in.readObject();
				propertyList.add((CommercialProperty) cp);
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
		for (CommercialProperty c : propertyList) {
			if (nextId <= c.getId())
				nextId = c.getId();
		}
	}
}
	


