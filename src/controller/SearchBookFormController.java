package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.IssueDetail;
import model.SearchBook;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchBookFormController {
    public JFXTextField txtBookId;
    public TableView tblSearchBooks;
    public TableColumn colBookId;
    public TableColumn colTitle;
    public TableColumn colCategory;
    public TableColumn colQty;
    public TableColumn colPrice;

    public void initialize() throws SQLException, ClassNotFoundException {
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory("category"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        loadAllBooks();
    }

    /** search books  */
    private void loadAllBooks() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM book");

        while (resultSet.next()){
            observableList.add(
                    new SearchBook(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getDouble(5)
                    )
            );
        }
        tblSearchBooks.setItems(observableList);

        FilteredList<SearchBook> filterData = new FilteredList<SearchBook>(observableList, b -> true);

        txtBookId.textProperty().addListener((observable, oldValue, newValue) -> {
            filterData.setPredicate(SearchBook-> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();


                if (SearchBook.getBookId().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;


            });
        });

        SortedList<SearchBook> sortedData = new SortedList<>(filterData);

        sortedData.comparatorProperty().bind(tblSearchBooks.comparatorProperty());

        tblSearchBooks.setItems(sortedData);
    }
}
