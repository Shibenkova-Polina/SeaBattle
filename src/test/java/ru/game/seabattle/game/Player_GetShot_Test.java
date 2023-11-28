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
    ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
    Human human = Human.getInstance();
    Field field = human.getField();
    Cell[][] cells = field.getCells();

    @Test
    @DisplayName("when player shoots at cell with state SEA")
    public void whenPlayerShootsAtCellWithStateSea() {
        cells[1][1].setState(CellState.SEA);

        ShootResult shootResult = human.getShot(1, 1);
        then("MISS").isEqualTo(shootResult.toString());
    }

    @Test
    @DisplayName("when player shoots at cell with state INJURE")
    public void whenPlayerShootsAtCellWithStateInjure() {
        cells[2][2].setState(CellState.INJURE);

        ShootResult shootResult = human.getShot(2, 2);
        assertNull(shootResult);
    }

    @Test
    @DisplayName("when player shoots at cell with state SHIP")
    public void whenPlayerShootsAtCellWithStateShip() {
        arrangementOfShips.setPlacementField(human.getField().getCells());

        cells[3][3].setState(CellState.SHIP);
        arrangementOfShips.createShip(3, 3, 1, ArrangementOfShips.Orientation.VERTICAL);

        ShootResult shootResult = human.getShot(3, 3);
        then("KILL").isEqualTo(shootResult.toString());

        arrangementOfShips.createShip(3, 3, 2, ArrangementOfShips.Orientation.VERTICAL);

        shootResult = human.getShot(3, 3);
        then("INJURE").isEqualTo(shootResult.toString());
    }

    @Test
    @DisplayName("when player shoots at cell with state MISS")
    public void whenPlayerShootsAtCellWithStateMiss() {
        cells[4][4].setState(CellState.MISS);

        ShootResult shootResult = human.getShot(4, 4);
        assertNull(shootResult);
    }

    @Test
    @DisplayName("when player shoots at cell with state KILL")
    public void whenPlayerShootsAtCellWithStateKill() {
        cells[5][5].setState(CellState.KILL);

        ShootResult shootResult = human.getShot(5, 5);
        assertNull(shootResult);
    }
}