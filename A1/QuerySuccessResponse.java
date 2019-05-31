
public class QuerySuccessResponse extends Response implements IResponse
{
	QuerySuccessResponse(int statusCode, int quantityInStock) {
		super(statusCode);
		this.response.put("count", quantityInStock);
	}

	public int quantityInStock;

}
