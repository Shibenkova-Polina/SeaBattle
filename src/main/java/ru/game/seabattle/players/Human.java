package ru.game.seabattle.players;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.gui.Messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Human extends Player implements ActionListener {
    public static int humanNumberOfWins = 0;
    public static int shipsToKill = NUMBER_OF_PLAYER_SHIPS;

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
        shipsToKill = NUMBER_OF_PLAYER_SHIPS;
    }

    public static int getShipsToKill() {
        return shipsToKill;
    }

    public static void setShipsToKill(int num) {
        shipsToKill = num;
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