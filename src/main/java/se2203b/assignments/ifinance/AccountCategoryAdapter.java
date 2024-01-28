package se2203b.assignments.ifinance;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountCategoryAdapter {
    Connection connection;

    public AccountCategoryAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE AccountCategory");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {
            // Create the table
            stmt.execute("CREATE TABLE AccountCategory ("
                    + "Name VARCHAR(20) NOT NULL PRIMARY KEY,"
//                    + "Liabilities VARCHAR(20) NOT NULL,"
//                    + "Income VARCHAR(10) NOT NULL,"
                    + "Type VARCHAR(10) NOT NULL"
                    + ")");


        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
    }
//        private void addGroup(Group data) throws SQLException {
//
//
//            // save use profile
//            insertRecord(admin);
//        }
//        public void insertGroup(Group data) throws SQLException {
//
//            Statement stmt = null;
//
//            stmt = connection.createStatement();
//
//            // insert password info first
//            if (data.getElement().equals("Assets")){
//                stmt.executeUpdate("INSERT INTO AccountCatergory ( Assets) "  + "VALUES (" + data.getuAccount().getUName() + "')"
//            }
//            else if (data.getElement().equals("Liabilities")){}
//            else if (data.getElement().equals("Liabilities")){}
//            else if (data.getElement().equals("Liabilities")){}
//            int i = new UserAccountAdapter(connection, false).insertRecord(data.getuAccount());
//
//            // insert the user profile
//
//            stmt.executeUpdate("INSERT INTO Administrator ( id,  fullName, dateHired, dateFinished, uAccount) "
//                    + "VALUES ("
//                    + data.getID() + ", '"
//                    + data.getFullName() + "', '"
//                    + data.getDateCreated() + "', '"
//                    + data.getDateCreated() + "', '"
//                    + data.getuAccount().getUName() + "')"
//            );
//
//        }
}
