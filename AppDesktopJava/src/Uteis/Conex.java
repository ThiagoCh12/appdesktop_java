package Uteis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conex {

    public static Connection getConex() throws SQLException {

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Conectado com o banco de dados");
            return DriverManager.getConnection("jdbc:mysql://localhost/devthiago", "root", "");
        }catch(ClassNotFoundException e){

            throw new SQLException(e.getMessage());

        }

    }


}
