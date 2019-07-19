import java.util.HashMap;

public class Help
{
	HashMap<String, Command> commands;

	Help()
	{
		this.commands = new HashMap<>();
		this.commands.put("print", new PrintCommand());
		this.commands.put("close", new CloseCommand());
		this.commands.put("open", new OpenCommand());
	}

	public String getHelp(String command)
	{
		if (command != null && command.length() != 0)
		{
			return this.commands.get(command).getHelp();
		}
		return listAllCommands();
	}

	public String listAllCommands()
	{
		return "Commands: print, open, close";
	}
}