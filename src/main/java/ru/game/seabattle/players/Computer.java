package ru.game.seabattle.players;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.gui.Messages;

import java.util.ArrayList;

public class Computer extends Player {
    private int shipsToKill = NUMBER_OF_PLAYER_SHIPS;
    private final int SLEEP_TIME_SHOT = 300;
    private final int SLEEP_TIME_AFTER_SHOT = 2000;
    private final int NUMBER_OF_SHIP_TYPES = 4;
    private final int NUMBER_OF_COORDINATES = 2;

    private Cell[][] enemyCells;

    private ArrayList<Integer> leftAims = new ArrayList();
    private ArrayList<Integer> rightAims = new ArrayList();
    private ArrayList<Integer> downAims = new ArrayList();
    private ArrayList<Integer> upAims = new ArrayList();
    private ArrayList<Integer> currentAims;

    private boolean shootToPotentialAims;
    private boolean aimsCalculated;
    private boolean creationBD;

    private int lastX;
    private int lastY;

    private static Computer instance;

    private Computer() {
        super();
        myTurn = false;
        setEnemyField();
        creationBD = true;
    }

    @Override
    public void newGame() {
        super.newGame();
        myTurn = false;
        setEnemyField();
        creationBD = true;
        shipsToKill = NUMBER_OF_PLAYER_SHIPS;
    }

    private void setEnemyField() {
        enemyCells = new Cell[Field.FIELD_SIZE][Field.FIELD_SIZE];

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                enemyCells[j][i] = new Cell(j, i, Cell.CellState.SEA, null);
            }
        }
    }

    public synchronized static Computer getInstance() {
        if (instance == null) {
            instance = new Computer();
        }
        return instance;
    }

    public boolean getCreationBD() {
        return creationBD;
    }

    public void setCreationBD(boolean creation) {
        creationBD = creation;
    }

    public int getShipsToKill() {
        return shipsToKill;
    }

    public void setShipsToKill(int num) {
        shipsToKill = num;
    }

    public void shoot() {
        while (myTurn) {
            try {
                Thread.sleep(SLEEP_TIME_SHOT);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (shipsToKill == 0) {
                return;
            }

            int x;
            int y;

            do {
                int[] coordinates = getCoordinates();
                x = coordinates[0];
                y = coordinates[1];
                shootResult = opponent.getShot(x, y);
            } while (shootResult == null);

            afterShot(x, y);
        }
    }

    private int[] getCoordinates() {
        int[] coordinates;

        if (shootToPotentialAims) {
            coordinates = getNextCoordinates();
        } else {
            coordinates = getRandomCoordinates();
        }

        return coordinates;
    }
    private int[] getNextCoordinates() {
        if (!aimsCalculated) {
            calculateAims();
        }

        int[] coordinate = new int[NUMBER_OF_COORDINATES];

        if(!leftAims.isEmpty()) {
            currentAims = leftAims;
            coordinate[0] = leftAims.get(0);
            coordinate[1] = lastY;
            leftAims.remove(0);
        } else if(!rightAims.isEmpty()) {
            currentAims = rightAims;
            coordinate[0] = rightAims.get(0);
            coordinate[1] = lastY;
            rightAims.remove(0);
        } else if(!downAims.isEmpty()) {
            currentAims = downAims;
            coordinate[0] = lastX;
            coordinate[1] = downAims.get(0);
            downAims.remove(0);
        } else if(!upAims.isEmpty()) {
            currentAims = upAims;
            coordinate[0] = lastX;
            coordinate[1] = upAims.get(0);
            upAims.remove(0);
        }

        return coordinate;
    }

    private void calculateAims() {
        clearAims();

        int x = lastX;
        int y = lastY;

        aimsCalculated = true;

        for (int i = x + 1; i < x + NUMBER_OF_SHIP_TYPES; ++i) {
            if (i < Field.FIELD_SIZE) {
                if (enemyCells[i][y].getState() == Cell.CellState.MISS) {
                    break;
                }

                if (enemyCells[i][y].getState() == Cell.CellState.SEA) {
                    rightAims.add(i);
                }
            } else {
                break;
            }
        }

        for (int i = x - 1; i > x - NUMBER_OF_SHIP_TYPES; --i) {
            if (i >= 0) {
                if (enemyCells[i][y].getState() == Cell.CellState.MISS) {
                    break;
                }

                if (enemyCells[i][y].getState() == Cell.CellState.SEA) {
                    leftAims.add(i);
                }
            } else {
                break;
            }
        }

        for (int i = y - 1; i > y - NUMBER_OF_SHIP_TYPES; --i) {
            if (i >= 0) {
                if (enemyCells[x][i].getState() == Cell.CellState.MISS) {
                    break;
                }

                if (enemyCells[x][i].getState() == Cell.CellState.SEA) {
                    upAims.add(i);
                }
            } else {
                break;
            }
        }

        for (int i = y + 1; i < y + NUMBER_OF_SHIP_TYPES; ++i) {
            if (i < Field.FIELD_SIZE) {
                if (enemyCells[x][i].getState() == Cell.CellState.MISS) {
                    break;
                }

                if (enemyCells[x][i].getState() == Cell.CellState.SEA) {
                    downAims.add(i);
                }
            } else {
                break;
            }
        }
    }

    private void clearAims() {
        leftAims.clear();
        rightAims.clear();
        downAims.clear();
        upAims.clear();
    }

    private int[] getRandomCoordinates() {
        int[] coordinate = new int[NUMBER_OF_COORDINATES];
        int x;
        int y;

        do {
            x = (int) (Math.random() * Field.FIELD_SIZE);
            y = (int) (Math.random() * Field.FIELD_SIZE);
        } while (enemyCells[x][y].getState() != Cell.CellState.SEA);

        coordinate[0] = x;
        coordinate[1] = y;

        lastX = x;
        lastY = y;

        return coordinate;
    }

    private void afterShot(int x, int y) {
        boolean sleep = false;

        switch (shootResult) {
            case MISS:
                if (shootToPotentialAims) {
                    currentAims.clear();
                }

                enemyCells[x][y].setState(Cell.CellState.MISS);
                switchPlayers();
                break;
            case INJURE:
                shootToPotentialAims = true;
                enemyCells[x][y].setState(Cell.CellState.INJURE);
                sleep = true;
                break;
            case KILL:
                markKilled(x, y);
                sleep = true;
                shipsToKill --;
                shootToPotentialAims = false;
                aimsCalculated = false;
                break;
        }

        Messages.getInstance().getMessage(false, shootResult);

        try {
            if (sleep) {
                Thread.sleep(SLEEP_TIME_AFTER_SHOT);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void markKilled(int x, int y) {
        enemyCells[x][y].setState(Cell.CellState.INJURE);

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                Cell cell = enemyCells[j][i];

                if (cell.getState() == Cell.CellState.INJURE) {
                    cell.setState(Cell.CellState.KILL);

                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (k >= 0 && l >= 0 && l < Field.FIELD_SIZE && k < Field.FIELD_SIZE) {
                                if (enemyCells[l][k].getState() == Cell.CellState.SEA) {
                                    enemyCells[l][k].setState(Cell.CellState.MISS);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}