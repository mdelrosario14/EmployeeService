package com.empservc.model;

/**
 * Employee model object.
 * @author Mardolfh del Rosario
 *
 */
public class Employee {
	private long employeeId;
	private PersonalInfo personalInfo;
	private ContactInfo contactInfo;
	private AddressInfo addressInfo;
	
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public AddressInfo getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ",personalInfo=" + personalInfo + ", contactInfo=" +
				contactInfo + ", addressInfo=" + addressInfo + "]";
	}
}
