package ru.game.seabattle.players;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.CellState;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.elements.ShootResult;

public abstract class Player {
    public static final int NUMBER_OF_PLAYER_SHIPS = 10;

    public Player opponent;
    public boolean myTurn;
    public ShootResult shootResult;

    private final Field field;

    public Player() {
        field = new Field();
    }

    public boolean moves() {
        return myTurn;
    }

    public Field getField() {
        return field;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public void switchPlayers() {
        myTurn = !myTurn;
        opponent.myTurn = !opponent.myTurn;
    }

    public ShootResult getShot(int x, int y) {
        Cell cell = field.getCells()[x][y];

        if (cell.getState() == CellState.SEA || cell.getState() == CellState.SHIP) {
            cell.getShot();

            switch (cell.getState()) {
                case MISS:
                    return ShootResult.MISS;
                case INJURE:
                    return ShootResult.INJURE;
                case KILL:
                    return ShootResult.KILL;
            }
        }
        return null;
    }

    public void newGame() {
        field.resetCells();
    }
}