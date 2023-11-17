package ru.game.seabattle.gui;

import ru.game.seabattle.action.listeners.*;
import ru.game.seabattle.elements.Cell;
import ru.game.seabattle.elements.Field;
import ru.game.seabattle.process.Game;

import javax.swing.*;
import java.awt.*;

public class GameInterface extends JFrame {
    private static final int INTERFACE_WIDTH = 1100;
    private static final int INTERFACE_HEIGHT = 800;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 100;
    private static final int PANEL_FIELD_SIZE = 400;
    private static final int WINDOW_GRID_SIZE = 2;

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    private JPanel panelStart = new JPanel();
    private JPanel player1Field = new JPanel();
    private JPanel player2Field = new JPanel();
    private JPanel gamePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();

    private JPanel buttonAlignment = new JPanel(new GridBagLayout());
    private GridBagConstraints constraints = new GridBagConstraints();

    private final ImageIcon ship = new ImageIcon((GameInterface.class.getResource("icons/ship.png")));
    private final ImageIcon kill = new ImageIcon((GameInterface.class.getResource("icons/kill.png")));
    private final ImageIcon injure = new ImageIcon(GameInterface.class.getResource("icons/injure.png"));
    private final ImageIcon miss = new ImageIcon(GameInterface.class.getResource("icons/miss.png"));
    private final ImageIcon sea = new ImageIcon(GameInterface.class.getResource("icons/sea.png"));
    private final ImageIcon picture = new ImageIcon(GameInterface.class.getResource("icons/picture.png"));

    private static GameInterface instance;

    public static JLabel textLabel = new JLabel();

    private GameInterface() {
        super("Морской бой");
        setSize(INTERFACE_WIDTH, INTERFACE_HEIGHT);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton start = new JButton("Начать новую игру");
        start.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        JButton exit = new JButton("Выйти");
        exit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        JButton resume = new JButton("Продолжить прошлую игру");
        resume.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        JLabel picture = new JLabel(this.picture);
        panelStart.add(start);
        panelStart.add(resume);
        panelStart.add(exit);
        panelStart.add(picture);

        cardPanel.add(panelStart, "start");
        cardPanel.add(gamePanel, "play");
        add(cardPanel);

        ActionListenerExit actionListenerExit = new ActionListenerExit();
        exit.addActionListener(actionListenerExit);
        resume.addActionListener(new ActionListenerPreviousGameSwitch(cardLayout, cardPanel, "play"));
        start.addActionListener(new ActionListenerNewGameSwitch(cardLayout, cardPanel, "play", Game.newGame, Game.game.getLastGame()));

        JButton backToMenu = new JButton("Вернуться в меню");
        backToMenu.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        backToMenu.addActionListener(new ActionListenerSwitch(cardLayout, cardPanel, "start"));

        initializeFields();

        textLabel = new JLabel("Мой ход", SwingConstants.CENTER);

        JButton newGameButton = new JButton("Новая игра");
        newGameButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        ActionListenerNewGame actionListenerNewGame = new ActionListenerNewGame(Game.newGame, Game.game.getLastGame());
        newGameButton.addActionListener(actionListenerNewGame);

        gamePanel.setLayout(new GridLayout(WINDOW_GRID_SIZE, WINDOW_GRID_SIZE));
        gamePanel.add(player1Field);
        gamePanel.add(textLabel);
        gamePanel.add(player2Field);
        gamePanel.add(buttonAlignment);

        buttonPanel.add(newGameButton, BorderLayout.CENTER);
        buttonPanel.add(backToMenu, BorderLayout.CENTER);
        buttonAlignment.add(buttonPanel, constraints);

        setVisible(true);
    }

    private void initializeFields() {
        initializeField(player1Field, Game.computer.getField());
        initializeField(player2Field, Game.human.getField());
    }

    private void initializeField(JPanel jPanel, Field field) {
        if (field == null) {
            return;
        }

        jPanel.setLayout(new GridLayout(Field.FIELD_SIZE + 1, Field.FIELD_SIZE + 1));
        jPanel.setMaximumSize(new Dimension(PANEL_FIELD_SIZE, PANEL_FIELD_SIZE));

        Cell[][] cells = field.getCells();

        String alphabet = "АБВГДЕЖЗИК";

        for (int i = - 1; i < Field.FIELD_SIZE; i++) {
            for (int j = - 1; j < Field.FIELD_SIZE; j++) {
                if (i == - 1 && j == - 1) {
                    jPanel.add(new JButton(" "));
                } else if (i == - 1) {
                    char letter = alphabet.charAt(j);
                    jPanel.add(new JButton(Character.toString(letter)));
                } else if (j == - 1) {
                    jPanel.add(new JButton(Integer.toString(i + 1)));
                } else {
                    cells[j][i].setIcon(sea);
                    jPanel.add(cells[j][i]);
                }
            }
        }
    }

    public void draw() {
        Field humanField = Game.human.getField();
        Field computerField = Game.computer.getField();

        updateField(humanField, false);
        updateField(computerField, true);
    }

    private void updateField(Field field, boolean computerField) {
        if (field == null) {
            return;
        }

        Cell[][] cells = field.getCells();

        for (int i = 0; i < Field.FIELD_SIZE; i++) {
            for (int j = 0; j < Field.FIELD_SIZE; j++) {
                Cell cell = cells[j][i];

                if (computerField){
                    updateComputerCell(cell);
                } else {
                    updateHumanCell(cell);
                }
            }
        }
    }

    private void updateComputerCell(Cell cell) {
        Cell.CellState state = cell.getState();

        if (state != null) {
            switch (state) {
                case SEA:
                    cell.setIcon(sea);
                    break;
                case INJURE:
                    cell.setIcon(injure);
                    break;
                case KILL:
                    cell.setIcon(kill);
                    break;
                case MISS:
                    cell.setIcon(miss);
                    break;
            }
        }
    }

    private void updateHumanCell(Cell cell) {
        Cell.CellState state = cell.getState();

        if (state != null) {
            switch (state) {
                case SEA:
                    cell.setIcon(sea);
                    break;
                case MISS:
                    cell.setIcon(miss);
                    break;
                case SHIP:
                    cell.setIcon(ship);
                    break;
                case INJURE:
                    cell.setIcon(injure);
                    break;
                case KILL:
                    cell.setIcon(kill);
                    break;
            }
        }
    }

    public static GameInterface getInstance() {
        if (instance == null) {
            instance = new GameInterface();
        }

        return instance;
    }
}
