package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IssueDetail;
import model.Member;
import model.TableIssue;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IssuedBooksListFormController {
    public JFXTextField txtSearch;
    public TableView tblSearchIssue;
    public TableColumn colIssueId;
    public TableColumn colBookId;
    public TableColumn colBookTitle;
    public TableColumn colCustomerId;
    public TableColumn colBarrowDate;

    public void initialize() throws SQLException, ClassNotFoundException {
        colIssueId.setCellValueFactory(new PropertyValueFactory("issueId"));
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("memberId"));
        colBarrowDate.setCellValueFactory(new PropertyValueFactory("barrowedDate"));

        loadAllIssue();
    }

    /** load issue book list */
    private void loadAllIssue() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM issue");

        while (resultSet.next()){
            observableList.add(
                    new IssueDetail(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        tblSearchIssue.setItems(observableList);

        FilteredList<IssueDetail> filterData = new FilteredList<IssueDetail>(observableList, b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(IssueDetail-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (IssueDetail.getIssueId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;


            });
        });

        SortedList<IssueDetail> sortedData = new SortedList<>(filterData);

        sortedData.comparatorProperty().bind(tblSearchIssue.comparatorProperty());

        tblSearchIssue.setItems(sortedData);
    }
}
