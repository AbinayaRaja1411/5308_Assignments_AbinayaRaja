public class Calculator
{
	public static int divide(int left, int right)
	{
		MathOperation op = new Divide(left, right);
		return op.getResult();
	}

	public static int multiply(int left, int right)
	{
		MathOperation op = new Multiply(left, right);
		return op.getResult();
	}

	public static int add(int left, int right)
	{
		MathOperation op = new Add(left, right);
		return op.getResult();
	}

	public static int subtract(int left, int right)
	{
		MathOperation op = new Subtract(left, right);
		return op.getResult();
	}
}