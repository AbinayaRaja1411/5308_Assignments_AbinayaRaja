import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShipMate implements IShipMate {

    ArrayList<Address> knownAddresses;

    ShipMate() {
        knownAddresses = new ArrayList<Address>();
        Address address = new Address();
        address.customer = "abi";
        address.street = "gerrish street";
        address.city = "halifax";
        address.province = "nova scotia";
        address.country = "canada";
        address.postalCode = "b3k5k3";
        knownAddresses.add(address);
    }

    public boolean isKnownAddress(Address address) {
        boolean isKnown;
        isKnown = address != null && knownAddresses != null && knownAddresses.size() > 0
                && address.customer != null && !address.customer.trim().isEmpty()
                && address.street != null && !address.street.trim().isEmpty()
                && address.city != null && !address.city.trim().isEmpty()
                && address.province != null && !address.province.trim().isEmpty()
                && address.country != null && !address.country.trim().isEmpty()
                && address.postalCode != null && !address.postalCode.trim().isEmpty()
                && isAddressMatchFound(address);
        return isKnown;
    }
	
	public String shipToAddress(Address address, int count, String drugName) throws Exception {
        return "06-10-2019";
    }

    private boolean isAddressMatchFound(Address addr) {
        boolean isMatchFound = false;
        if(knownAddresses != null && knownAddresses.size() > 0) {
            int addressListSize = knownAddresses.size();
            for(int i = 0; i < addressListSize; i++) {
                Address addressAtCurrentIndex = knownAddresses.get(i);
                if(addressAtCurrentIndex.customer.equals(addr.customer) 
                    && addressAtCurrentIndex.street.equals(addr.street)
                    && addressAtCurrentIndex.city.equals(addr.city)
                    && addressAtCurrentIndex.province.equals(addr.province)
                    && addressAtCurrentIndex.country.equals(addr.country)
                    && addressAtCurrentIndex.postalCode.equals(addr.postalCode))
                    {
                        isMatchFound = true;
                        break;
                    }
            }
        }
        return isMatchFound;
    }
}