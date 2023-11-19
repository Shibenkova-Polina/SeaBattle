package ru.game.seabattle.action;

import ru.game.seabattle.gui.GameInterface;
import ru.game.seabattle.gui.Messages;
import ru.game.seabattle.process.Game;

public class Run implements Runnable {
    private final int SLEEP_TIME = 10;

    @Override
    public void run() {
        Game game = Game.getInstance();
        GameInterface gameInterface = GameInterface.getInstance();
        Messages messages = Messages.getInstance();

        gameInterface.draw();
        messages.getStartMessage();

        while(true) {
            if (game.getNewGame()) {
                game.setNewGame(false);
                game.getComputer().newGame();
                game.getHuman().newGame();
                gameInterface.draw();
                messages.getStartMessage();
            }

            if (game.getComputer().moves()) {
                game.getComputer().shoot();
            }

            if (game.getComputer().getShipsToKill() == 0 || game.getHuman().getShipsToKill() == 0) {
                messages.getWinMessage(game.getHuman().getShipsToKill() == 0);
                break;
            }

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

