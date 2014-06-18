/**
 *
 * Author: OCAD
 *
 *
 */
package managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.net.UnknownHostException;
import managers.LoggerManager;

/**
 * @author OCAD
 *
 */
public class DatabaseManager {

    private static String dbLocation = "ecotienda";
    private static String dbURL = "jdbc:mysql://";
    private static String dbHost = "127.0.0.1";
    private static String dbPort = "3306";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String option = "?autoReconnect=true";
    private static String userdb = "root";
    private static String pwddb = "";

    public static Connection conn = null;
    private static Statement stmt = null;

    /**
     * @return @throws Exception
     */
    public static void abrirConexion() {
        try {
            //Connection conn = null;
            if (conn == null || conn.isClosed()) {
                conn = createConnection(userdb, pwddb);
            }
            //return conn;
        } catch (Exception ex) {
            LoggerManager.getLog().error(ex.getMessage());

        }
    }

    /**
     *
     */
    public static void cerrarConexion() {

        try {

            if (conn != null) {
                conn.close();
                conn = null;
                LoggerManager.getLog().info("Connection closed!");
            }

        } catch (Exception e) {
            LoggerManager.getLog().error(e.toString());

        }
    }

    private static Connection createConnection(String userName, String password) throws SQLException, ClassNotFoundException, UnknownHostException {
        Connection conn = null;
        try {
            Class.forName(driverName);
            Properties dbProps = new Properties();
            dbProps.put("user", userName);
            dbProps.put("password", password);
            //dbHost = InetAddress.getLocalHost().getHostAddress();
            conn = DriverManager.getConnection(dbURL + dbHost + ":" + dbPort + "/" + dbLocation + option, dbProps);
            LoggerManager.getLog().info("Connection successful!");
        } catch (SQLException except) {
            LoggerManager.getLog().error(except.toString());
            LoggerManager.getLog().error("Could not connect to the database with username: " + userName);
            LoggerManager.getLog().error(" password " + password);
            LoggerManager.getLog().error("Check that the MySQL Server is running on: " + dbHost);
            throw new SQLException();
        } catch (ClassNotFoundException cnf) {
            LoggerManager.getLog().error(cnf.getMessage());
            LoggerManager.getLog().error(cnf.toString());
            throw new ClassNotFoundException();
        }

        return conn;
    }

    /**
     * @param conn
     * @param sql
     * @throws SQLException
     * @throws Exception
     */
    public static void executeDelete(Connection conn, String sql) throws SQLException, Exception {

        stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }
//FUNCION COMUN PARA CUALQUIER SQL QUE INSERTA EN TABLA Y DEVUELVE EL ID CORRESPONDIENTE A LA CLAVE PRIMARA DE LA TABLAÇ
    
    public static int executeUpdate(String insertSql) throws SQLException {//si hay excepción se controla desde la llamada
        int id = -1;
//
        String ultimoIdQuery = "SELECT last_insert_id() as last_id";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

//se prepara la query para insertar 
        preparedStatement = conn.prepareStatement(insertSql);

//se ejecuta la query e inserta el registro en la tabla
        preparedStatement.executeUpdate();//Update se utiliza para sentencias de inserta, borrar.....

        preparedStatement = conn.prepareStatement(ultimoIdQuery);
//se ejecuta la query que nos devuelve un valor         
        resultSet = preparedStatement.executeQuery();//Query se usa para devolver datos de una sentencia

//se recoge el valor y se devuelve el valor de la clave primaria, en este caso es el de la tabla Motor que es el que buscamos.  
        
        while (resultSet.next()) {
            id = resultSet.getInt("last_id");
        }
        preparedStatement.close();
        resultSet.close();

        return id;
    }

    public static void openConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
