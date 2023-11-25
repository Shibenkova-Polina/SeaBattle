package ru.game.seabattle.elements;

import java.util.ArrayList;
public class Ship {
    private ArrayList<Cell> cells;
    private ArrayList<Cell> borders;

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void sink() {
        for (Cell cell : cells) {
            cell.setState(CellState.KILL);
        }

        for (Cell cell : borders) {
            cell.setState(CellState.MISS);
        }
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void setBorders(ArrayList<Cell> borders) {
        this.borders = borders;
    }
}