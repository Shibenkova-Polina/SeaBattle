package main.java.actionListeners;

import main.java.dataBase.DataBase;
import main.java.gameElements.Cell;
import main.java.gameElements.Field;
import main.java.gameProcess.Game;
import players.Computer;
import players.Human;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;

public class ActionListenerSwitch implements ActionListener {
    private final CardLayout cardLayout;
    private final String panelNameToSwitchTo;
    private final Container container;
    private int id = 1;

    public ActionListenerSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(container, panelNameToSwitchTo);

        Field humanField = Game.human.getField();
        Field computerField = Game.computer.getField();

        DataBase.clearBD();
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
                DataBase.fillBD(id, "Computer", states, i, Computer.getShipsToKill());
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
                DataBase.fillBD(id, "Human", states, i, Human.getShipsToKill());
                this.id += 1;
            }
        }
    }
}