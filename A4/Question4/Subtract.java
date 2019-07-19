public class Subtract extends MathOperation
{
    public Subtract(int leftOperand, int rightOperand) 
    {
        super(leftOperand, rightOperand);
    }

    @Override
    public int getResult() 
    {
        return leftOperand - rightOperand;
    }
}