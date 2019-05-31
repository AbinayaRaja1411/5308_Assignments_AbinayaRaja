public class ErrorResponse extends Response
{
    
    ErrorResponse(int statusCode, String error) {
        super(statusCode);
        response.put("error", error);
    }

    public String errorMessage;
}