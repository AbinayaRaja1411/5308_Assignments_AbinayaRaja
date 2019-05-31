public class Authentication implements IAuthentication {
    public boolean authenticate(String apiKey) {
        boolean isAuthenticated;
        if(apiKey != null && apiKey.length() > 0 && apiKey.toLowerCase().contains("true")) {
            isAuthenticated = true;
        }
        else {
            isAuthenticated = false;
        }
        return isAuthenticated;
    }

	public boolean authorize(String username, RequestAction action) {
        boolean isAuthorized;
        if(action != null && username != null && username.length() > 0 && username.toUpperCase().contains(action.name())) {
            isAuthorized = true;
        }
        else {
            isAuthorized = false;
        }
        return isAuthorized;
    }

}