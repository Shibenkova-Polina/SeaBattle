package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.players.Computer;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Computer_Logic_Test {
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_TWO = 2;
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_FOUR = 4;
    private static final int NUMBER_FIVE = 5;
    private static final int NUMBER_SIX = 6;

    private final Computer computer = Computer.getInstance();

    @Test
    @DisplayName("when computer marks cells as killed after shot")
    public void whenComputerMarksCellsAsKilledAfterShot() {
        computer.markKilled(NUMBER_ONE, NUMBER_ONE);
        Cell[][] cells = computer.getEnemyCells();

        then("KILL").isEqualTo(cells[NUMBER_ONE][NUMBER_ONE].getState().toString());
        then("MISS").isEqualTo(cells[NUMBER_ZERO][NUMBER_ZERO].getState().toString());
        then("MISS").isEqualTo(cells[NUMBER_TWO][NUMBER_TWO].getState().toString());
        then("MISS").isEqualTo(cells[NUMBER_ZERO][NUMBER_ONE].getState().toString());
        then("MISS").isEqualTo(cells[NUMBER_TWO][NUMBER_ONE].getState().toString());
        then("SEA").isEqualTo(cells[NUMBER_ONE][NUMBER_THREE].getState().toString());
        then("SEA").isEqualTo(cells[NUMBER_THREE][NUMBER_ONE].getState().toString());
    }

    @Test
    @DisplayName("when computer calculate aims for next shots")
    public void whenComputerCalculateAimsForNextShots() {
        computer.markKilled(NUMBER_ONE, NUMBER_ONE);

        computer.setLastX(NUMBER_THREE);
        computer.setLastY(NUMBER_ONE);
        computer.calculateAims();

        Integer[] expectedRight = {NUMBER_FOUR, NUMBER_FIVE, NUMBER_SIX};
        Integer[] expectedLeft = {};
        Integer[] expectedUp = {NUMBER_ZERO};
        Integer[] expectedDown = {NUMBER_TWO, NUMBER_THREE, NUMBER_FOUR};

        Object[] actualRight = computer.getRightAims().toArray();
        Object[] actualLeft = computer.getLeftAims().toArray();
        Object[] actualUp = computer.getUpAims().toArray();
        Object[] actualDown = computer.getDownAims().toArray();

        assertArrayEquals(expectedRight, actualRight);
        assertArrayEquals(expectedLeft, actualLeft);
        assertArrayEquals(expectedUp, actualUp);
        assertArrayEquals(expectedDown, actualDown);
    }

    @Test
    @DisplayName("when computer gets coordinate to shoot at one of the aim")
    public void whenComputerGetNextCoordinateToShoot() {
        computer.markKilled(NUMBER_ONE, NUMBER_ONE);
        computer.setLastX(NUMBER_THREE);
        computer.setLastY(NUMBER_ONE);
        computer.calculateAims();

        int[] actualNextCoordinate = computer.getNextCoordinates();
        int[] expectedNextCoordinate = {NUMBER_FOUR, NUMBER_ONE};
        assertArrayEquals(expectedNextCoordinate, actualNextCoordinate);
    }
}
