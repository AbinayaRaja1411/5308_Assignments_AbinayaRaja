public class Student
{
	String bannerID;
	String firstName;
	String lastName;
	String email;

	public Student()
	{
		bannerID = null;
		firstName = null;
		lastName = null;
		email = null;
	}

	public void setBannerID(String bannerID)
	{
		this.bannerID = bannerID;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
}