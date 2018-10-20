package RESTfulAPITesting.Beans;

import java.util.HashMap;

public class User {
	
	private String name;
	private String username;
	private String email;
	private String phone;
	private String website;
	private HashMap address;
	private HashMap company;
 
	public void setName(String name) {
		this.name=name;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public void setPhone(String phone) {
		this.phone=phone;
	}
	
	public void setWebsite(String website) {
		this.website=website;
	}
	
	public void setAddress(String street, String suite, String city, String zipcode, HashMap geo) {
		address=new HashMap<>();
		address.put("street", street);
		address.put("suite", suite);
		address.put("city", city);
		address.put("zipcode", zipcode);
		address.put("geo", geo);
	}

	
	public void setCompany(String companyname, String catchPhrase, String bs) {
		company=new HashMap<>();
		company.put("name", companyname);
		company.put("catchPhrase", catchPhrase);
		company.put("bs", bs);
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public HashMap getAddress() {
		return address;
	}
	
	public HashMap getCompany() {
		return company;
	}
	
}

