package dbconnection_utility;

import java.sql.*;


public class BasicConncectDB {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        connectDb();

    }

    public static void connectDb() throws ClassNotFoundException, SQLException {

        /**
         *
         * 1. import MySQL Jar files using maven
         *
         * 2. Load and register Driver
         * 3 Connect to DB
         * 3.1 dbUserName & password
         * 3.2 protocol://DBHostName/dbName
         *
         * 4. Prepare the sql query
         * 5. Execute sql query
         * 6. Process Results
         * 7. Close connection
         *
         * */

        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&allowPublicKeyRetrieval=true";
        String query = "select * from students;";

        // Load and register driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Connect
        Connection connection = DriverManager.getConnection(url, userName, password);
        // Prepare Statement
        Statement statement = connection.createStatement();
        // Execute Statement
       ResultSet resultSet = statement.executeQuery(query);
       // Process results
       // resultSet.next(); Will push the pointer to next if there is any record
        resultSet.next();
        String name = resultSet.getString("name");
        System.out.println("Name: " + name);

        // Close
        statement.close();
        connection.close();

    }
}
