package ru.game.seabattle.database;

import ru.game.seabattle.elements.Field;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String username = "postgres";
    private static String password = "Polina/2023";

    private static PreparedStatement pstmt;

    public static void clearBD() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM cells");
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }

    public static void clearBD_2() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM ships");
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }

    public static void fillBD(int id, String player, StringBuilder states, int i, int shipsKill) {
        states.append(" ");
        states.append(shipsKill);

        String sql = "INSERT into cells values(" + id + ", '" + player + "', '" + states + "' , " + i + ")";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }

    public static void fillBD_2(int id, String player, int size, int x, int y, String orient) {
        String coordX = Integer.toString(x);
        String coordY = Integer.toString(y);
        String sizeStr = Integer.toString(size);
        String data = coordX + " " + coordY + " " + sizeStr + " " + orient;

        String sql = "INSERT into ships values(" + id + ", '" + player + "', '" + data + "' )";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }

    public static Map<String, String> getDataHuman() {
        Map<String, String> data = new HashMap<>();
        String sql = "SELECT CellsStates, LineNumber FROM cells where Player = 'Human'";
        fillData(sql, data);
        return data;
    }

    public static Map<String, String> getDataComputer() {
        Map<String, String> data = new HashMap<>();
        String sql = "SELECT CellsStates, LineNumber FROM cells where Player = 'Computer'";
        fillData(sql, data);
        return data;
    }

    private static void fillData(String sql, Map<String, String> data) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(sql);

            while (set.next()) {
                data.put(set.getString("LineNumber"), set.getString("CellsStates"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static String[] getDataHuman_2() {
        String[] data = new String[Field.FIELD_SIZE];
        String sql = "SELECT Data FROM ships where Player = 'Human'";

        fillData_2(sql, data);
        return data;
    }

    public static String[] getDataComputer_2() {
        String[] data = new String[Field.FIELD_SIZE];
        String sql = "SELECT Data FROM ships where Player = 'Computer'";

        fillData_2(sql, data);
        return data;
    }

    private static void fillData_2(String sql, String[] data) {
        int i = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
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
