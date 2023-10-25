package actionListeners;

import gui.GameInterface;
import gui.Messages;
import gameProcess.Game;

public class Run implements Runnable {

    @Override
    public void run() {
        GameInterface.getInstance().draw();
        Messages.getInstance().getStartMessage();
        boolean shootComanded = false;

        while(true){
            if (Game.newGame) {
                Game.newGame = false;
                Game.computer.newGame();
                Game.human.newGame();
                GameInterface.getInstance().draw();
                Messages.getInstance().getStartMessage();
            }

            if (Game.computer.moves() && !shootComanded) {
                Game.computer.shoot();
                shootComanded = true;
            } else {
                shootComanded = false;
            }

            if (Game.computer.getShipsToKill() == 0 || Game.human.getShipsToKill() == 0) {
                Messages.getInstance().getWinMessage(Game.human.getShipsToKill() == 0);

                break;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

