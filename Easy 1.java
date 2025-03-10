import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        // Database URL, username, and password
        String url = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";

        // SQL query to retrieve data from the Employee table
        String query = "SELECT EmpID, Name, Salary FROM Employee";

        // Declare the Connection and Statement objects
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Register JDBC driver (optional in newer versions of MySQL)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step 2: Open a connection
            connection = DriverManager.getConnection(url, username, password);

            // Step 3: Create a statement
            statement = connection.createStatement();

            // Step 4: Execute the query
            resultSet = statement.executeQuery(query);

            // Step 5: Process the ResultSet
            System.out.println("EmpID\tName\tSalary");
            while (resultSet.next()) {
                // Retrieve data from the result set
                int empID = resultSet.getInt("EmpID");
                String name = resultSet.getString("Name");
                double salary = resultSet.getDouble("Salary");

                // Display the result
                System.out.println(empID + "\t" + name + "\t" + salary);
            }
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Step 6: Clean up the environment (close resources)
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
