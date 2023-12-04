package ru.game.seabattle.game;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.players.Human;
import ru.game.seabattle.process.ArrangementOfShips;

import static org.assertj.core.api.BDDAssertions.then;

public class CreateShips_Test {
    private static final int COUNT_OF_SHIPS = 10;

    @Test
    @DisplayName("when program creates and places ships")
    public void whenProgramCreatesAndPlacesShips() {
        ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
        Human human = Human.getInstance();

        arrangementOfShips.setPlacementField(human.getField().getCells());
        arrangementOfShips.createShips(human.getField());
        then(COUNT_OF_SHIPS).isEqualTo(arrangementOfShips.getIdPlacements());
    }
}
