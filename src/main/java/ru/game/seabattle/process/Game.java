package ru.game.seabattle.process;

import ru.game.seabattle.action.Run;
import ru.game.seabattle.players.Computer;
import ru.game.seabattle.players.Human;

public class Game {
    private Thread lastGame;
    private boolean newGame = false;

    private Computer computer = Computer.getInstance();
    private Human human = Human.getInstance();
    private static Game instance;

    private Game() {
        human.setOpponent(computer);
        computer.setOpponent(human);
    }

    public Computer getComputer() {
        return computer;
    }

    public Human getHuman() {
        return human;
    }

    public boolean getNewGame() {
        return newGame;
    }

    public void setNewGame(boolean game) {
        newGame = game;
    }

    public Thread getLastGame() {
        return lastGame;
    }

    public void playGame() {
        Run run = new Run();
        lastGame = new Thread(run);
        lastGame.start();
    }

    public synchronized static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }

        return instance;
    }
}