package main.java.actionListeners;

import main.java.dataBase.DataBase;
import main.java.gameProcess.ArrangementOfShips;
import main.java.gameProcess.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerNewGame implements ActionListener {
    private Thread lastGame;

    public ActionListenerNewGame(boolean newGame, Thread lastGame) {
        this.lastGame = lastGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrangementOfShips.idPlacements = 0;
        DataBase.clearBD();
        DataBase.clearBD_2();

        Game.newGame = true;
        Run run = new Run();

        if (!lastGame.isAlive()) {
            lastGame = new Thread(run);
            lastGame.start();
        }
    }
}