package actionListeners;

import gui.GameInterface;
import gui.Messages;
import gameProcess.Game;

public class Run implements Runnable {
    private static final int SLEEP_TIME = 10;

    @Override
    public void run() {
        GameInterface.getInstance().draw();
        Messages.getInstance().getStartMessage();

        while(true) {
            if (Game.newGame) {
                Game.newGame = false;
                Game.computer.newGame();
                Game.human.newGame();
                GameInterface.getInstance().draw();
                Messages.getInstance().getStartMessage();
            }

            if (Game.computer.moves()) {
                Game.computer.shoot();
            }

            if (Game.computer.getShipsToKill() == 0 || Game.human.getShipsToKill() == 0) {
                Messages.getInstance().getWinMessage(Game.human.getShipsToKill() == 0);
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

