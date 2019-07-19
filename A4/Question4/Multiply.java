public class Multiply extends MathOperation
{
    public Multiply(int leftOperand, int rightOperand) 
    {
        super(leftOperand, rightOperand);
    }

    @Override
    public int getResult() 
    {
        return leftOperand * rightOperand;
    }
}