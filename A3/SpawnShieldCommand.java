public class SpawnShieldCommand extends Command
{
	public SpawnShieldCommand(Object receiver, String[] args)
	{
		super(receiver, args);
	}

	@Override
	public void Execute()
	{
		Square square = (Square) receiver;
		IAsteroidGameFactory factory = GameBoard.Instance().GetFactory();
		System.out.println("Spawning shield at (" + args[0] + "," + args[1] + ")");
		Shield shld = new Shield(square);
		// GameBoard.Instance().GetBoard().get(Integer.valueOf(args[1])).set(Integer.valueOf(args[0]), shield);
		Subject.getAsteroidSubject().Attach(shld);
		Subject.getAsteroidSubject().Detach(square);
	}
}