public class Add extends MathOperation 
{
    public Add(int leftOperand, int rightOperand) 
    {
        super(leftOperand, rightOperand);
    }

    @Override
    public int getResult() 
    {
        return leftOperand + rightOperand;
    }
    
}