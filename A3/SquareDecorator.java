public abstract class SquareDecorator extends BoardComponent
{
	protected BoardComponent square;
	
	public SquareDecorator(BoardComponent square)
	{
		this.square = square;
	}

	public abstract void Operation();
	public abstract void Add(BoardComponent child);
	public abstract void Remove(BoardComponent child);
}