package dbconnection_utility;

import java.sql.*;
import java.util.*;


public class ConnectDB {

    static Connection connection = null;
    static ResultSet resultSet = null;
    static PreparedStatement preparedStatement = null;


    private static Connection connectDB() throws ClassNotFoundException, SQLException {


       Properties properties = ReadPropertiesFile.readApplicationProperties();

       String userName = properties.getProperty("userName");
       String password = properties.getProperty("password");
       String url = properties.getProperty("url");

        /* String userName = "root";
         String password = "root";
         String url ="jdbc:mysql://localhost:3306/testdb?useSSL=false&allowPublicKeyRetrieval=true";
*/
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        return connection;

    }

    // Execute sql query
    public static ResultSet executeSelectStatement(String tableName) {

        try {
            Connection connection = connectDB();
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from " + tableName);

           /* while(resultSet.next()) {
                String value = resultSet.getString(fieldName);
                list.add(value);
            }*/
        }

        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public List<String> processThreeFieldsFromResultset(ResultSet resultSet, String col1, String col2, String col3) throws SQLException {
        List<String> data = new ArrayList<>();
        while (resultSet.next()){
            String value1 = resultSet.getString(col1);
            String value2 = resultSet.getString(col2);
            String vale3 = resultSet.getString(col3);
            data.add(value1);
            data.add(value2);
            data.add(vale3);
        }

        return data;
    }

    public void createTableFromStringToMySql(String tableName, String columnName){
        try {
            Connection connection = connectDB();
            preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS `"+tableName+"`;");
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("CREATE TABLE `"+tableName+"` (`ID` int(11) NOT NULL AUTO_INCREMENT,`"+columnName+"` varchar(2500) DEFAULT NULL,  PRIMARY KEY (`ID`) );");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void storeTableInMap() throws SQLException {

        /**
         * List of Maps
         * map1 and map2 in list
         *
         *
         * */

        resultSet =executeSelectStatement("students");
        ResultSetMetaData metaData = resultSet.getMetaData();
        int totalColumn = metaData.getColumnCount();

        Map<String, String> map=null;
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();


        while (resultSet.next()){

            for(int i = 1; i<=totalColumn; i++) {

                map = new HashMap<>();
                // map.put("name", resultSet.getString("name"));
                map.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));

            }
            arrayList.add(map);

        }

        for(int i =1; i<=arrayList.size(); i++){

            Map<String, String>  map1 = arrayList.get(i);
            System.out.println(map1.get("id") + map1.get("name") + map1.get("grade"));

            /*for(Map.Entry<String, String> e : map1.entrySet()){

                System.out.println(e.getKey() + " : " + e.getValue());
            }*/

        }

    }


    public static void main(String[] args) throws SQLException {
        storeTableInMap();
    }

}
