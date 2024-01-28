package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupAdapter {
    Connection connection;
    AccountCategory asset = new AccountCategory("Asset", "Debit");
    AccountCategory expense = new AccountCategory("Expense", "Debit");
    AccountCategory liabilities = new AccountCategory("Liabilities", "Credit");
    AccountCategory income = new AccountCategory("Income", "Credit");
    Group root = new Group(0, "none", null, null);

    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;

        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE AccountGroups");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            } finally {
                // Create the table

                stmt.execute("CREATE TABLE AccountGroups ("
                        + "ID INT NOT NULL PRIMARY KEY,"
                        + "GroupName VARCHAR(20) NOT NULL,"
                        + "GroupParentName VARCHAR(20) NOT NULL,"
                        + "Element VARCHAR(20) NOT NULL"
                        + ")");

                populateData();

            }
//                } catch (SQLException ex) {
//                        // No need to report an error.
//                        // The table exists and may have some data.
        }
//                try {
//                        // Create the table
//                        stmt.execute("CREATE TABLE AccountCategory ("
//                                + "id INT NOT NULL PRIMARY KEY,"
//                                + "fullName VARCHAR(60) NOT NULL,"
//                                + "dateHired VARCHAR(10) ,"
//                                + "dateFinished VARCHAR(10) ,"
//                                + "uAccount VARCHAR(30) NOT NULL REFERENCES UserAccount(uName)"
//                                + ")");
//
//
//                } catch (SQLException ex) {
//                        // No need to report an error.
//                        // The table exists and may have some data.
//                }
//                try {
//                        addAmin();
//                } catch (SQLException ex) {
//                        // No need to report an error.
//                        // The table exists and may have some data.
//                }
    }

    public void insertGroup(Group data) throws SQLException {
        int i = 0;
        String parentName;
        if (data.getParent() == null) {
            parentName = "None";
        } else {
            parentName = data.getParent().getName();
        }


        Statement stmt = connection.createStatement();


        stmt.executeUpdate("INSERT INTO AccountGroups (ID,  GroupName, GroupParentName, Element) "
                + "VALUES ("
                + data.getID() + ", '"
                + data.getName() + "', '"
                + parentName + "', '"
                + data.getElement().getName() + "')"
        );

    }

    public ObservableList<String[]> getAllGroups() throws SQLException {
        Statement stmt = null;
        ObservableList<String[]> list = FXCollections.observableArrayList();

        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM AccountGroups");
        while (rs.next()) {

            String[] res = {rs.getString(2), rs.getString(3), rs.getString(4)};
            list.add(res);
        }
        return list;
    }

    public void populateData() throws SQLException {
        String[] names = new String[10];
//                        // Pre-populated data from the Excel file
        int id = 1;
//
        // Excel file data

        names = new String[]{"Fixed Assets", "Investments", "Branch/divisions", "Cash in hand", "Bank accounts", "deposits (assets)", "Advances (assets)"};

        for (int i = 0; i < names.length; i++) {
            Group group = new Group(id, names[i], root, asset);
            insertGroup(group);
            id += 1;
        }

        Group capitalAccount = new Group(id, "Capital Accounts", root, liabilities);
        Group longTerm = new Group(id + 1, "Long term loans", root, liabilities);
        Group currentLiabilities = new Group(id + 2, "Current Liabilities", root, liabilities);
        id += 3;
        insertGroup(capitalAccount);
        insertGroup(longTerm);
        insertGroup(currentLiabilities);
        insertGroup(new Group(id, "Reserves and Surplus", root, liabilities));

        id += 1;
        names = new String[]{"Sales Account"};
        insertGroup(new Group(id, names[0], root, income));

        id += 1;
        names = new String[]{"Purchase Account", "Expenses(direct)", "Expenses(indirect"};
        for (int i = 0; i < names.length; i++) {
            Group group = new Group(id, names[i], root, expense);
            insertGroup(group);
            id += 1;
        }
        names = new String[]{"Secured loans", "Unsecured loans"};
        insertGroup(new Group(16, names[0], longTerm, liabilities));
        id += 1;
        insertGroup(new Group(17, names[1], longTerm, liabilities));
        id += 1;

        names = new String[]{"Duties taxes payable", "Provisions", "Sundry creditors", "Bank od & limits"};
        for (int i = 0; i < names.length; i++) {
            Group group = new Group(id, names[i], currentLiabilities, liabilities);
            insertGroup(group);
            id += 1;
        }


    }

    public void addGroup(int id, String groupName, String parentName, String type) throws SQLException {
        Statement stmt = connection.createStatement();


        stmt.executeUpdate("INSERT INTO AccountGroups (ID,  GroupName, GroupParentName, Element) "
                + "VALUES ("
                + id + ", '"
                + groupName + "', '"
                + parentName + "', '"
                + type + "')"
        );

    }

    public void deleteGroup(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        // user profile

        stmt.executeUpdate("DELETE FROM AccountGroups WHERE GroupName = '" + name + "'");

    }

    public void updateGroup(String newName, String changed) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE AccountGroups "
                + "SET GroupName = '" + newName + "'"
                + "WHERE GroupName = '" + changed + "'");
    }

    public int getMax() throws SQLException {
        int num = 0;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(ID) FROM AccountGroups");
        if (rs.next()) num = rs.getInt(1);
        return num;
    }

    public String checkCategory(String name) throws SQLException {
        // Create a Statement object
        String category = "";
        Statement stmt = connection.createStatement();

        // Create a string with a SELECT statement
        String sqlStatement = "SELECT * FROM AccountGroups WHERE GroupName = '" + name + "'";
        ResultSet rs = stmt.executeQuery(sqlStatement);
        while (rs.next()) {
            // note that, this loop will run only once
            category = rs.getString(4);
        }
        return category;
    }
}

