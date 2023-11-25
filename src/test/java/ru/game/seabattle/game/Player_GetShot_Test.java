package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.CellState;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.players.Human;
import ru.game.seabattle.players.Player;
import ru.game.seabattle.process.ArrangementOfShips;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Player_GetShot_Test {

    @Test
    @DisplayName("when human cells are entered into DB")
    public void whenHumanCellsAreEnteredIntoDB() {
        ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
        Human human = Human.getInstance();
        arrangementOfShips.setPlacementField(human.getField().getCells());

        Field field = human.getField();
        Cell[][] cells = field.getCells();

        cells[1][1].setState(CellState.SEA);
        cells[2][2].setState(CellState.INJURE);
        cells[3][3].setState(CellState.SHIP);
        arrangementOfShips.createShip(3, 3, 1, ArrangementOfShips.Orientation.VERTICAL);

        Player.ShootResult shootResult = human.getShot(1, 1);
        then("MISS").isEqualTo(shootResult.toString());

        shootResult = human.getShot(2, 2);
        assertNull(shootResult);

        shootResult = human.getShot(3, 3);
        then("KILL").isEqualTo(shootResult.toString());
    }
}
