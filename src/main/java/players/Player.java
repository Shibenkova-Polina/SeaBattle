package players;

import main.java.gameElements.Cell;
import main.java.gameElements.Field;

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

    public enum ShootResult {
        MISS,
        INJURE,
        KILL
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

        if (cell.getState() == Cell.CellState.SEA || cell.getState() == Cell.CellState.SHIP) {
            cell.getShot();

            switch (cell.getState()) {
                case MISS:
                    return ShootResult.MISS;
                case INJURE:
                    return ShootResult.INJURE;
                case KILL:
                    return ShootResult.KILL;
                default:
                    return null;
            }
        }
        return null;
    }

    public void newGame() {
        field.resetCells();
    }
}