public abstract class Account
{
	protected float balance;

	public float getBalance()
	{
		return balance;
	}

	public abstract void credit(float amount);

	public abstract void debit(float amount);
}