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
        Computer.createFirst = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (myTurn) {
            Cell cell = (Cell) e.getSource();
            shoot(cell.getXCoord(), cell.getYCoord());
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
        Computer.createFirst = false;
    }

    private void shoot(int x, int y) {
        shootResult = opponent.getShot(x, y);

        if (shootResult == ShootResult.MISS) {
            switchPlayers();
        }

        afterShot();
    }

    private void afterShot() {
        if (shootResult == ShootResult.KILL) {
            shipsToKill--;
        }

        Messages.getInstance().getMessage(true, shootResult);
    }

    public void setActionListnerToCells() {
        Cell[][] cells = opponent.getField().getCells();

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                cells[j][i].addActionListener(this);
            }
        }
    }
}