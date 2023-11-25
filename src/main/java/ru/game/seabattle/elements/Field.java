package ru.game.seabattle.elements;

import ru.game.seabattle.process.ArrangementOfShips;

public class Field {
    public static final int FIELD_SIZE = 10;

    private Cell[][] cells = new Cell[FIELD_SIZE][FIELD_SIZE];

    public Field() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                cells[j][i] = new Cell(j, i, CellState.SEA, null);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void resetCells() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                cells[j][i].setState(CellState.SEA);
            }
        }

        ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
        arrangementOfShips.setPlacementField(cells);
        arrangementOfShips.createShips(this);
    }
}
