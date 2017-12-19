package controller.command;

public class Invoker {
	private Command command = null;

	public Command getCommand() {
		return command;
	}

	public void runCommand() {
		this.command.execute();
	}

	public void setCommand(final Command command) {
		this.command = command;
	}
}
