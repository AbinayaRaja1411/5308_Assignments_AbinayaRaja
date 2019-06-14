public class Canada implements ICountry
{
	public String getAgriculture()
	{
		return "$50000000 CAD";
	}

	public String getManufacturing()
	{
		return "$100000 CAD";
	}

	public void displayGDPReport() {
		System.out.println("- Canada:\n");
		System.out.println("   - Agriculture: " + getAgriculture());
		System.out.println("   - Manufacturing: " + getManufacturing());
	}
}