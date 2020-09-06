package saitMLS.problemDomain.clientale;

import saitMLS.exceptions.InvalidPhoneNumberException;
import saitMLS.exceptions.InvalidPostalCodeException;

public class Client {

	private boolean active;
	private String address;
	private long clientID;
	private char clientType;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String postalCode;
	public static final int SIZE = 0;
	
	public Client() {
		
	}
	
	public Client(long id,
            String firstName,
            String lastName,
            String address,
            String postalCode,
            String phoneNumber,
            char clientType) throws InvalidPhoneNumberException,
    InvalidPostalCodeException{
		
		this.active = true;
		this.clientID = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.clientType = clientType;
		
	}
	
	public Client(String line)
		       throws InvalidPhoneNumberException,
		              InvalidPostalCodeException{
		
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public char getClientType() {
		return clientType;
	}

	public void setClientType(char clientType) {
		this.clientType = clientType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
		
		if (validatePhoneNumber(phoneNumber)) {
		this.phoneNumber = phoneNumber;
		}
		else {
			throw new InvalidPhoneNumberException("Invalid Phone Number");
		}
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) throws InvalidPostalCodeException {
		
		if (validatePostalCode(postalCode)) {
		this.postalCode = postalCode;
		}
		else {
			throw new InvalidPostalCodeException("Invalid Postal Code");
		}
	}
	
	public String toString() {
		return getClientID() + " " + getFirstName().trim() + " " + 
				getLastName().trim() + " " + getAddress().trim() + " " + 
				getPostalCode() + " " + getPhoneNumber() + " " + getClientType();
	}
	
	private boolean validatePhoneNumber(String number) {
		
		String phonePattern = "\\d{3}-\\d{3}-\\d{4}";
		return number.matches(phonePattern);
		
	}
	
	private boolean validatePostalCode(String code) {
		
		String postalPattern = "[A-Z][0-9][A-Z] [0-9][A-Z][0-9]";
		return code.matches(postalPattern);
		
	}

}
