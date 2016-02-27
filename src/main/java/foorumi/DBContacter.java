package foorumi;

import java.sql.*;
import java.util.*;

public class DBContacter {
    private String databaseName;
    private Connection connection;

    public DBContacter(String databaseName) {
        this.databaseName = databaseName;
        try {
            this.connection = DriverManager.getConnection(databaseName);
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:constructor(String) viestillä:\n" + e.getMessage());
        }
    }
    
    public void testaaYhteys() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT 1;");
        if (rs.next()) {
            System.out.println("Yhteys tietokantaan pelaa! Jes :)");
        }

        stmt.close();
        rs.close();
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:testaaYhteys viestillä:\n" + e.getMessage());
        }
    }
    public void foreignKeytPaalle() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
            stmt.close();
            System.out.println("Foreign key -checkit päällä!");
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:testaaYhteys viestillä:\n" + e.getMessage());
        }
    }
    

    public void suljeYhteys() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:suljeYhteys viestillä:\n" + e.getMessage());
        }
    }

    
    public ArrayList queryAndCollect(String query, Collector col) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList elements = new ArrayList<>();
            
            while(rs.next()) {
                elements.add(col.collect(rs));
            }
            
            stmt.close();
            return elements;
        
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:queryAndCollect kyselyllä:\n " + query + "\n ja viestillä:\n" + e.getMessage());
            return null;
        }
    }
    public boolean update(String query) {
        try {
            Statement stmt= connection.createStatement();
            int touchedRows = stmt.executeUpdate(query);
            if (touchedRows >=1) {
                return true;
            } else { return false;}
        } catch (Exception e) {
            System.out.println("Tuli virhe DBContacter:update viestillä:\n" + e.getMessage());
            return false;
        }
    }
}
