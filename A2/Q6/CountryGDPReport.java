import java.util.ArrayList;

public class CountryGDPReport
{
	ArrayList<ICountry> countries;

	public CountryGDPReport(ICountry canada, ICountry mexico)
	{
		countries = new ArrayList<>();
		countries.add(canada);
		countries.add(mexico);
	}

	public void printCountryGDPReport()
	{
		System.out.println("GDP By Country:\n");
		for (ICountry country : countries) 
		{
			country.displayGDPReport();
		}
	}
}