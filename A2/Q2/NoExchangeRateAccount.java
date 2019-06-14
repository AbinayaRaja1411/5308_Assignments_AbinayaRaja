public class NoExchangeRateAccount  extends Account 
{
	@Override
    public void credit(float amount)
	{
		balance += amount;
	}

	@Override
	public void debit(float amount)
	{
		balance -= amount;
	}
}