import java.util.Date;

public class ShipSuccessResponse extends Response
{
	String estimateddeliverydate;

	ShipSuccessResponse(int statusCode, String estDeliveryDate) {
		super(statusCode);
		response.put("estimateddeliverydate", estDeliveryDate);
	}

	public Date estimatedDeliveryDate;
}
