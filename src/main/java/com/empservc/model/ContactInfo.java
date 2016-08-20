package com.empservc.model;

import java.util.List;

/**
 * Contact details of the person.
 * @author Mardolfh del Rosario
 *
 */
public class ContactInfo {
	private String emailAddress;
	private List<Long> phoneNumbers;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<Long> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<Long> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	@Override
	public String toString() {
		return "ContactInfo [emailAddress=" + emailAddress + ", phoneNumbers=" + phoneNumbers + "]";
	}
	
	
}
