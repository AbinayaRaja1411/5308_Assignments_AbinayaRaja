
public class QuerySuccessResponse extends Response
{
	QuerySuccessResponse(int statusCode, int quantityInStock) {
		super(statusCode);
		this.response.put("count", quantityInStock);
	}

	public int quantityInStock;

}
