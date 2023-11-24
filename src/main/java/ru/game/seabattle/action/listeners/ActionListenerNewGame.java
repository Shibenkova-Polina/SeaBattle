package ru.game.seabattle.action.listeners;

import ru.game.seabattle.action.Run;
import ru.game.seabattle.process.ArrangementOfShips;
import ru.game.seabattle.process.Game;
import ru.game.seabattle.database.MyDataBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerNewGame implements ActionListener {
    private Thread lastGame;

    public ActionListenerNewGame(Thread lastGame) {
        this.lastGame = lastGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrangementOfShips.getInstance().setIdPlacements(0);
        MyDataBase myDataBase = MyDataBase.getInstance();

        myDataBase.clearShips();

        Game.getInstance().setNewGame(true);
        Run run = new Run();

        if (!lastGame.isAlive()) {
            lastGame = new Thread(run);
            lastGame.start();
        }
    }
}