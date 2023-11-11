import dataBase.DataBase;
import gameProcess.Game;

public class Main {
    public static void main(String[] args) {
        DataBase.clearBD();
        Game.playGame();
    }
}