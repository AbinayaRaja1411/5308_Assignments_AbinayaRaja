public class ErrorResponse extends Response implements IResponse {
    
    ErrorResponse(int statusCode, String error) {
        super(statusCode);
        response.put("error", error);
    }

    public String errorMessage;
}