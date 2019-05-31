// YOU ARE ALLOWED TO MODIFY THIS FILE
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class RequestProcessor implements IRequestProcessor
{
	/*
		This is dependency injection. Everything the class and this method needs to do their job is
		passed to it. This allows you to perfectly test every aspect of your class by writing mock
		objects that implement these interfaces such that you can test every possible path through
		your code.
	*/
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database)
	{
		String response = "";
		try
		{
			Request request  = parseJSONString(json);
			Response responseObj = null;
			if(request != null)
			{
			JSONObject outputJSONObj = new JSONObject();
			if(authentication.authenticate(request.apikey)) {
				if(authentication.authorize(request.username, request.action))
				{
					if(database.isDrugExist(request.drug)) {
						if(request.action == RequestAction.QUERY)
						{
							int quantity = database.query(request.drug);
							responseObj = new QuerySuccessResponse(200, quantity);
						}
						else if(request.action == RequestAction.SHIP)
						{
							if(shipMate.isKnownAddress(request.address))
							{
								if(database.claim(request.drug, request.quantity) >= 0)
								{
									String estDeliveryDate = shipMate.shipToAddress(request.address, database.query(request.drug), request.drug);
									responseObj = new ShipSuccessResponse(200, estDeliveryDate);
								}
								else {
									responseObj = new ErrorResponse(500, "Insufficient Stock");
								}
							}
							else
							{
								responseObj = new ErrorResponse(500, "Unknown Address");
							}
						}
					}
					else {
						responseObj = new ErrorResponse(500, "Unknown Drug");
					}
				}
				else
				{
					responseObj = new ErrorResponse(500, "Not Authorized");
				}
			}
			else {
				responseObj = new ErrorResponse(500, "Authentication Failure");
			}
			response = responseObj.getJSONString();
		}
		}
		catch (Exception e)
		{}
		return response;
	}

	/*
		Insert all of your instantiation of mock objects and RequestProcessor(s)
		here. Then insert calls to all of your unit tests for the RequestProcessor
		class.  These tests should send different combinations of JSON strings
		to your class with mock objects such that you test all paths through the
		API.  Write one test function per "path" you are testing.  For example,
		to test authentication you would write two unit tests: authenticateSuccess()
		that passes JSON with a known API key that should be authenticated by your
		mock security object and tests for the correct JSON response from processRequest(),
		and authenticateFailure() that passes JSON with a bad API key that should fail to
		be authenticated by your mock security object and tests for the correct JSON
		response from processRequest().

		The runUnitTests() method will be called by Main.java. It must run your unit tests.
		All of your unit tests should System.out.println() one line indicating pass or
		failure with the following format:
		PASS - <Name of test>
		FAIL - <Name of test>
	*/

	Request parseJSONString(String jsonString) {
		Request req = null;
		try
		{
			// https://codesjava.com/parse-json-in-java

			Object obj = new JSONParser().parse(jsonString);
			JSONObject jsonObj = (JSONObject) obj;
			String apikey = jsonObj.containsKey("apikey") ? String.valueOf(jsonObj.get("apikey")) : null;
			String username = jsonObj.containsKey("username") ? String.valueOf(jsonObj.get("username")) : null;
			String drugname = jsonObj.containsKey("drug") ? String.valueOf(jsonObj.get("drug")).toLowerCase() : null;
			RequestAction action = null;
			try
			{
				action = jsonObj.containsKey("action") ? RequestAction.valueOf(String.valueOf(jsonObj.get("action")).toUpperCase()) : null;
			}
			catch(IllegalArgumentException e) 
			{
				// used to catch the exceptions caused when action was given invalid values
				action = null;
			}
			if(action != null && action.name().equalsIgnoreCase(RequestAction.SHIP.name()))
			{
				int quantity = jsonObj.containsKey("quantity") && jsonObj.get("quantity") != null && String.valueOf(jsonObj.get("quantity")) != "" ? Integer.parseInt(String.valueOf(jsonObj.get("quantity"))) : 0;
				JSONObject address = jsonObj.containsKey("address") ? (JSONObject) jsonObj.get("address") : null;
				if(address != null) {
					String customer = address.containsKey("customer") ? String.valueOf(address.get("customer")).toLowerCase() : null;
					String street = address.containsKey("street") ? String.valueOf(address.get("street")).toLowerCase() : null;
					String city = address.containsKey("city") ? String.valueOf(address.get("city")).toLowerCase() : null;
					String province = address.containsKey("province") ? String.valueOf(address.get("province")).toLowerCase() : null;
					String country = address.containsKey("country") ? String.valueOf(address.get("country")).toLowerCase() : null;
					String postalCode = address.containsKey("postalCode") ? String.valueOf(address.get("postalCode")).toLowerCase() : null;
					req = new Request(apikey, username, drugname, quantity, action, customer, street, city, province, country, postalCode);
				}
				else {
					req = new Request(apikey, username, drugname, quantity, action);
				}
			}
			else
			{
				req = new Request(apikey, username, drugname, action);
			}
		}
		catch (Exception e)
		{
			req = null;
			System.out.println("Inside parse " + e);
		}
		return req;
	}

	static public void runUnitTests()
	{
		IShipMate shipMate = new ShipMate();
		IDatabase database = new Database();
		IAuthentication authentication = new Authentication();
		RequestProcessor requestProcessor = new RequestProcessor();
		requestProcessor.RequestWithEmptyapikeyAuthenticationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.RequestWithNULLapikeyAuthenticationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.RequestWithFALSEapikeyAuthenticationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthenticatedRequestNULLUsernameAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthenticatedRequestEmptyUsernameAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthenticatedRequestNULLActionAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthenticatedRequestInvalidActionAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.UnAuthorizedShipRequestAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.UnAuthorizedQueryRequestAuthorizationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthorizedQueryReqNULLDrugValidationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthorizedShipReqEmptyDrugValidationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.AuthorizedReqUnknownDrugValidationTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.ValidQueryRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLAddressShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyAddressShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLCustomerShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyCustomerShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLStreetShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyStreetShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLCityShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyCityShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLProvinceShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyProvinceShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLCountryShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyCountryShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.NULLPostalCodeShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.EmptyPostalCodeShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.UnknownAddressShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.KnownAddrShipReqInsufficientStockTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.ConsecutiveShipReqOfSameDrugInsuffStockTest(requestProcessor, authentication, shipMate, database);
		requestProcessor.ValidShipRequestProcessTest(requestProcessor, authentication, shipMate, database);
	}

	private void RequestWithEmptyapikeyAuthenticationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Authentication Failure\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - RequestWithEmptyapikeyAuthenticationTest");
        }
        else {
            System.out.println("FAIL - RequestWithEmptyapikeyAuthenticationTest");
        }
	}
	
	void RequestWithNULLapikeyAuthenticationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Authentication Failure\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - RequestWithNULLapikeyAuthenticationTest");
        }
        else {
            System.out.println("FAIL - RequestWithNULLapikeyAuthenticationTest");
        }
    }

    void RequestWithFALSEapikeyAuthenticationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyfAlsE\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Authentication Failure\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - RequestWithFALSEapikeyAuthenticationTest");
        }
        else {
            System.out.println("FAIL - RequestWithFALSEapikeyAuthenticationTest");
        }
    }

    void AuthenticatedRequestNULLUsernameAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"action\":\"QUERY\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthenticatedRequestNULLUsernameAuthorizationTest");
        }
        else {
            System.out.println("FAIL - AuthenticatedRequestNULLUsernameAuthorizationTest");
        }
	}
	
	void AuthenticatedRequestEmptyUsernameAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"\",\"action\":\"QUERY\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthenticatedRequestEmptyUsernameAuthorizationTest");
        }
        else {
            System.out.println("FAIL - AuthenticatedRequestEmptyUsernameAuthorizationTest");
        }
	}
	
	void AuthenticatedRequestNULLActionAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abi\",}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthenticatedRequestNULLActionAuthorizationTest");
        }
        else {
            System.out.println("FAIL - AuthenticatedRequestNULLActionAuthorizationTest");
        }
	}
	
	void AuthenticatedRequestInvalidActionAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abi\",\"action\":\"QUERYY\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthenticatedRequestInvalidActionAuthorizationTest");
        }
        else {
            System.out.println("FAIL - AuthenticatedRequestInvalidActionAuthorizationTest");
        }
	}
	
	void UnAuthorizedShipRequestAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abiQueRy\",\"action\":\"ship\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - UnAuthorizedShipRequestAuthorizationTest");
        }
        else {
            System.out.println("FAIL - UnAuthorizedShipRequestAuthorizationTest");
        }
	}
	
	void UnAuthorizedQueryRequestAuthorizationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abi\",\"action\":\"query\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Not Authorized\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - UnAuthorizedQueryRequestAuthorizationTest");
        }
        else {
            System.out.println("FAIL - UnAuthorizedQueryRequestAuthorizationTest");
        }
	}
	
	void AuthorizedQueryReqNULLDrugValidationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abiQueRy\",\"action\":\"query\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Drug\",\"status\":500}";
        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthorizedQueryReqNULLDrugValidationTest");
        }
        else {
            System.out.println("FAIL - AuthorizedQueryReqNULLDrugValidationTest");
        }
	}
	
	void AuthorizedShipReqEmptyDrugValidationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
        String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Drug\",\"status\":500}";
        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthorizedShipReqEmptyDrugValidationTest");
        }
        else {
            System.out.println("FAIL - AuthorizedShipReqEmptyDrugValidationTest");
        }
	}
	
	void AuthorizedReqUnknownDrugValidationTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"crocin\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Drug\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - AuthorizedReqUnknownDrugValidationTest");
        }
        else {
            System.out.println("FAIL - AuthorizedReqUnknownDrugValidationTest");
        }
	}

	void ValidQueryRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abiQueRy\",\"action\":\"QuerY\",\"drug\":\"Dolo\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"count\":30,\"status\":200}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - ValidQueryRequestProcessTest");
        }
        else {
            System.out.println("FAIL - ValidQueryRequestProcessTest");
        }
	}

	void NULLAddressShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLAddressShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLAddressShipRequestProcessTest");
        }
	}

	void EmptyAddressShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\"}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyAddressShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyAddressShipRequestProcessTest");
        }
	}

	void NULLCustomerShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLCustomerShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLCustomerShipRequestProcessTest");
        }
	}

	void EmptyCustomerShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\" \",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyCustomerShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyCustomerShipRequestProcessTest");
        }
	}

	void NULLStreetShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLStreetShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLStreetShipRequestProcessTest");
        }
	}

	void EmptyStreetShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyStreetShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyStreetShipRequestProcessTest");
        }
	}

	void NULLCityShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLCityShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLCityShipRequestProcessTest");
        }
	}

	void EmptyCityShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\" \",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyCityShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyCityShipRequestProcessTest");
        }
	}

	void NULLProvinceShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLProvinceShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLProvinceShipRequestProcessTest");
        }
	}

	void EmptyProvinceShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"\",\"country\":\"Canada\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyProvinceShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyProvinceShipRequestProcessTest");
        }
	}
	void NULLCountryShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLCountryShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLCountryShipRequestProcessTest");
        }
	}

	void EmptyCountryShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\" \",\"postalCode\":\"H0H0H0\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyCountryShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyCountryShipRequestProcessTest");
        }
	}

	void NULLPostalCodeShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - NULLPostalCodeShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - NULLPostalCodeShipRequestProcessTest");
        }
	}

	void EmptyPostalCodeShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"  \"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - EmptyPostalCodeShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - EmptyPostalCodeShipRequestProcessTest");
        }
	}

	void UnknownAddressShipRequestProcessTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\", \"address\":{\"customer\":\"Abc xyz\",\"street\":\"Robie Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"  \"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Unknown Address\",\"status\":500}";

        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - UnknownAddressShipRequestProcessTest");
        }
        else {
            System.out.println("FAIL - UnknownAddressShipRequestProcessTest");
        }
	}

	void KnownAddrShipReqInsufficientStockTest(RequestProcessor requestProcessor, IAuthentication authentication, IShipMate shipMate, IDatabase database) {
		String inputJSON = "{\"apikey\":\"qwertyTruE\",\"username\":\"abishIP\",\"action\":\"Ship\",\"drug\":\"amoxicillin\",\"quantity\":250, \"address\":{\"customer\":\"abi\",\"street\":\"Gerrish Street\",\"city\":\"Halifax\",\"province\":\"Nova Scotia\",\"country\":\"Canada\",\"postalCode\":\"B3K5K3\"}}";
		String actualOutput = requestProcessor.processRequest(inputJSON, authentication, shipMate, database);
		String expectedOutput = "{\"error\":\"Insufficient Stock\",\"status\":500}";
		System.out.println(inputJSON + "\n" + actualOutput + "\n" + expectedOutput);
        if(actualOutput.equals(expectedOutput)) {
            System.out.println("PASS - KnownAddrShipReqInsufficientStockTest");
        }
        else {
            System.out.println("FAIL - KnownAddrShipReqInsufficientStockTest");
        }
	}
}
