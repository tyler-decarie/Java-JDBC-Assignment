package saitMLS.main;

import java.io.IOException;
import java.io.Serializable;

import saitMLS.exceptions.InvalidPhoneNumberException;
import saitMLS.exceptions.InvalidPostalCodeException;
import saitMLS.persistance.property.CommercialPropertyBroker;
import saitMLS.problemDomain.clientale.ClientBroker;

public class AppDriver implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Driver for the SAIT MLS Assignment 6 program
	 * @param args
	 * @throws InvalidPhoneNumberException
	 * @throws InvalidPostalCodeException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InvalidPhoneNumberException, InvalidPostalCodeException, IOException {
		//CommercialPropertyBroker.getBroker();
		
		new MyFrame();
	}

}
