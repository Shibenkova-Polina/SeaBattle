package actionListeners;

import gameProcess.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerNewGame implements ActionListener {
    private boolean newGame;
    private Thread lastGame;

    public ActionListenerNewGame(boolean newGame, Thread lastGame) {
        this.newGame = newGame;
        this.lastGame = lastGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Game.newGame = true;
        Run run = new Run();

        if (!lastGame.isAlive()) {
            lastGame = new Thread(run);
            lastGame.start();
        }
    }
}