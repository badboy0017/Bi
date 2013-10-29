/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hamza
 */
public class ConnexionBD {
    
    public static Connection openConnectionForMySql(String bd) {
        
        if(!bd.equals(Constante.db_trans) && !bd.equals(Constante.db_dwh)) {
            System.out.println("Nom de base de données n'est pas valide");
            return null;
        }
        
        try {
            Class.forName(Constante.mysql_driver).newInstance();
            return DriverManager.getConnection(Constante.mysql_host + bd, Constante.mysql_username, Constante.mysql_password);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println(ex.getCause() + ex.getMessage());
            return null;
        }
    }
    
    public static Connection openConnectionForOracle(String bd) {
        
        return null;
    }
    
    public static void openConnectionFromFile(String path) {
        
    }
    
    public static boolean closeConnection(Connection conn) {
        
        try {
            if(conn != null) {
                conn.close();
        }
            return true;
        }
        catch (SQLException ex) {
            System.out.println(ex.getCause() + ex.getMessage());
            return false;
        }
    }
    
    public static boolean remplirTables(Connection conn, int nbreLigne) {
        
        try {
            if(!conn.getMetaData().getURL().contains(Constante.db_trans)) {
                System.out.println("Mauvaise base de données selectionnée. Vous devez utiliser la base transcationnelle pour cette fonction");
                return false;
            }
            
            Statement st = conn.createStatement();
            ResultSet keys;
            Integer lastId;
            
            for(int i=0 ; i<nbreLigne ; i++) {
                st.executeUpdate("INSERT INTO etudiant VALUES(null, 'Riahi', 'walid', '" + Constante.getRandomNumber(1950, 2000) + "/" + Constante.getRandomNumber(1, 12) + "/" + Constante.getRandomNumber(1, 28) + "', " + Constante.getRandomNumber(1, 7) + ")", Statement.RETURN_GENERATED_KEYS);
                keys = st.getGeneratedKeys();
                keys.next();
                lastId = keys.getInt(1);
                st.executeUpdate("INSERT INTO notes VALUES(null, " + Constante.getRandomNumber(0,20) + ", " + Constante.getRandomNumber(0,20) + ", " + Constante.getRandomNumber(0,20) + ", " + lastId + ")");
            }
            
            System.out.println("Remplissage terminé avec succès");
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getCause() + ex.getMessage());
            return false;
        }
    }
    
    public static boolean clearEtudiant(Connection conn) {
        
        try {
            if(!conn.getMetaData().getURL().contains(Constante.db_trans)) {
                System.out.println("Mauvaise base de données selectionnée. Vous devez utiliser la base transcationnelle pour cette fonction");
                return false;
            }
            
            Statement st = conn.createStatement();
            st.executeUpdate("TRUNCATE TABLE etudiant");
            
            System.out.println("Table vidé avec succès");
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getCause() + ex.getMessage());
            return false;
        }
    }
    
    public static boolean clearNotes(Connection conn) {
        
        try {
            if(!conn.getMetaData().getURL().contains(Constante.db_trans)) {
                System.out.println("Mauvaise base de données selectionnée. Vous devez utiliser la base transcationnelle pour cette fonction");
                return false;
            }
            
            Statement st = conn.createStatement();
            st.executeUpdate("TRUNCATE TABLE notes");
            
            System.out.println("Table vidé avec succès");
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getCause() + ex.getMessage());
            return false;
        }
    }
    
    public static boolean clearDWH(Connection conn) {
        
        try {
            if(!conn.getMetaData().getURL().contains(Constante.db_dwh)) {
                System.out.println("Mauvaise base de données selectionnée. Vous devez utiliser la base DWH pour cette fonction");
                return false;
            }
            
            Statement st = conn.createStatement();
            st.executeUpdate("TRUNCATE TABLE etudiant");
            
            System.out.println("Table vidé avec succès");
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getCause() + ex.getMessage());
            return false;
        }
    }
    
}
