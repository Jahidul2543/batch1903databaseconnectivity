package consumer;

import dbconnection_utility.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumeData {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        getStudentInfoFromStudentTable();
        createTeachersTable();
    }

    public static void getStudentInfoFromStudentTable() throws SQLException, ClassNotFoundException {

       ResultSet resultSet = ConnectDB.executeSelectStatement("students");
       resultSet.next();
       String name = resultSet.getString("name");
        System.out.println(name);

    }

    public static void createTeachersTable(){
        ConnectDB connectDB = new ConnectDB();
        connectDB.createTableFromStringToMySql("teachers222", "teachersName");
    }
}
