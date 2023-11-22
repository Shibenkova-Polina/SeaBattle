package ru.game.seabattle.database;

import ru.game.seabattle.config.DatabaseProperties;
import ru.game.seabattle.config.PropertiesFactory;
import ru.game.seabattle.elements.Field;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MyDataBase {
    private static MyDataBase instanse;
    private final DatabaseProperties properties = PropertiesFactory.getProperties();

    private MyDataBase() {
        init();
    }

    public synchronized static MyDataBase getInstance() {
        if (instanse == null) {
            instanse = new MyDataBase();
        }

        return instanse;
    }

    private void init() {
        boolean tableCells = false;
        boolean tableShips = false;

        try {
            Connection connection = connect();
             DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "cells", null);

            if (rs.next()) {
                tableCells = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            Connection connection = connect();
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "ships", null);

            if (rs.next()) {
                tableShips = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        if (!tableShips) {
            createShips();
        }

        if (!tableCells) {
            createCells();
        }
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(
                properties.getUrl(),
                properties.getLogin(),
                properties.getPassword()
        );
    }

    private void createShips() {
        String sql = """
               create table ships
               (
                   id int not null primary key,
                   Player text not null,
                   Data text not null
               )
               """;
        execute(sql);
    }

    private void createCells() {
        String sql = """
                create table cells
                (
                    id int not null primary key,
                    Player text not null,
                    CellsStates text not null,
                    LineNumber text not null
                );
                """;
        execute(sql);
    }

    public void execute(String sql) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void clearCells() {
        String sql = "DELETE FROM cells";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void clearShips() {
        String sql = "DELETE FROM ships";
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Map<String, String> getHumanCells() {
        Map<String, String> data = new HashMap<>();
        String sql = "SELECT CellsStates, LineNumber FROM cells where Player = 'Human'";
        conectCells(sql, data);
        return data;
    }

    public Map<String, String> getComputerCells() {
        Map<String, String> data = new HashMap<>();
        String sql = "SELECT CellsStates, LineNumber FROM cells where Player = 'Computer'";
        conectCells(sql, data);
        return data;
    }

    private void conectCells(String sql, Map<String, String> data) {
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);

            while (set.next()) {
                data.put(set.getString("LineNumber"), set.getString("CellsStates"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String[] getHumanShips() {
        String[] data = new String[Field.FIELD_SIZE];
        String sql = "SELECT Data FROM ships where Player = 'Human'";

        connectShips(sql, data);
        return data;
    }

    public String[] getComputerShips() {
        String[] data = new String[Field.FIELD_SIZE];
        String sql = "SELECT Data FROM ships where Player = 'Computer'";

        connectShips(sql, data);
        return data;
    }

    private void connectShips(String sql, String[] data) {
        int i = 0;
        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);

            while (set.next()) {
                data[i] = set.getString("Data");
                i += 1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
