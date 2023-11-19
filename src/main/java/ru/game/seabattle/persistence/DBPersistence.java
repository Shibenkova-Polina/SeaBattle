package ru.game.seabattle.persistence;

import ru.game.seabattle.database.MyDataBase;

public class DBPersistence {
    private final MyDataBase db = MyDataBase.getInstance();

    public void createCells(int id, String player, StringBuilder states, int i, int shipsKill) {
        states.append(" ");
        states.append(shipsKill);

        String sql = """
                insert into cells
                (id, player, cellsstates, linenumber)
                values
                (%d, '%s', '%s', %d)
                """;
        db.execute(String.format(sql, id, player, states, i));
    }

    public void createShips(int id, String player, int size, int x, int y, String orient) {
        String coordX = Integer.toString(x);
        String coordY = Integer.toString(y);
        String sizeStr = Integer.toString(size);
        String data = coordX + " " + coordY + " " + sizeStr + " " + orient;

        String sql = """
                insert into ships
                (id, player, data)
                values
                (%d, '%s', '%s')
                """;
        db.execute(String.format(sql, id, player, data));
    }
}
