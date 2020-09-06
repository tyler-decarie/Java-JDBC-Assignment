package saitMLS.problemDomain.clientale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import saitMLS.exceptions.InvalidPhoneNumberException;
import saitMLS.exceptions.InvalidPostalCodeException;
import saitMLS.persistance.Broker;


/**
 * ClientBroker handles the Random Access File backend 
 * for the client part of the SAIT MLS GUI Assignment
 * @author 800977
 *
 */
public class ClientBroker implements Broker{
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rst;
	
	private static final String INSERT_STMT = "INSERT INTO JAVACLIENTS (ID,FNAME,LNAME,ADDRESS,POSTAL,PHONE,TYPE) VALUES (?,?,?,?,?,?,?)";
	private static final String SELECT_STMT = "SELECT * FROM JAVACLIENTS";
	private static final String DELETE_STMT = "DELETE FROM JAVACLIENTS";
	private static final String SELECTID_STMT = "SELECT * FROM JAVACLIENTS WHERE ID >= ?";

	private static ClientBroker clientBroker;
	private ArrayList<Client> clientList;
	private long highId;
	private static String INPUT_FILE = "res/clients.txt";
	
	/**
	 * Constructor for ClientBroker, can only be called once
	 * @throws InvalidPhoneNumberException
	 * @throws InvalidPostalCodeException
	 * @throws IOException
	 */
	private ClientBroker() throws InvalidPhoneNumberException, InvalidPostalCodeException, IOException {
		clientList = new ArrayList<>();
		setConnection();
		if (!tableExists()) {
			createTable();
			loadClientDatabase();
		}
		transferData();
	}
	/**
	 * Method to control that only one ClientBroker is made
	 * @return returns the singleton ClientBroker
	 * @throws InvalidPhoneNumberException
	 * @throws InvalidPostalCodeException
	 * @throws IOException
	 */
	public static ClientBroker getBroker() throws InvalidPhoneNumberException, InvalidPostalCodeException, IOException {
		if (clientBroker == null)
			clientBroker = new ClientBroker();
			return clientBroker;
	}
	
	/**
	 * creates connection to database
	 */
	private void setConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:SAIT","CPRG250","password");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","CPRG307","CPRG307");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * checks if table exists or not
	 * @return true - if table exists, false if it does not
	 */
	private boolean tableExists() {
		boolean result = false;
		
		try {
			stmt = conn.createStatement();
			String query = "SELECT * FROM JAVACLIENTS";
			rst = stmt.executeQuery(query);
			result = true;
			
		} catch (SQLException e) {
			
		}
		
		return result;
		
	}
	/**
	 * Creates a Clients table in mySQL, method is only called if table does not exist
	 */
	private void createTable() {
		String query = "CREATE TABLE JAVACLIENTS (ID NUMBER(4), FNAME VARCHAR2(20), LNAME VARCHAR2(20), ADDRESS VARCHAR2(30), POSTAL VARCHAR2(10), PHONE VARCHAR2(15), TYPE CHAR(1))";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Transfers data from the Database "Javaclients" to the arrayList<Client>
	 * @throws IOException
	 * @throws InvalidPhoneNumberException
	 * @throws InvalidPostalCodeException
	 */
	public void transferData() throws IOException, InvalidPhoneNumberException, InvalidPostalCodeException {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SELECT_STMT);
				
				rst = pstmt.executeQuery();
				
				while (rst.next()) {
					
					Client c = new Client(rst.getLong(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7).charAt(0));
					clientList.add(c);
				}
				pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	/**
	 * testing method to check if table was working (NOT USED)
	 * @param id
	 */
	public void printRecords(int id) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(SELECTID_STMT);
			pstmt.setInt(1, id);
			
			rst = pstmt.executeQuery();
			
			while (rst.next()) {
				System.out.println("ID: " + rst.getInt(1) + " Name: " + rst.getString(2));
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	/**
	 * saves changes to the database, this updated database is used on the next open
	 */
	@Override
	public void closeBroker() throws IOException {
		
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	        for(Client c: clientList) {
	        	
	        	insertRecord(c.getClientID(), c.getFirstName(), c.getLastName(), c.getAddress(), c.getPostalCode(), c.getPhoneNumber(), Character.toString(c.getClientType()));
	        }
		
	}
	
	
	/**
	 * makes changes or additions to the arraylist 
	 */
	@Override
	public boolean persist(Object arg0) {
		Client c =  (Client) arg0 ;
		
		findHighestCurrentId();
		
		Client newC = new Client();
		if (c.getClientID() == 0) {
			newC.setActive(true);
			newC.setClientID(highId + 1);
			newC.setFirstName(c.getFirstName());
			newC.setLastName(c.getLastName());
			newC.setAddress(c.getAddress());
			try {
				newC.setPostalCode(c.getPostalCode());
			} catch (InvalidPostalCodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				newC.setPhoneNumber(c.getPhoneNumber());
			} catch (InvalidPhoneNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newC.setClientType(c.getClientType());
			clientList.add(newC);
			return true;
		}
		else {
			for (Client array : clientList) {
				long arrayID = array.getClientID();
	            long refID = c.getClientID();
	            if(arrayID == refID) {
	            	array.setActive(true);
	            	array.setFirstName(c.getFirstName());
	            	array.setLastName(c.getLastName());
	            	array.setAddress(c.getAddress());
	            	try {
						array.setPostalCode(c.getPostalCode());
					} catch (InvalidPostalCodeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	try {
						array.setPhoneNumber(c.getPhoneNumber());
					} catch (InvalidPhoneNumberException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	array.setClientType(c.getClientType());
	            	
	            }
			}
			return true;
		}
	} 
	
	
	/**
	 * removes a client from the last based on ID
	 */
	@Override
	public boolean remove(Object o) {
		Client toRemove = null;
        Client c = (Client) o;
        boolean result = false;
        for(Client array: clientList) {
            long arrayID = array.getClientID();
            long refID = c.getClientID();
            if(arrayID == refID) {
            	toRemove = array;
                

                result = true;
            }
        }
        clientList.remove(toRemove);
        return result;
    }
	
	/**
	 * sets the variable highId by reading through the arraylist and getting the highest id value
	 */
	private void findHighestCurrentId() {
		for (Client c : clientList) {
			if (highId <= c.getClientID())
				highId = c.getClientID();
		}
	}

	/**
	 * method that gets the temporary client and finds the value used to search, 
	 * sends value to secondary method based on value
	 */
	@Override
	public List search(Object o) {
		Client c = (Client) o;
		List resultArray = new ArrayList<>();
		
		if (c.getFirstName() != null) {
			resultArray = searchFirstName(clientList, c.getFirstName());
		} 
		else if (c.getLastName() != null) {
			resultArray = searchLastName(clientList, c.getLastName());
		}
		// https://stackoverflow.com/questions/26638047/how-to-check-a-long-for-null-in-java
		else if (c.getClientID() != 0L) {
			resultArray = searchId(clientList, c.getClientID());
		}
		else if (c.getClientType() != 0) {
			resultArray = searchType(clientList, c.getClientType());
		}
		
		return resultArray;
	}
	
	private List searchFirstName(ArrayList<Client> array, String fname) {
		List resultArray = new ArrayList<>();
		
		for (Client c : array) {
			String firstName = c.getFirstName();
			if(firstName.contains(fname) && c.isActive())
				resultArray.add(c);
		}
		return resultArray;
	}
	
	private List searchLastName(ArrayList<Client> array, String lname) {
		List resultArray = new ArrayList<>();
		
		for (Client c : array) {
			String lastname = c.getLastName();
			if(lastname.contains(lname) && c.isActive())
				resultArray.add(c);
		}
		return resultArray;
	}
	
	private List searchId(ArrayList<Client> array , long id) {
		List resultArray = new ArrayList<>();
		
		for (Client c : array) {
			long clientID = c.getClientID();
			if(clientID == id && c.isActive())
				resultArray.add(c);
		}
		
		return resultArray;
	}
	
	private List searchType(ArrayList<Client> array , char type) {
		List resultArray = new ArrayList<>();
		
		for (Client c : array) {
			char clientType = c.getClientType();
			if (clientType == type && c.isActive())
				resultArray.add(c);
		}
		return resultArray;
		
	}
	/**
	 * loads data from text file to the clients database table
	 * @throws IOException
	 * @throws InvalidPhoneNumberException
	 * @throws InvalidPostalCodeException
	 */
	private void loadClientDatabase() throws IOException, InvalidPhoneNumberException, InvalidPostalCodeException {
		
		File f = new File(INPUT_FILE);
		// CHECKS IF BIN FILE EXISTS, IF IT DOES NOT IT WILL WRITE A BIN FILE
		
		Scanner scan = new Scanner(f);
		
		long ID = 1;
		while (scan.hasNextLine()) {
			
			String[] splitLine = scan.nextLine().split(";");
			//long id,
			//String firstName,
            //String lastName,
            //String address,
            //String postalCode,
            //String phoneNumber,
            //char clientType
			insertRecord(ID, splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4], splitLine[5]);
			
			ID++;
		}
		scan.close();
		}
		
	
		/**
		 * used to insert records into the table
		 * @param id
		 * @param fname
		 * @param lname
		 * @param address
		 * @param postal
		 * @param phone
		 * @param type
		 */
public void insertRecord(long id, String fname, String lname, String address, String postal, String phone, String type) {
	try {
		PreparedStatement pstmt = conn.prepareStatement(INSERT_STMT);
		pstmt.setLong(1,id);
		pstmt.setString(2, fname);
		pstmt.setString(3, lname);
		pstmt.setString(4, address);
		pstmt.setString(5, postal);
		pstmt.setString(6, phone);
		pstmt.setString(7, type);
		
		pstmt.executeUpdate();
		pstmt.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
}