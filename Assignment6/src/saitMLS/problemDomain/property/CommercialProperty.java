package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;

public class CommercialProperty extends Property implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noFloors;
	private String type;
	
	public CommercialProperty() {
		
	}
	
	public CommercialProperty(long id, String legalDescription, String address, String quadrant, 
			String zone,double askingPrice, String comments, String type, int noFloors) throws InvalidLegalDescriptionException {
		super(id, legalDescription, address, quadrant, zone, askingPrice, comments);
		this.noFloors = noFloors;
		this.type = type;
	}

	public int getNoFloors() {
		return noFloors;
	}

	public void setNoFloors(int noFloors) {
		this.noFloors = noFloors;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CommercialProperty [noFloors=" + noFloors + ", type=" + type + ", id=" + id + ", legalDescription="
				+ legalDescription + ", address=" + address + ", quadrant=" + quadrant + ", zone=" + zone
				+ ", askingPrice=" + askingPrice + ", comments=" + comments + "]";
	}
	
}
