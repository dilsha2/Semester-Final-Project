package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ReturnBookList;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReturnedBookListFormController {
    public JFXTextField txtSearchReturn;
    public TableView tblReturnedBookList;
    public TableColumn colMemberId;
    public TableColumn colBookId;
    public TableColumn colBookName;
    public TableColumn colBarrowedDate;
    public TableColumn colReturnedDate;
    public TableColumn colPaymentStatus;

    public void initialize() throws SQLException, ClassNotFoundException {
        colMemberId.setCellValueFactory(new PropertyValueFactory("memberID"));
        colBookId.setCellValueFactory(new PropertyValueFactory("bookID"));
        colBookName.setCellValueFactory(new PropertyValueFactory("bookName"));
        colBarrowedDate.setCellValueFactory(new PropertyValueFactory("barrowedDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory("returnedDate"));

        loadAllReturn();
    }

    /** load all return book list  */
    private void loadAllReturn() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM returnBook");

        while (resultSet.next()){
            observableList.add(
                    new ReturnBookList(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)

                            )
            );
        }
        tblReturnedBookList.setItems(observableList);

        FilteredList<ReturnBookList> filterData = new FilteredList<ReturnBookList>(observableList, b -> true);

        txtSearchReturn.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(ReturnBookList-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (ReturnBookList.getMemberID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;


            });
        });

        SortedList<ReturnBookList> sortedData = new SortedList<>(filterData);

        sortedData.comparatorProperty().bind(tblReturnedBookList.comparatorProperty());

        tblReturnedBookList.setItems(sortedData);
    }
}
