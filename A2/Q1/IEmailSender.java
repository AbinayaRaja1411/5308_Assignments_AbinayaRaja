public interface IEmailSender 
{
    void sendEmail(String emailAddress, String subject, String message);
}