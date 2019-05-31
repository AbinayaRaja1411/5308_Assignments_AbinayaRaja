import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Response
{
	public int statusCode;
	protected HashMap<String, Object> response;

	Response(int statusCode) {
		this.statusCode = statusCode;
		response = new HashMap<String, Object>();
		response.put("status", statusCode);
	}

	public String getJSONString() {
		return new JSONObject(this.response).toJSONString();
	}
}
