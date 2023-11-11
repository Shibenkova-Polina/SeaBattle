package gameProcess;

import actionListeners.Run;
import players.Computer;
import players.Human;

public class Game {

    private static Thread lastGame;
    public static boolean newGame = false;
    public static int numberOfGame = 1;

    public static Computer computer = new Computer();
    public static Human human = new Human();
    public static Game game = new Game();

    private Game() {
        human.setOpponent(computer);
        computer.setOpponent(human);
    }

    public Thread getLastGame() {
        return lastGame;
    }

    public static void playGame() {
        Run run = new Run();
        lastGame = new Thread(run);
        lastGame.start();
    }
}