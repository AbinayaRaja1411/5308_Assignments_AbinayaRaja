
import java.util.ArrayList;

// Implementation of the Abstract Factory pattern's interface
public class AsteroidGameFactory implements IAsteroidGameFactory
{
	@Override
	public BoardComponent MakeSquare()
	{
		Square sqr = new Square();
		Subject.getAsteroidSubject().Attach(sqr);
		return sqr;
	}

	@Override
	public BoardComponent MakeBuilding()
	{
		Building building = new Building();
		return building;
	}

	@Override
	public Asteroid MakeAsteroid(int height)
	{
		Asteroid asteroid = new Asteroid(height);
		return asteroid;
	}
	
	@Override
	public ArrayList<ArrayList<BoardComponent>> MakeBoard(int height, int width)
	{
		ArrayList<ArrayList<BoardComponent>> board = new ArrayList<>();
		for (int i = 0; i < height; i++)
		{
			// Make an array for each row.
			ArrayList<BoardComponent> row = new ArrayList<>();
			// Add squares equal to the width to the row.
			for (int j = 0; j < width; j++)
			{
				row.add(MakeSquare());
			}
			board.add(row);
		}
		return board;
	}
	
	@Override
	public Command MakeCommand(String commandLine)
	{
		String commandToMake;
		String[] args = null;
		String commandLineArgs = "";
		if (commandLine.contains(" "))
		{
			commandToMake = commandLine.split(" ")[0];
			commandLineArgs = commandLine.substring(commandToMake.length() + 1);
			args = commandLineArgs.split(" ");
		}
		else
		{
			commandToMake = commandLine;
		}
		switch (commandToMake.toUpperCase())
		{
			case "INIT":
			{
				return new InitializeBoardCommand(GameBoard.Instance(), args);
			}
			case "SPAWN_ASTEROID":
			{
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnAsteroidCommand(square, args);
			}
			case "TICK":
			{
				return new TickCommand(GameBoard.Instance(), args);
			}
			case "START_GAME":
			{
				return new StartGameCommand(GameBoard.Instance(), args);
			}
			case "SPAWN_BUILDING":
			{
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnBuildingCommand(square, args);
			}
			case "SPAWN_SHIELD":
			{
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				BoardComponent square = GameBoard.Instance().GetBoard().get(y).get(x);
				return new SpawnShieldCommand(square, args);
			}
		}
		return null;
	}
}
