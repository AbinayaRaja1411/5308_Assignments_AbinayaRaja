public class MathOperation
{
	private int leftOperand;
	private int rightOperand;
	private IArithmeticOperation operationType;

	public MathOperation(IArithmeticOperation operationType, int leftOperand, int rightOperand)
	{
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		this.operationType = operationType;
	}

	public int getResult()
	{
		return operationType.calculate(leftOperand, rightOperand);
	}
}