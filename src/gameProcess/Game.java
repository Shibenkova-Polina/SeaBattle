package gameProcess;

import actionListeners.Run;
import players.Computer;
import players.Human;

public class Game {

    private Thread lastGame;
    public static boolean newGame = false;

    public static Computer computer = new Computer();
    public static Human human = new Human();

    public static Game game = getInstance();

    private Game() {
        human.setOpponent(computer);
        computer.setOpponent(human);
    }

    public Thread getLastGame() {
        return lastGame;
    }

    public void playGame() {
        Run run = new Run();

        lastGame = new Thread(run);
        lastGame.start();
    }

    public static Game getInstance() {
        if (game == null) {
            game = new Game();
        }

        return game;
    }
}