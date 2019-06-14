public class CustomerSpecialOffer {
    IEmailSender sender;

    CustomerSpecialOffer(IEmailSender emailSender) {
        super();
        sender = emailSender;
    }

    public void emailCustomerSpecialOffer(String email)
	{
		String msg = "Congratulations! Your purchase history has earned you a 10% discount on your next purchase!";
		sender.sendEmail(email, "10% off your next order!", msg);
	}
}