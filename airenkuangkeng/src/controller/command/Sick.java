package controller.command;

import controller.Player;

public class Sick implements Command {
	private Player player = null;

	public Sick(final Player player) {
		this.player = player;
	}

	@Override
	public void execute() {
		this.player.sick();
	}

	@Override
	public void sick() {
		// TODO Auto-generated method stub

	}

}
