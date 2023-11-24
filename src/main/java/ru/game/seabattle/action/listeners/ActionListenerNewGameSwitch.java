package ru.game.seabattle.action.listeners;

import ru.game.seabattle.action.Run;
import ru.game.seabattle.process.ArrangementOfShips;
import ru.game.seabattle.process.Game;
import ru.game.seabattle.database.MyDataBase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerNewGameSwitch implements ActionListener {
    private CardLayout cardLayout;
    private String panelNameToSwitchTo;
    private Container container;

    private Thread lastGame;

    public ActionListenerNewGameSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo, Thread lastGame) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;
        this.lastGame = lastGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrangementOfShips.getInstance().setIdPlacements(0);
        MyDataBase myDataBase = MyDataBase.getInstance();

        myDataBase.clearShips();
        cardLayout.show(container, panelNameToSwitchTo);

        Game.getInstance().setNewGame(true);
        Run run = new Run();

        if (!lastGame.isAlive()) {
            lastGame = new Thread(run);
            lastGame.start();
        }
    }
}
