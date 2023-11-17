package ru.game.seabattle;

import ru.game.seabattle.process.ArrangementOfShips;
import ru.game.seabattle.process.Game;

public class Main {
    public static void main(String[] args) {
        ArrangementOfShips.idPlacements = 0;

        Game.playGame();
    }
}