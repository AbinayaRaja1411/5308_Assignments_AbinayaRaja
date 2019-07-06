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
	public void updateObserver() {
		shieldHealth -= 1;
		if(shieldHealth == 0) {
			square.updateObserver();
		}
	}
}