package ru.game.seabattle.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.seabattle.persistence.DBPersistence;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

public class MyDataBase_Test {
    private final MyDataBase myDataBase = new MyDataBase();
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
        then(data[0]).isEqualTo("1 6 4 VERTICAL");
        then(data[1]).isEqualTo("4 0 3 HORIZONTAL");
        then(data[2]).isEqualTo("6 0 3 HORIZONTAL");
        then(data[3]).isEqualTo("0 2 2 HORIZONTAL");
        then(data[4]).isEqualTo("3 8 2 VERTICAL");
        then(data[5]).isEqualTo("7 4 2 VERTICAL");
        then(data[6]).isEqualTo("2 2 1 VERTICAL");
        then(data[7]).isEqualTo("9 0 1 VERTICAL");
        then(data[8]).isEqualTo("9 6 1 VERTICAL");
        then(data[9]).isEqualTo("9 9 1 HORIZONTAL");

        myDataBase.clearShips();
    }

    @Test
    @DisplayName("when human ships are entered into DB")
    public void whenHumanShipsAreEnteredIntoDB() {
        myDataBase.clearShips();
        addElementForTestShipsHuman();

        String[] data = myDataBase.getHumanShips();
        then(data[0]).isEqualTo("0 5 4 HORIZONTAL");
        then(data[1]).isEqualTo("6 5 3 HORIZONTAL");
        then(data[2]).isEqualTo("4 1 3 HORIZONTAL");
        then(data[3]).isEqualTo("7 0 2 VERTICAL");
        then(data[4]).isEqualTo("2 5 2 VERTICAL");
        then(data[5]).isEqualTo("3 8 2 VERTICAL");
        then(data[6]).isEqualTo("8 4 1 HORIZONTAL");
        then(data[7]).isEqualTo("2 3 1 VERTICAL");
        then(data[8]).isEqualTo("0 2 1 VERTICAL");
        then(data[9]).isEqualTo("2 0 1 HORIZONTAL");

        myDataBase.clearShips();
    }

    @Test
    @DisplayName("when cells are removed from DB")
    public void whenCellsAreRemovedFromDB() {
        myDataBase.clearCells();
        addElementForTestCellsHuman();

        Map<String, String> firstData = myDataBase.getHumanCells();
        then(firstData.size()).isEqualTo(1);

        myDataBase.clearCells();
        Map<String, String> secondData = myDataBase.getHumanCells();
        then(secondData.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("when ships are removed from DB")
    public void whenShipsAreRemovedFromDB() {
        myDataBase.clearShips();
        addElementForTestShipsComputer();

        String[] firstData = myDataBase.getComputerShips();
        then(firstData[0]).isEqualTo("1 6 4 VERTICAL");

        myDataBase.clearShips();
        String[] secondData = myDataBase.getComputerShips();
        then(secondData[0]).isNull();
    }

    private void addElementForTestCellsHuman() {
        String statesStr = "SEA SEA SEA MISS SEA SEA SEA SEA SEA MISS";
        StringBuilder states = new StringBuilder(statesStr);
        dbPersistence.createCells(10, "Human", states, 9, 8);
    }

    private void addElementForTestCellsComputer() {
        String statesStr = "SEA SEA MISS MISS MISS MISS MISS MISS MISS KILL";
        StringBuilder states = new StringBuilder(statesStr);
        dbPersistence.createCells(10, "Computer", states, 2, 5);
    }

    private void addElementForTestShipsComputer() {
        dbPersistence.createShips(1, "Computer", 4, 1, 6, "VERTICAL");
        dbPersistence.createShips(2, "Computer", 3, 4, 0, "HORIZONTAL");
        dbPersistence.createShips(3, "Computer", 3, 6, 0, "HORIZONTAL");
        dbPersistence.createShips(4, "Computer", 2, 0, 2, "HORIZONTAL");
        dbPersistence.createShips(5, "Computer", 2, 3, 8, "VERTICAL");
        dbPersistence.createShips(6, "Computer", 2, 7, 4, "VERTICAL");
        dbPersistence.createShips(7, "Computer", 1, 2, 2, "VERTICAL");
        dbPersistence.createShips(8, "Computer", 1, 9, 0, "VERTICAL");
        dbPersistence.createShips(9, "Computer", 1, 9, 6, "VERTICAL");
        dbPersistence.createShips(10, "Computer", 1, 9, 9, "HORIZONTAL");
    }

    private void addElementForTestShipsHuman() {
        dbPersistence.createShips(1, "Human", 4, 0, 5, "HORIZONTAL");
        dbPersistence.createShips(2, "Human", 3, 6, 5, "HORIZONTAL");
        dbPersistence.createShips(3, "Human", 3, 4, 1, "HORIZONTAL");
        dbPersistence.createShips(4, "Human", 2, 7, 0, "VERTICAL");
        dbPersistence.createShips(5, "Human", 2, 2, 5, "VERTICAL");
        dbPersistence.createShips(6, "Human", 2, 3, 8, "VERTICAL");
        dbPersistence.createShips(7, "Human", 1, 8, 4, "HORIZONTAL");
        dbPersistence.createShips(8, "Human", 1, 2, 3, "VERTICAL");
        dbPersistence.createShips(9, "Human", 1, 0, 2, "VERTICAL");
        dbPersistence.createShips(10, "Human", 1, 2, 0, "HORIZONTAL");
    }
}