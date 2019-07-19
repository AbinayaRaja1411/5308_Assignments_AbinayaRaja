public class Divide extends MathOperation
{
    public Divide(int leftOperand, int rightOperand) 
    {
        super(leftOperand, rightOperand);
    }

    @Override
    public int getResult() 
    {
        return leftOperand / rightOperand;
    }
}