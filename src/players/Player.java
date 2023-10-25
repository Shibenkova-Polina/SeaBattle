package players;

import gameElements.Cell;
import gameElements.Field;

public abstract class Player {

    public Player opponent;
    private Field field;
    public boolean myTurn;
    public int shipsToKill = 10;
    public ShootResult shootResult;

    public Player() {
        field = new Field();
    }

    public boolean moves(){
        return myTurn;
    }

    public int getShipsToKill() {
        return shipsToKill;
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

    public enum ShootResult {
        MISS,
        INJURE,
        KILL
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

        } return null;
    }

    public void afterShot(int x, int y) {};

    public void newGame() {
        field.resetCells();
        shipsToKill = 10;
    }
}