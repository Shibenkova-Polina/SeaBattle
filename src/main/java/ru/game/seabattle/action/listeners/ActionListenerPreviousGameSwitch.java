package ru.game.seabattle.action.listeners;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.process.ArrangementOfShips;
import ru.game.seabattle.process.Game;
import ru.game.seabattle.database.MyDataBase;
import ru.game.seabattle.players.Computer;
import ru.game.seabattle.players.Human;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ActionListenerPreviousGameSwitch implements ActionListener {
    private final CardLayout cardLayout;
    private final String panelNameToSwitchTo;
    private final Container container;

    ArrangementOfShips arrangementOfShips = ArrangementOfShips.getInstance();
    MyDataBase myDataBase = MyDataBase.getInstance();

    public ActionListenerPreviousGameSwitch(CardLayout cardLayout, Container container, String panelNameToSwitchTo) {
        this.cardLayout = cardLayout;
        this.panelNameToSwitchTo = panelNameToSwitchTo;
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cardLayout.show(container, panelNameToSwitchTo);

        Field humanField = Game.getInstance().getHuman().getField();
        Field computerField = Game.getInstance().getComputer().getField();

        resumeFieldHuman(humanField);
        resumeFieldComputer(computerField);
    }

    public void resumeFieldHuman(Field field) {
        if (field == null) {
            return;
        }

        String[] data_2 = myDataBase.getHumanShips();

        arrangementOfShips.setPlacementField(field.getCells());
        arrangementOfShips.createPrevShips(field, data_2);

        Map<String, String> data = myDataBase.getHumanCells();
        String str = data.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[10]));
        Human.getInstance().setShipsToKill(num);
        setCellCtate(field, data);
    }

    public void resumeFieldComputer(Field field) {
        if (field == null) {
            return;
        }

        MyDataBase myDataBase = MyDataBase.getInstance();
        String[] data_2 = myDataBase.getComputerShips();

        arrangementOfShips.setPlacementField(field.getCells());
        arrangementOfShips.createPrevShips(field, data_2);

        Map<String, String> data = myDataBase.getComputerCells();
        String str = data.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[10]));
        Computer.getInstance().setShipsToKill(num);
        setCellCtate(field, data);
    }

    private void setCellCtate(Field field, Map<String, String> data) {
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