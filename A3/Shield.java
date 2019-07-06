public class Shield extends SquareDecorator {
	private int shieldHealth;

	public Shield(BoardComponent shield) {
		super(shield);
		shieldHealth = 2;
	}

	@Override
	public void Operation() {
		// Shield just stand there, they don't do anything.
	}

	@Override
	public void Add(BoardComponent child) {
		// Do nothing, I'm a leaf.
	}

	@Override
	public void Remove(BoardComponent child) {
		// Do nothing, I'm a leaf.
	}

	@Override
	public void updateObserver(BoardComponent target) {
		if(target.equals(square))
		{
			if(shieldHealth > 0) 
			{
				shieldHealth -= 1;
			}
			else
			{
				square.updateObserver(target);
			}
		}
	}
}