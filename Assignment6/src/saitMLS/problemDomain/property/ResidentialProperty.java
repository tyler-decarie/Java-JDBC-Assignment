package saitMLS.problemDomain.property;

import java.io.Serializable;

import saitMLS.exceptions.property.InvalidLegalDescriptionException;
import saitMLS.exceptions.property.InvalidNumberOfBathroomsException;

public class ResidentialProperty extends Property implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double area;
	private double bathrooms;
	private int bedrooms;
	private char garage;
	
	public ResidentialProperty() {
		
	}
	
	public ResidentialProperty(long id, String legalDescription, String address, String quadrant, String zone,
            double askingPrice, String comments, double area, double bathrooms, int bedrooms, char garage)
     throws InvalidLegalDescriptionException, InvalidNumberOfBathroomsException {
		
		super(id, legalDescription, address, quadrant, zone, askingPrice, comments);
		this.area = area;
		this.bathrooms = bathrooms;
		this.bedrooms = bedrooms;
		this.garage = garage;
		
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getBathrooms() {
		return bathrooms;
	}

	public void setBathrooms(double bathrooms) {
		
		try {
			validateNumberOfBathrooms(bathrooms);
		} catch (InvalidNumberOfBathroomsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getBedrooms() {
		return bedrooms;
	}

	public void setBedrooms(int bedrooms) {
		this.bedrooms = bedrooms;
	}

	public char getGarage() {
		return garage;
	}

	public void setGarage(char garage) {
		this.garage = garage;
	}

	@Override
	public String toString() {
		return "ResidentialProperty [area=" + area + ", bathrooms=" + bathrooms + ", bedrooms=" + bedrooms + ", garage="
				+ garage + ", id=" + id + ", legalDescription=" + legalDescription + ", address=" + address
				+ ", quadrant=" + quadrant + ", zone=" + zone + ", askingPrice=" + askingPrice + ", comments="
				+ comments + "]";
	}
	
	private void validateNumberOfBathrooms(double nob) throws InvalidNumberOfBathroomsException {
		
		Double d = nob;
		String [] splitter = d.toString().split("\\.");
		if (Double.parseDouble(splitter[0]) > 0 && ((Double.parseDouble(splitter[1]) == 0) || Double.parseDouble(splitter[1]) == 5) ){
			this.bathrooms = nob;
		}
		else {
			throw new InvalidNumberOfBathroomsException();
		}
			
		
	}
	
}
