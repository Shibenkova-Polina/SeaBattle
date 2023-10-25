package players;

import gameElements.Cell;
import gameElements.Field;
import gui.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Human extends Player implements ActionListener {

    public Human() {
        super();
        myTurn = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myTurn) {
            Cell cell = (Cell) e.getSource();
            shoot(cell.getXCoord(), cell.getYCoord());
        }
    }

    public void setActionListnerToCells() {
        Cell[][] cells = opponent.getField().getCells();

        for (int i = 0; i < Field.fieldSize; i++) {
            for (int j = 0; j < Field.fieldSize; j++) {
                cells[j][i].addActionListener(this);
            }
        }
    }

    @Override
    public void setOpponent(Player opponent) {
        super.setOpponent(opponent);
        setActionListnerToCells();
    }

    @Override
    public void newGame() {
        super.newGame();
        myTurn = true;
    }

    private void shoot(int x, int y) {
        shootResult = opponent.getShot(x, y);

        if (shootResult == ShootResult.MISS){
            switchPlayers();
        }

        afterShot(0, 0);
    }

    @Override
    public void afterShot(int x, int y) {
        if (shootResult == ShootResult.KILL) {
            shipsToKill--;
        }

        Messages.getInstance().getMessage(true, shootResult);
    }
}