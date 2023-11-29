package ru.game.seabattle.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.persistence.DBPersistence;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

public class MyDataBase_Test {
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_TWO = 2;
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_FOUR = 4;
    private static final int NUMBER_FIVE = 5;
    private static final int NUMBER_SIX = 6;
    private static final int NUMBER_SEVEN = 7;
    private static final int NUMBER_EIGHT = 8;
    private static final int NUMBER_NINE = 9;
    private static final int NUMBER_TEN = 10;

    private final MyDataBase myDataBase = MyDataBase.getInstance();
    private final DBPersistence dbPersistence = new DBPersistence();

    @Test
    @DisplayName("when human cells are entered into DB")
    public void whenHumanCellsAreEnteredIntoDB() {
        myDataBase.clearCells();
        addElementForTestCellsHuman();

        Map<String, String> data = myDataBase.getHumanCells();
        Map<String, String> expected = new HashMap<>();
        expected.put("9", "SEA SEA SEA MISS SEA SEA SEA SEA SEA MISS 8");

        then(data).isEqualTo(expected);

        myDataBase.clearCells();
    }

    @Test
    @DisplayName("when computer cells are entered into DB")
    public void whenComputerCellsAreEnteredIntoDB() {
        myDataBase.clearCells();
        addElementForTestCellsComputer();

        Map<String, String> data = myDataBase.getComputerCells();
        Map<String, String> expected = new HashMap<>();
        expected.put("2", "SEA SEA MISS MISS MISS MISS MISS MISS MISS KILL 5");

        then(data).isEqualTo(expected);

        myDataBase.clearCells();
    }

    @Test
    @DisplayName("when computer ships are entered into DB")
    public void whenComputerShipsAreEnteredIntoDB() {
        myDataBase.clearShips();
        addElementForTestShipsComputer();

        String[] data = myDataBase.getComputerShips();
        then(data[NUMBER_ZERO]).isEqualTo("1 6 4 VERTICAL");
        then(data[NUMBER_ONE]).isEqualTo("4 0 3 HORIZONTAL");
        then(data[NUMBER_TWO]).isEqualTo("6 0 3 HORIZONTAL");
        then(data[NUMBER_THREE]).isEqualTo("0 2 2 HORIZONTAL");
        then(data[NUMBER_FOUR]).isEqualTo("3 8 2 VERTICAL");
        then(data[NUMBER_FIVE]).isEqualTo("7 4 2 VERTICAL");
        then(data[NUMBER_SIX]).isEqualTo("2 2 1 VERTICAL");
        then(data[NUMBER_SEVEN]).isEqualTo("9 0 1 VERTICAL");
        then(data[NUMBER_EIGHT]).isEqualTo("9 6 1 VERTICAL");
        then(data[NUMBER_NINE]).isEqualTo("9 9 1 HORIZONTAL");

        myDataBase.clearShips();
    }

    @Test
    @DisplayName("when human ships are entered into DB")
    public void whenHumanShipsAreEnteredIntoDB() {
        myDataBase.clearShips();
        addElementForTestShipsHuman();

        String[] data = myDataBase.getHumanShips();
        then(data[NUMBER_ZERO]).isEqualTo("0 5 4 HORIZONTAL");
        then(data[NUMBER_ONE]).isEqualTo("6 5 3 HORIZONTAL");
        then(data[NUMBER_TWO]).isEqualTo("4 1 3 HORIZONTAL");
        then(data[NUMBER_THREE]).isEqualTo("7 0 2 VERTICAL");
        then(data[NUMBER_FOUR]).isEqualTo("2 5 2 VERTICAL");
        then(data[NUMBER_FIVE]).isEqualTo("3 8 2 VERTICAL");
        then(data[NUMBER_SIX]).isEqualTo("8 4 1 HORIZONTAL");
        then(data[NUMBER_SEVEN]).isEqualTo("2 3 1 VERTICAL");
        then(data[NUMBER_EIGHT]).isEqualTo("0 2 1 VERTICAL");
        then(data[NUMBER_NINE]).isEqualTo("2 0 1 HORIZONTAL");

        myDataBase.clearShips();
    }

    @Test
    @DisplayName("when cells are removed from DB")
    public void whenCellsAreRemovedFromDB() {
        myDataBase.clearCells();
        addElementForTestCellsHuman();

        Map<String, String> data = myDataBase.getHumanCells();
        then(data.size()).isEqualTo(1);

        myDataBase.clearCells();
        data = myDataBase.getHumanCells();
        then(data.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("when ships are removed from DB")
    public void whenShipsAreRemovedFromDB() {
        myDataBase.clearShips();
        addElementForTestShipsComputer();

        String[] data = myDataBase.getComputerShips();
        then(data[0]).isEqualTo("1 6 4 VERTICAL");

        myDataBase.clearShips();
        data = myDataBase.getComputerShips();
        then(data[0]).isNull();
    }

    private void addElementForTestCellsHuman() {
        String statesStr = "SEA SEA SEA MISS SEA SEA SEA SEA SEA MISS";
        StringBuilder states = new StringBuilder(statesStr);
        dbPersistence.createCells(NUMBER_TEN, "Human", states, NUMBER_NINE, NUMBER_EIGHT);
    }

    private void addElementForTestCellsComputer() {
        String statesStr = "SEA SEA MISS MISS MISS MISS MISS MISS MISS KILL";
        StringBuilder states = new StringBuilder(statesStr);
        dbPersistence.createCells(NUMBER_TEN, "Computer", states, NUMBER_TWO, NUMBER_FIVE);
    }

    private void addElementForTestShipsComputer() {
        dbPersistence.createShips(NUMBER_ONE, "Computer", NUMBER_FOUR, NUMBER_ONE, NUMBER_SIX, "VERTICAL");
        dbPersistence.createShips(NUMBER_TWO, "Computer", NUMBER_THREE, NUMBER_FOUR, NUMBER_ONE, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_THREE, "Computer", NUMBER_THREE, NUMBER_SIX, NUMBER_ZERO, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_FOUR, "Computer", NUMBER_TWO, NUMBER_ZERO, NUMBER_TWO, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_FIVE, "Computer", NUMBER_TWO, NUMBER_THREE, NUMBER_EIGHT, "VERTICAL");
        dbPersistence.createShips(NUMBER_SIX, "Computer", NUMBER_TWO, NUMBER_SEVEN, NUMBER_FOUR, "VERTICAL");
        dbPersistence.createShips(NUMBER_SEVEN, "Computer", NUMBER_ONE, NUMBER_TWO, NUMBER_TWO, "VERTICAL");
        dbPersistence.createShips(NUMBER_EIGHT, "Computer", NUMBER_ONE, NUMBER_NINE, NUMBER_ZERO, "VERTICAL");
        dbPersistence.createShips(NUMBER_NINE, "Computer", NUMBER_ONE, NUMBER_NINE, NUMBER_SIX, "VERTICAL");
        dbPersistence.createShips(NUMBER_TEN, "Computer", NUMBER_ONE, NUMBER_NINE, NUMBER_NINE, "HORIZONTAL");
    }

    private void addElementForTestShipsHuman() {
        dbPersistence.createShips(NUMBER_ONE, "Human", NUMBER_FOUR, NUMBER_ZERO, NUMBER_FIVE, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_TWO, "Human", NUMBER_THREE, NUMBER_SIX, NUMBER_FIVE, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_THREE, "Human", NUMBER_THREE, NUMBER_FOUR, NUMBER_ONE, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_FOUR, "Human", NUMBER_TWO, NUMBER_SEVEN, NUMBER_ZERO, "VERTICAL");
        dbPersistence.createShips(NUMBER_FIVE, "Human", NUMBER_TWO, NUMBER_TWO, NUMBER_FIVE, "VERTICAL");
        dbPersistence.createShips(NUMBER_SIX, "Human", NUMBER_TWO, NUMBER_THREE, NUMBER_EIGHT, "VERTICAL");
        dbPersistence.createShips(NUMBER_SEVEN, "Human", NUMBER_ONE, NUMBER_EIGHT, NUMBER_FOUR, "HORIZONTAL");
        dbPersistence.createShips(NUMBER_EIGHT, "Human", NUMBER_ONE, NUMBER_TWO, NUMBER_THREE, "VERTICAL");
        dbPersistence.createShips(NUMBER_NINE, "Human", NUMBER_ONE, NUMBER_ZERO, NUMBER_TWO, "VERTICAL");
        dbPersistence.createShips(NUMBER_TEN, "Human", NUMBER_ONE, NUMBER_TWO, NUMBER_ZERO, "HORIZONTAL");
    }
}