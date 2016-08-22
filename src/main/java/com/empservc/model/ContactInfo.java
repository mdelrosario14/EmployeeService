package com.empservc.model;

import java.util.List;

/**
 * Contact details of the person.
 * @author Mardolfh del Rosario
 *
 */
public class ContactInfo {
	private List<Long> phoneNumbers;

	public List<Long> getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(List<Long> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	@Override
	public String toString() {
		return "ContactInfo [phoneNumbers=" + phoneNumbers + "]";
	}
	
	
}
