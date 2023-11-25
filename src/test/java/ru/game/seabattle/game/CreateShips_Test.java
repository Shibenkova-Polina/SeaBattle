package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.players.Human;
import ru.game.seabattle.process.ArrangementOfShips;

import static org.assertj.core.api.BDDAssertions.then;

public class CreateShips_Test {
    @Test
    @DisplayName("when human cells are entered into DB")
    public void whenHumanCellsAreEnteredIntoDB() {
        ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
        Human human = Human.getInstance();
        arrangementOfShips.setPlacementField(human.getField().getCells());

        arrangementOfShips.createShips(human.getField());
        then(10).isEqualTo(arrangementOfShips.getIdPlacements());
    }
}
