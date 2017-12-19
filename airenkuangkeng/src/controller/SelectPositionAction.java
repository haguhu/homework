package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import controller.decorator.AddFuncCardTurn;
import controller.decorator.RoadCardTurn;
import controller.decorator.Turn;
import data.Card;
import data.EmptyCard;
import data.EndCard;
import data.FuncCleanOrRepaire;
import data.FuncCleanSpill;
import data.FuncMap;
import data.FuncRepaireRockFall;
import data.FuncRockFall;
import data.FuncSpill;
import data.Gold;
import data.RoadCard;
import view.GameBoard;
import view.HandView;

public class SelectPositionAction extends MouseAdapter {

	private boolean downFlag;
	private boolean flag;
	private GameBoard gb;

	private HandView hv;
	private boolean leftFlag;
	private boolean rightFlag;
	private boolean upFlag;

	public SelectPositionAction(GameBoard gb, HandView hv) {
		this.gb = gb;
		this.hv = hv;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (hv.getSelectedCard() == null) {
			return;
		}
		if (hv.isPlay()) {
			JOptionPane.showMessageDialog(null, "you have played");
			return;
		}

		upFlag = false;
		downFlag = false;
		leftFlag = rightFlag = false;
		int width = gb.getBoardCards()[0][0].getWidth();
		int height = gb.getBoardCards()[0][0].getHeight();
		int y = e.getX() / width;
		int x = e.getY() / height;
		System.out.println(x + "," + y);
		if (gb.getBoardCards()[x][y] instanceof EmptyCard) {
			if (hv.getSelectedCard() instanceof RoadCard) {
				RoadCard selectRoad = (RoadCard) hv.getSelectedCard();
				System.out.println(selectRoad);

				int surroundedEmptyCardNumber = 0;
				int surroundedRoadCardNumber = 0;
				if (y - 1 >= 0) {
					if (gb.getBoardCards()[x][y - 1] instanceof RoadCard) {
						System.out.println("left card:" + gb.getBoardCards()[x][y - 1]);
						RoadCard rc = ((RoadCard) gb.getBoardCards()[x][y - 1]);
						if (rc.getRight() == 1 && selectRoad.getLeft() == 1 && rc.getFuncCard() == null) {
							leftFlag = true;
							surroundedRoadCardNumber++;
						}
					} else if (gb.getBoardCards()[x][y - 1] instanceof EmptyCard
							|| gb.getBoardCards()[x][y - 1] instanceof EndCard) {
						leftFlag = true;
						surroundedEmptyCardNumber++;
					}
				} else {
					leftFlag = true;
				}

				if (y + 1 <= 8) {
					if (gb.getBoardCards()[x][y + 1] instanceof RoadCard) {
						System.out.println("right card:" + gb.getBoardCards()[x][y + 1]);
						RoadCard rc = ((RoadCard) gb.getBoardCards()[x][y + 1]);
						if (rc.getLeft() == 1 && selectRoad.getRight() == 1 && rc.getFuncCard() == null) {
							rightFlag = true;
							surroundedRoadCardNumber++;
						}
					} else if ((gb.getBoardCards()[x][y + 1] instanceof EmptyCard
							|| gb.getBoardCards()[x][y + 1] instanceof EndCard)) {
						rightFlag = true;
						surroundedEmptyCardNumber++;
					}
				} else {
					rightFlag = true;
				}

				if (x - 1 >= 0) {
					if (gb.getBoardCards()[x - 1][y] instanceof RoadCard) {
						System.out.println("up card:" + gb.getBoardCards()[x - 1][y]);
						RoadCard rc = ((RoadCard) gb.getBoardCards()[x - 1][y]);
						if (rc.getDown() == 1 && selectRoad.getUp() == 1 && rc.getFuncCard() == null) {
							upFlag = true;
							surroundedRoadCardNumber++;
						}
					} else if ((gb.getBoardCards()[x - 1][y] instanceof EmptyCard
							|| gb.getBoardCards()[x - 1][y] instanceof EndCard)) {
						upFlag = true;
						surroundedEmptyCardNumber++;
					}
				} else {
					upFlag = true;
				}

				if (x + 1 <= 4) {
					if (gb.getBoardCards()[x + 1][y] instanceof RoadCard) {
						System.out.println("down card:" + gb.getBoardCards()[x + 1][y]);
						RoadCard rc = ((RoadCard) gb.getBoardCards()[x + 1][y]);
						if (rc.getUp() == 1 && selectRoad.getDown() == 1 && rc.getFuncCard() == null) {
							downFlag = true;
							surroundedRoadCardNumber++;
						}
					} else if (x + 1 <= 4 && (gb.getBoardCards()[x + 1][y] instanceof EmptyCard
							|| gb.getBoardCards()[x - 1][y] instanceof EndCard)) {
						downFlag = true;
						surroundedEmptyCardNumber++;
					}
				} else {
					downFlag = true;
				}

				if (surroundedEmptyCardNumber == 4) {
					flag = false;
				} else if (leftFlag && rightFlag && upFlag && downFlag && surroundedRoadCardNumber > 0) {
					flag = true;
				} else
					flag = false;

				if (flag) {
					Turn.turn = new RoadCardTurn(Turn.turn, hv.getSelectedCard(), x, y);
					gb.getBoardCards()[x][y] = hv.getSelectedCard();
					hv.playCard();

					gb.update();
					hv.update();

					winOrNot((RoadCard) gb.getBoardCards()[x][y], y + 1 <= 8 ? gb.getBoardCards()[x][y + 1] : null,
							x - 1 >= 0 ? gb.getBoardCards()[x - 1][y] : null,
							x + 1 <= 4 ? gb.getBoardCards()[x + 1][y] : null);

				}
			}
		}

		if (gb.getBoardCards()[x][y] instanceof RoadCard) {
			RoadCard rc = ((RoadCard) gb.getBoardCards()[x][y]);
			if (rc.getFuncCard() == null) {
				if (hv.getSelectedCard() instanceof FuncRockFall) {
					FuncRockFall selectedCard = (FuncRockFall) hv.getSelectedCard();
					rc.setFuncCard(selectedCard);
					flag = true;
					Turn.turn = new AddFuncCardTurn(Turn.turn, selectedCard, x, y);
				}
				if (hv.getSelectedCard() instanceof FuncSpill) {
					FuncSpill selectedCard = (FuncSpill) hv.getSelectedCard();
					rc.setFuncCard(selectedCard);
					flag = true;
					Turn.turn = new AddFuncCardTurn(Turn.turn, selectedCard, x, y);
				}

			} else {
				if (rc.getFuncCard() instanceof FuncRockFall) {
					if (hv.getSelectedCard() instanceof FuncRepaireRockFall
							|| hv.getSelectedCard() instanceof FuncCleanOrRepaire) {
						rc.setFuncCard(null);
						flag = true;
						Turn.turn = new RoadCardTurn(Turn.turn, hv.getSelectedCard(), x, y);
					}
				}
				if (rc.getFuncCard() instanceof FuncSpill) {
					if (hv.getSelectedCard() instanceof FuncCleanSpill
							|| hv.getSelectedCard() instanceof FuncCleanOrRepaire) {
						rc.setFuncCard(null);
						flag = true;
						Turn.turn = new RoadCardTurn(Turn.turn, hv.getSelectedCard(), x, y);
					}
				}

			}
			if (flag) {
				hv.playCard();
				gb.update();
			}
		}

		if (gb.getBoardCards()[x][y] instanceof EndCard) {
			if (hv.getSelectedCard() instanceof FuncMap) {
				EndCard ec = (EndCard) gb.getBoardCards()[x][y];
				ec.showImage();
				ec.repaint();
				flag = true;
				hv.playCard();
			}
		}

		if (flag) {
			flag = false;
		} else {
			JOptionPane.showMessageDialog(null, "you can't put this here");
			hv.getSelectedCard().setBorder(null);
			hv.setSelectedCard(null);
		}

		if (Game.minerWin) {
			JOptionPane.showMessageDialog(null, "Miners Win!");
			Game.dispatchGold();
			Game.minerWin = false;
			Game.getGame().init();
		}

		if (Game.noHandCard() && !Game.minerWin) {
			Game.SaboteurWin = true;
			JOptionPane.showMessageDialog(null, "Saboteur Win!");
			Game.dispatchGold();
			Game.SaboteurWin = false;
			Game.getGame().init();
		}

	}

	private void showImage(EndCard endCard) {
		endCard.setFound(true);
	}

	private void winOrNot(RoadCard selectedCard, Card rightCard, Card upCard, Card downCard) {
		// TODO Auto-generated method stub
		if (selectedCard.getRight() == 1 && rightCard != null && rightCard instanceof EndCard) {
			showImage((EndCard) rightCard);
			if (rightCard instanceof Gold) {
				Game.minerWin = true;
			}
		}

		if (selectedCard.getUp() == 1 && upCard != null && upCard instanceof EndCard) {
			showImage((EndCard) upCard);
			if (upCard instanceof Gold) {
				Game.minerWin = true;
			}
		}

		if (selectedCard.getDown() == 1 && downCard != null && downCard instanceof EndCard) {
			showImage((EndCard) downCard);
			if (downCard instanceof Gold) {
				Game.minerWin = true;
			}
		}

	}

}
