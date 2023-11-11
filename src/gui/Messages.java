package gui;

import players.Computer;
import players.Human;
import players.Player;

public class Messages {

    private static Messages instance;

    public static Messages getInstance() {
        if (instance == null)
            instance = new Messages();
        return instance;
    }

    public void getStartMessage(){
        GameInterface.textLabel.setText("Твой ход");
    }

    public void getMessage(boolean human, Player.ShootResult shootResult) {
        if (shootResult == null) {
            return;
        }

        if (human) {
            switch (shootResult) {
                case MISS:
                    GameInterface.textLabel.setText("Ты промахнулся");
                    break;
                case INJURE:
                    GameInterface.textLabel.setText("Отличный выстрел! Продолжай!");
                    break;
                case KILL:
                    GameInterface.textLabel.setText("Ты уничтожил корабль противника! Поздравляю! Продолжай!");
                    break;
            }
        } else {
            switch (shootResult) {
                case MISS:
                    GameInterface.textLabel.setText("Противник промахнулся. Твой ход");
                    break;
                case INJURE:
                    GameInterface.textLabel.setText("Твой корабль был ранен. Противник продолжает стрельбу...");
                    break;
                case KILL:
                    GameInterface.textLabel.setText("Твой корабль был потоплен. Противник продолжает стрельбу...");
                    break;
            }
        }
    }

    public void getWinMessage(boolean humanWin) {
        if (humanWin) {
            GameInterface.textLabel.setText("Игра окончена. Ты победил! Поздравляю!");
        } else {
            GameInterface.textLabel.setText("Игра окончена. Компьютер победил. Возьми реванш...");
        }
    }
}
