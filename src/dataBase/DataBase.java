package dataBase;

import java.sql.*;

public class DataBase {
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String username = "postgres";
    private static String password = "Polina/2023";

    private static PreparedStatement pstmt;
    private static String alphabet = "АБВГДЕЖЗИК";

    public static void clearBD() {
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM database");
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }

    public static void fill(int id, int num, String str, int size, int x, int y, String orient) {
        String coordX = Integer.toString(x + 1);
        char letter = alphabet.charAt(y);
        String coordY = Character.toString(letter);
        String coord = coordY + coordX;

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            pstmt = conn.prepareStatement("INSERT into database values(" + id + ", " + num + ", '" + str + "', " + size + ", '" + coord + "', '" + orient + "')");
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to DB: " + ex.getMessage());
        }
    }
}
