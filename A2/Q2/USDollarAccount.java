public class USDollarAccount extends Account
{
	static final float EXCHANGE_RATE = 0.75f;

	@Override
	public void credit(float amount)
	{
		balance += amount * EXCHANGE_RATE;
	}

	@Override
	public void debit(float amount)
	{
		balance -= amount * EXCHANGE_RATE;
	}
}