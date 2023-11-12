package main.java.actionListeners;

import main.java.dataBase.DataBase;
import main.java.gameElements.Cell;
import main.java.gameElements.Field;
import main.java.gameProcess.ArrangementOfShips;
import main.java.gameProcess.Game;
import players.Computer;
import players.Human;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ActionListenerPreviousGameSwitch implements ActionListener {
    private final CardLayout cardLayout;
    private final String panelNameToSwitchTo;
    private final Container container;

    public ActionListenerPreviousGameSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(container, panelNameToSwitchTo);

        Field humanField = Game.human.getField();
        Field computerField = Game.computer.getField();

        resumeFieldHuman(humanField);
        resumeFieldComputer(computerField);
    }

    public void resumeFieldHuman(Field field) {
        if (field == null) {
            return;
        }

        String[] data_2 = DataBase.getDataHuman_2();

        ArrangementOfShips.setPlacementField(field.getCells());
        ArrangementOfShips.createPrevShips(field, data_2);

        Map<String, String> data = DataBase.getDataHuman();
        String str = data.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[10]));
        Human.setShipsToKill(num);
        setCellCtate(field, data);
    }

    public void resumeFieldComputer(Field field) {
        if (field == null) {
            return;
        }

        String[] data_2 = DataBase.getDataComputer_2();

        ArrangementOfShips.setPlacementField(field.getCells());
        ArrangementOfShips.createPrevShips(field, data_2);

        Map<String, String> data = DataBase.getDataComputer();
        String str = data.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[10]));
        Computer.setShipsToKill(num);
        setCellCtate(field, data);
    }

    private static void setCellCtate(Field field, Map<String, String> data) {
        Cell[][] cells = field.getCells();

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            String statesStr = data.get(Integer.toString(i));
            if (statesStr != null) {
                String[] states = statesStr.trim().split(" ");
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    Cell cell = cells[j][i];
                    Cell.CellState cellState = Cell.CellState.valueOf(states[j]);
                    cell.setState(cellState);
                }
            }
        }
    }
}