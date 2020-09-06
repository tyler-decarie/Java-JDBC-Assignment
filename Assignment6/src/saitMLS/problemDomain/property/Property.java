package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;

public abstract class Property implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long id;
	String legalDescription;
	String address;
	String quadrant;
	String zone;
	double askingPrice;
	String comments;
	
	public Property() {
		
	}
	
	public Property(long id, String legalDescription, String address, 
			String quadrant, String zone, double askingPrice, String comments) throws InvalidLegalDescriptionException{
		this.id = id;
		this.legalDescription = legalDescription;
		this.address = address;
		this.quadrant = quadrant;
		this.zone = zone;
		this.askingPrice = askingPrice;
		this.comments = comments;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLegalDescription() {
		return legalDescription;
	}

	public void setLegalDescription(String legalDescription) {
		try {
			validateLegalDescription(legalDescription);
		} catch (InvalidLegalDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getQuadrant() {
		return quadrant;
	}

	public void setQuadrant(String quadrant) {
		this.quadrant = quadrant;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public double getAskingPrice() {
		return askingPrice;
	}

	public void setAskingPrice(double askingPrice) {
		this.askingPrice = askingPrice;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Property [id=" + id + ", legalDescription=" + legalDescription + ", address=" + address + ", quadrant="
				+ quadrant + ", zone=" + zone + ", askingPrice=" + askingPrice + ", comments=" + comments + "]";
	}
	
	private void validateLegalDescription(String desc) throws InvalidLegalDescriptionException{
		String pattern = "[0-9]{1,4}[A-Z][0-9]{0,4}[-][0-9]{1,2}";
		if (desc.matches(pattern)) {
			this.legalDescription = desc;
		}
		else 
			throw new InvalidLegalDescriptionException();
	}

}
