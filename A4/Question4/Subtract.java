public class Subtract implements IArithmeticOperation
{
    @Override
    public int calculate(int leftOperand, int rightOperand) 
    {
        return leftOperand - rightOperand;
    }
}