public class Mexico implements ICountry
{
	public String getAgriculture()
	{
		return "$50000000 MXN";
	}

	public String getTourism()
	{
		return "$100000 MXN";
	}

	public void displayGDPReport() 
	{
		System.out.println("- Mexico:\n");
		System.out.println("   - Agriculture: " + getAgriculture());
		System.out.println("   - Tourism: " + getTourism());
	}
}