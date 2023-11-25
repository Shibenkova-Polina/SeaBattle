package ru.game.seabattle.action.listeners;

import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.CellState;
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

    private void resumeFieldHuman(Field field) {
        if (field == null) {
            return;
        }

        String[] dataShips = myDataBase.getHumanShips();

        arrangementOfShips.setPlacementField(field.getCells());
        arrangementOfShips.createPrevShips(field, dataShips);

        Map<String, String> dataCells = myDataBase.getHumanCells();
        String str = dataCells.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[Field.FIELD_SIZE]));
        Human.getInstance().setShipsToKill(num);
        setCellCtate(field, dataCells);
    }

    private void resumeFieldComputer(Field field) {
        if (field == null) {
            return;
        }

        String[] dataShips = myDataBase.getComputerShips();

        arrangementOfShips.setPlacementField(field.getCells());
        arrangementOfShips.createPrevShips(field, dataShips);

        Map<String, String> dataCells = myDataBase.getComputerCells();
        String str = dataCells.get("0");
        int num = Integer.parseInt((str.trim().split(" ")[Field.FIELD_SIZE]));
        Computer.getInstance().setShipsToKill(num);
        setCellCtate(field, dataCells);
    }

    private void setCellCtate(Field field, Map<String, String> data) {
        Cell[][] cells = field.getCells();

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            String statesStr = data.get(Integer.toString(i));
            if (statesStr != null) {
                String[] states = statesStr.trim().split(" ");
                for (int j = 0; j < Field.FIELD_SIZE; j++) {
                    Cell cell = cells[j][i];
                    CellState cellState = CellState.valueOf(states[j]);
                    cell.setState(cellState);
                }
            }
        }
    }
}