public class Request {
    public String apikey;
    public String username;
    public RequestAction action;
    public String drug;
    public int quantity;
    public Address address;

    Request(String apikey, String username, String drugname, RequestAction action) {
        this.apikey = apikey;
        this.username = username;
        this.drug = drugname;
        this.action = action;
    }

    Request(String apikey, String username, String drugname, int quantity, RequestAction action, 
            String customer, String street, String city, String province, String country, String postalCode) {
        this(apikey, username, drugname, action);
        this.quantity = quantity;
        this.address = new Address();
        this.address.customer = customer;
        this.address.street = street;
        this.address.city = city;
        this.address.province = province;
        this.address.country = country;
        this.address.postalCode = postalCode;
    }

    Request(String apikey, String username, String drugname, int quantity, RequestAction action) {
        this(apikey, username, drugname, action);
        this.quantity = quantity;
    }
}