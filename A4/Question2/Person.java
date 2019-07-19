public class Person
{
	private String name;
	private ContactInfo personContactInfo;
	private LoginInfo personLoginInfo;

	public Person(String name)
	{
		this.name = name;
		this.personContactInfo = new ContactInfo();
		this.personLoginInfo = new LoginInfo();
	}

	public void setAreaCode(String areaCode)
	{
		this.personContactInfo.setAreaCode(areaCode);
	}

	public String getAreaCode()
	{
		return this.personContactInfo.getAreaCode();
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.personContactInfo.setPhoneNumber(phoneNumber);
	}

	public String getPhoneNumber()
	{
		return this.personContactInfo.getPhoneNumber();
	}

	public void setLoginCredentials(String userName, String password)
	{
		this.personLoginInfo.setLoginCredentials(userName, password);
	}

	public boolean authenticateUser()
	{
		return this.personLoginInfo.authenticateUser();
	}
}