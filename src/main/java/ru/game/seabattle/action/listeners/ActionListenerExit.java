package ru.game.seabattle.action.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerExit implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit (0);
    }
}