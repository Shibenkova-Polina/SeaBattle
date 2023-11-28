package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.players.Computer;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class Computer_Logic_Test {
    private final Computer computer = Computer.getInstance();

    @Test
    @DisplayName("when computer marks cells as killed after shot")
    public void whenComputerMarksCellsAsKilledAfterShot() {
        computer.markKilled(1, 1);
        Cell[][] cells = computer.getEnemyCells();

        then("KILL").isEqualTo(cells[1][1].getState().toString());
        then("MISS").isEqualTo(cells[0][0].getState().toString());
        then("MISS").isEqualTo(cells[2][2].getState().toString());
        then("MISS").isEqualTo(cells[0][1].getState().toString());
        then("MISS").isEqualTo(cells[2][1].getState().toString());
        then("SEA").isEqualTo(cells[1][3].getState().toString());
        then("SEA").isEqualTo(cells[3][1].getState().toString());
    }

    @Test
    @DisplayName("when computer calculate aims for next shots")
    public void whenComputerCalculateAimsForNextShots() {
        computer.markKilled(1, 1);

        computer.setLastX(3);
        computer.setLastY(1);
        computer.calculateAims();

        Integer[] expectedRight = {4, 5, 6};
        Integer[] expectedLeft = {};
        Integer[] expectedUp = {0};
        Integer[] expectedDown = {2, 3, 4};

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
        computer.markKilled(1, 1);
        computer.setLastX(3);
        computer.setLastY(1);
        computer.calculateAims();

        int[] actualNextCoordinate = computer.getNextCoordinates();
        int[] expectedNextCoordinate = {4, 1};
        assertArrayEquals(expectedNextCoordinate, actualNextCoordinate);
    }
}
