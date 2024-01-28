package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CustomAccountGroupsController implements Initializable {
    // will be complete in assignment 4
    IFinanceController iFinanceController;
    private GroupAdapter groupAdapter;
    @FXML
    private AnchorPane pane;
    @FXML
    private MenuItem addNewGroup;
    @FXML
    private TextField text;
    @FXML
    private TreeView<String> treeView = new TreeView<>();
    @FXML
    private Button save;

    private int num = 0;


    private TreeItem<String> root = new TreeItem<>("");
    private TreeItem<String> trial = new TreeItem<>("heheh");
    private TreeItem<String> assets = new TreeItem<>("Asset");
    private TreeItem<String> liabilities = new TreeItem<>("Liabilities");
    private TreeItem<String> income = new TreeItem<>("Income");
    private TreeItem<String> expenses = new TreeItem<>("Expense");
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<TreeItem> parentHeirarchy = new ArrayList<>();
    private TreeItem<String> x = new TreeItem<>("");

    @FXML
    private MenuItem addSelect;
    @FXML
    private MenuItem changeNameSelect;
    @FXML
    private MenuItem deleteSelect;


    public void setAdapters(GroupAdapter groupedAdapter) {

        this.groupAdapter = groupedAdapter;
    }

    public void setIFinanceController(IFinanceController controller) {
        iFinanceController = controller;
    }
    public void cancel() {
        // Get current stage reference
        Stage stage = (Stage) save.getScene().getWindow();
        // Close stage
        stage.close();
    }


    public void getParent(String name, ObservableList<String[]> list) {
        for (int i = 0; i < list.size(); i++) {
            String[] first = list.get(i);
            if (first[1].equals("none")) {
                TreeItem<String> add = new TreeItem<>();

                data.add(first[0]);


                return;
            } else if (first[0].equals(name) && !first[1].equals("none")) {
                getParent(first[1], list);
            }
        }

    }


    public void addNewGroupClicked() {
        x = treeView.getSelectionModel().getSelectedItem();
        text.setDisable(false);
        save.setDisable(false);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    addNewGroup();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void modifyGroupClicked() {
        x = treeView.getSelectionModel().getSelectedItem();
        text.setDisable(false);
        save.setDisable(false);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    updateGroupName();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void DeleteGroupClicked() throws SQLException {
        x = treeView.getSelectionModel().getSelectedItem();
        deleteNewGroup();
    }

    public void addNewGroup() throws SQLException {
        String y = treeView.getSelectionModel().getSelectedItem().getValue();
        String z = text.getText();
        groupAdapter.addGroup(groupAdapter.getMax() + 1, z, y, groupAdapter.checkCategory(y));
        x.getChildren().add(new TreeItem<>(z));
        text.clear();
        text.setDisable(true);
        save.setDisable(true);
    }

    public void deleteNewGroup() throws SQLException {
        groupAdapter.deleteGroup(x.getValue());
        x.getParent().getChildren().remove(x);
        treeView.refresh();
    }

    public void updateGroupName() throws SQLException {
        groupAdapter.updateGroup(String.valueOf(text.getText()), x.getValue());
        x.setValue(String.valueOf(text.getText()));
        treeView.refresh();
        text.clear();
        text.setDisable(true);
        save.setDisable(true);
    }

    public void addToCategory(String name, String type) {
        if (type.equals("Asset")) {
            assets.getChildren().add(new TreeItem<>(name));
        }
        if (type.equals("Liabilities")) {
            liabilities.getChildren().add(new TreeItem<>(name));
        }
        if (type.equals("Income")) {
            income.getChildren().add(new TreeItem<>(name));
        }
        if (type.equals("Expense")) {
            expenses.getChildren().add(new TreeItem<>(name));
        }
    }

    public void setUpPage() throws SQLException {
        ObservableList<String[]> secondaryList = FXCollections.observableArrayList();
        ;
        ObservableList<String[]> list = groupAdapter.getAllGroups();
        Boolean complete = false;
        for (int i = 0; i < list.size(); i++) {
            String[] first = list.get(i);

            if (first[1].equals("none")) {
                addToCategory(first[0], first[2]);
            } else {
                secondaryList.add(list.get(i));

            }

        }
        for (int i = 0; i < secondaryList.size(); i++) {
            String[] first = secondaryList.get(i);

            if (!first[1].equals("none")) {
                Boolean hi = false;
                String type = first[2];

                if (type.equals("Asset")) {

                    ObservableList<TreeItem<String>> something = assets.getChildren();
                    int trial = 0;
                    int k = i;
                    while (!complete) {
                        first = secondaryList.get(k);

                        if (something.get(trial).getValue().equals(first[1])) {
                            something.get(trial).getChildren().add(new TreeItem<String>(first[0]));


                            break;
                        }
                        trial += 1;
                    }
                }
                if (type.equals("Liabilities")) {

                    ObservableList<TreeItem<String>> something = liabilities.getChildren();
                    int trial = 0;
                    int k = i;
                    while (!complete) {
                        first = secondaryList.get(k);

                        if (something.get(trial).getValue().equals(first[1])) {
                            something.get(trial).getChildren().add(new TreeItem<String>(first[0]));


                            break;
                        }
                        trial += 1;
                    }
                }


                if (type.equals("Income")) {

                    ObservableList<TreeItem<String>> something = income.getChildren();
                    int trial = 0;
                    int k = i;
                    while (!complete) {
                        first = secondaryList.get(k);


                        if (something.get(trial).getValue().equals(first[1])) {
                            something.get(trial).getChildren().add(new TreeItem<String>(first[0]));


                            break;
                        }
                        trial += 1;
                    }
                }
                if (type.equals("Expense")) {

                    ObservableList<TreeItem<String>> something = expenses.getChildren();
                    int trial = 0;
                    int k = i;
                    while (!complete) {
                        first = secondaryList.get(k);


                        if (something.get(trial).getValue().equals(first[1])) {
                            something.get(trial).getChildren().add(new TreeItem<String>(first[0]));


                            break;
                        }
                        trial += 1;
                    }
                }
            }
        }

    }

    public void contextMenuRequested() {
        x = treeView.getSelectionModel().getSelectedItem();
        if (x.isLeaf()) {
            addSelect.setDisable(false);
            deleteSelect.setDisable(false);
            changeNameSelect.setDisable(false);
        } else if (x.getParent().getValue().equals("")) {
            addSelect.setDisable(false);
            deleteSelect.setDisable(true);
            changeNameSelect.setDisable(true);
        } else {
            addSelect.setDisable(false);
            deleteSelect.setDisable(true);
            changeNameSelect.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root.getChildren().addAll(assets, liabilities, income, expenses);
//        assets.getChildren().add(trial);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        save.setDisable(true);
        text.setDisable(true);
//        try {
//            setUpPage();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

    }


}
