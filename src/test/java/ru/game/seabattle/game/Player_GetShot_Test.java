package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.CellState;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.elements.ShootResult;
import ru.game.seabattle.players.Human;
import ru.game.seabattle.process.ArrangementOfShips;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Player_GetShot_Test {
    private static final int COORDINATE_ONE = 1;
    private static final int COORDINATE_TWO = 2;
    private static final int COORDINATE_THREE = 3;
    private static final int COORDINATE_FOUR = 4;
    private static final int COORDINATE_FIVE = 5;
    private static final int SIZE_ONE = 1;
    private static final int SIZE_TWO = 2;

    ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
    Human human = Human.getInstance();
    Field field = human.getField();
    Cell[][] cells = field.getCells();

    @Test
    @DisplayName("when player shoots at cell with state SEA")
    public void whenPlayerShootsAtCellWithStateSea() {
        cells[COORDINATE_ONE][COORDINATE_ONE].setState(CellState.SEA);

        ShootResult shootResult = human.getShot(COORDINATE_ONE, COORDINATE_ONE);
        then("MISS").isEqualTo(shootResult.toString());
    }

    @Test
    @DisplayName("when player shoots at cell with state INJURE")
    public void whenPlayerShootsAtCellWithStateInjure() {
        cells[COORDINATE_TWO][COORDINATE_TWO].setState(CellState.INJURE);

        ShootResult shootResult = human.getShot(COORDINATE_TWO, COORDINATE_TWO);
        assertNull(shootResult);
    }

    @Test
    @DisplayName("when player shoots at cell with state SHIP to injure it")
    public void whenPlayerShootsAtCellWithStateShipToInjureIt() {
        arrangementOfShips.setPlacementField(human.getField().getCells());

        cells[COORDINATE_THREE][COORDINATE_THREE].setState(CellState.SHIP);
        arrangementOfShips.createShip(COORDINATE_THREE, COORDINATE_THREE, SIZE_TWO, ArrangementOfShips.Orientation.VERTICAL);

        ShootResult shootResult = human.getShot(COORDINATE_THREE, COORDINATE_THREE);
        then("INJURE").isEqualTo(shootResult.toString());
    }

    @Test
    @DisplayName("when player shoots at cell with state SHIP to kill it")
    public void whenPlayerShootsAtCellWithStateShipToKillIt() {
        arrangementOfShips.setPlacementField(human.getField().getCells());

        cells[COORDINATE_THREE][COORDINATE_THREE].setState(CellState.SHIP);
        arrangementOfShips.createShip(COORDINATE_THREE, COORDINATE_THREE, SIZE_ONE, ArrangementOfShips.Orientation.VERTICAL);

        ShootResult shootResult = human.getShot(COORDINATE_THREE, COORDINATE_THREE);
        then("KILL").isEqualTo(shootResult.toString());
    }

    @Test
    @DisplayName("when player shoots at cell with state MISS")
    public void whenPlayerShootsAtCellWithStateMiss() {
        cells[COORDINATE_FOUR][COORDINATE_FOUR].setState(CellState.MISS);

        ShootResult shootResult = human.getShot(COORDINATE_FOUR, COORDINATE_FOUR);
        assertNull(shootResult);
    }

    @Test
    @DisplayName("when player shoots at cell with state KILL")
    public void whenPlayerShootsAtCellWithStateKill() {
        cells[COORDINATE_FIVE][COORDINATE_FIVE].setState(CellState.KILL);

        ShootResult shootResult = human.getShot(COORDINATE_FIVE, COORDINATE_FIVE);
        assertNull(shootResult);
    }
}