package demo;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import controller.Game;
import view.GameBoard;
import view.HandView;

public class Demo {

	public static void createJFrame() {
		HandView handView = new HandView();
		GameBoard gameBoard = new GameBoard(handView);

		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(gameBoard, BorderLayout.CENTER);
		frame.add(handView, BorderLayout.SOUTH);
		frame.setSize(1024, 800);
		frame.setMinimumSize(new Dimension(1024, 800));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

		Game.getGame(gameBoard, handView).init();

	}

	public static void main(String[] args) {

		createJFrame();

	}

}
