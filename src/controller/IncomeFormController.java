package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Income;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncomeFormController {

    public ComboBox cmbMonth;
    public TableView tblIncome;
    public TableColumn colIssueId;
    public TableColumn colDate;
    public TableColumn colAmount;

    String Month= new SimpleDateFormat("MM").format(new Date());
    String Year=new SimpleDateFormat("yyyy").format(new Date());

    public void initialize() throws SQLException, ClassNotFoundException {

        colIssueId.setCellValueFactory(new PropertyValueFactory("isssueId"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory("amount"));

        comboLoad();
        tableLoad();

    }

    public void comboLoad(){
        ObservableList<String> obList= FXCollections.observableArrayList();
        obList.add("January");
        obList.add("February");
        obList.add("March");
        obList.add("April");
        obList.add("May");
        obList.add("June");
        obList.add("July");
        obList.add("August");
        obList.add("September");
        obList.add("October");
        obList.add("November");
        obList.add("December");
        cmbMonth.setItems(obList);
    }


    public void tableLoad() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM ReturnBook WHERE MONTH(returned_date) =? and YEAR(returned_date)=?",Month,Year);
        returnBook(result);

    }

    public void cmbMonthOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Years=new SimpleDateFormat("yyyy").format(new Date());
        String Monthly= String.valueOf(cmbMonth.getValue());

        ResultSet result = CrudUtil.execute("SELECT * FROM returnBook WHERE MONTHNAME(returned_date) =? And YEAR(returned_date)=?",Monthly,Years);
        returnBook(result);

    }

    private void returnBook(ResultSet result) throws SQLException {
        ObservableList<Income> income = FXCollections.observableArrayList();

        while (result.next()){
            income.add(new Income(
                    result.getString(1),
                    result.getString(6),
                    result.getDouble(8)
            ));
        }
        tblIncome.setItems(income);

    }

}
