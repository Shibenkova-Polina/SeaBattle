package ru.game.seabattle.action.listeners;

import ru.game.seabattle.database.DataBase;
import ru.game.seabattle.process.ArrangementOfShips;
import ru.game.seabattle.process.Game;
import ru.game.seabattle.action.Run;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerNewGameSwitch implements ActionListener {
    private CardLayout cardLayout;
    private String panelNameToSwitchTo;
    private Container container;

    private Thread lastGame;

    public ActionListenerNewGameSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo, boolean newGame, Thread lastGame) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;

        this.lastGame = lastGame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrangementOfShips.idPlacements = 0;
        DataBase.clearBD();
        DataBase.clearBD_2();
        cardLayout.show(container, panelNameToSwitchTo);

        Game.newGame = true;
        Run run = new Run();

        if (!lastGame.isAlive()) {
            lastGame = new Thread(run);
            lastGame.start();
        }
    }
}
