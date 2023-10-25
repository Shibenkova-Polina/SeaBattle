package gameElements;

import gui.GameInterface;

import javax.swing.*;
import java.util.ArrayList;

public class Cell extends JButton {

    private int xCoord;
    private int yCoord;

    private Ship ship;
    private CellState state;

    public Cell(int x, int y, CellState cellState, Ship ship) {
        super();
        this.xCoord = x;
        this.yCoord = y;
        this.state = cellState;
        this.ship = ship;
    }

    public enum CellState {
        SEA,
        MISS,
        SHIP,
        INJURE,
        KILL
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
        GameInterface.getInstance().draw();
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public boolean isShip(){
        return state == CellState.SHIP;
    }

    public void getShot() {
        switch (state){
            case SEA:
                setState(CellState.MISS);
                break;
            case SHIP:
                if (conditionOfShip()) {
                    setState(CellState.INJURE);
                } else {
                    ship.sink();
                }
                break;
        }
    }

    private boolean conditionOfShip() {
        ArrayList<Cell> cells = ship.getCells();

        for (Cell cell : cells){
            if (!cell.equals(this) && cell.isShip()) {
                return true;
            }
        }

        return false;
    }

    public void inititalizeState(CellState state){
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Cell)) {
            return false;
        }

        Cell cell = (Cell) obj;

        return (cell.xCoord == xCoord && cell.yCoord == yCoord);
    }
}