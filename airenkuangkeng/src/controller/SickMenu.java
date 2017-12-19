package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controller.command.Command;
import controller.command.Invoker;
import controller.command.Sick;
import view.HandView;

public class SickMenu extends JPopupMenu {

	private HandView hv;

	public SickMenu(HandView hv) {
		this.hv = hv;
		for (int i = 0; i < hv.getPlayers().size(); i++) {
			Player player = hv.getPlayers().get(i);
			JMenuItem menuItem = new JMenuItem(player.getName());
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					final Command sick = new Sick(player);
					final Invoker invoker = new Invoker();
					invoker.setCommand(sick);
					invoker.runCommand();

					hv.playCard();
				}
			});
			this.add(menuItem);
		}
	}

}
