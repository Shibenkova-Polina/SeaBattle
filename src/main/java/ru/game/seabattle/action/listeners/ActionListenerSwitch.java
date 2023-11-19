package ru.game.seabattle.action.listeners;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.process.Game;
import ru.game.seabattle.database.MyDataBase;
import ru.game.seabattle.persistence.DBPersistence;
import ru.game.seabattle.players.Computer;
import ru.game.seabattle.players.Human;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;

public class ActionListenerSwitch implements ActionListener {
    private final CardLayout cardLayout;
    private final String panelNameToSwitchTo;
    private final Container container;
    private int id = 1;
    DBPersistence dbPersistence = new DBPersistence();

    public ActionListenerSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(container, panelNameToSwitchTo);

        Game game = Game.getInstance();

        Field humanField = game.getHuman().getField();
        Field computerField = game.getComputer().getField();

        MyDataBase.getInstance().clearCells();
        fillDB(humanField, false);
        fillDB(computerField, true);

        this.id = 1;
    }

    private void fillDB(Field field, boolean computerField) {
        if (field == null) {
            return;
        }

        Cell[][] cells = field.getCells();

        if (computerField) {
            StringBuilder states = new StringBuilder();
            for (int i = 0; i < Field.FIELD_SIZE; i++) {
                states.setLength(0);
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    Cell cell = cells[j][i];
                    String cellState = cell.getState().toString();

                    states.append(" ");
                    states.append(cellState);
                }
                dbPersistence.createCells(id, "Computer", states, i, Computer.getInstance().getShipsToKill());
                this.id += 1;
            }
        } else {
            StringBuilder states = new StringBuilder();
            for (int i = 0; i < Field.FIELD_SIZE; i++) {
                states.setLength(0);
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    Cell cell = cells[j][i];
                    String cellState = cell.getState().toString();

                    states.append(" ");
                    states.append(cellState);
                }
                dbPersistence.createCells(id, "Human", states, i, Human.getInstance().getShipsToKill());
                this.id += 1;
            }
        }
    }
}