package ru.game.seabattle.gui;

import ru.game.seabattle.players.Player;

public class Messages {
    private static Messages instance;
    private final GameInterface gameInterface = GameInterface.getInstance();

    public static Messages getInstance() {
        if (instance == null)
            instance = new Messages();
        return instance;
    }

    public void getStartMessage(){
        gameInterface.setTextLabel("Твой ход");
    }

    public void getMessage(boolean human, Player.ShootResult shootResult) {
        if (shootResult == null) {
            return;
        }

        if (human) {
            switch (shootResult) {
                case MISS:
                    gameInterface.setTextLabel("Ты промахнулся");
                    break;
                case INJURE:
                    gameInterface.setTextLabel("Отличный выстрел! Продолжай!");
                    break;
                case KILL:
                    gameInterface.setTextLabel("Ты уничтожил корабль противника! Поздравляю! Продолжай!");
                    break;
            }
        } else {
            switch (shootResult) {
                case MISS:
                    gameInterface.setTextLabel("Противник промахнулся. Твой ход");
                    break;
                case INJURE:
                    gameInterface.setTextLabel("Твой корабль был ранен. Противник продолжает стрельбу...");
                    break;
                case KILL:
                    gameInterface.setTextLabel("Твой корабль был потоплен. Противник продолжает стрельбу...");
                    break;
            }
        }
    }

    public void getWinMessage(boolean humanWin) {
        if (humanWin) {
            gameInterface.setTextLabel("Игра окончена. Ты победил! Поздравляю!");
        } else {
            gameInterface.setTextLabel("Игра окончена. Компьютер победил. Возьми реванш...");
        }
    }
}
