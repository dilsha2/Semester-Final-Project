package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Issue;
import model.ReturnBook;
import model.ReturnBookList;
import model.TableIssue;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.HashMap;

public class ReturnBookFormController {
    public ComboBox <String>cmbIssueId;
    public JFXTextField txtBookId;
    public JFXTextField txtBookTitle;
    public JFXTextField txtMemberId;
    public JFXTextField txtBarrowedDate;
    public JFXDatePicker returnDate;
    public JFXTextField txtFine;
    public JFXTextField txtCharge;
    public TableView <ReturnBook> tblReturn;
    public TableColumn colIssueId;
    public TableColumn colBookId;
    public TableColumn colBookTitle;
    public TableColumn colMemberId;
    public TableColumn colBarrowedDate;
    public TableColumn colReturnedDate;
    public TableColumn colFine;
    public TableColumn colCharge;
    public TextArea txtReturnBill;
    public Label lblTotal;
    private String issueId;

    public void initialize(){
        colIssueId.setCellValueFactory(new PropertyValueFactory("issueId"));
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colMemberId.setCellValueFactory(new PropertyValueFactory("memberId"));
        colBarrowedDate.setCellValueFactory(new PropertyValueFactory("barrowedDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory("returnDate"));
        colFine.setCellValueFactory(new PropertyValueFactory("fine"));
        colCharge.setCellValueFactory(new PropertyValueFactory("charge"));


        setCmbIssueId();

        cmbIssueId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setIssueId(newValue);
            }else{
                clear();
            }
        });
    }

    /** textfield clear */
    private void clear() {
        cmbIssueId.setValue(null);
        txtBookId.clear();
        txtBookTitle.clear();
        txtMemberId.clear();
        txtBarrowedDate.clear();
        returnDate.setValue(null);
        txtFine.clear();
        txtCharge.clear();
    }

    /** print reciept */
    private void receipt(){
        txtReturnBill.appendText("\n\n\t\t\t WELCOME !!! \n\n"+
                "\t\t~~Library Panadura~~ \n"+ "" +
                "\t\tNo.100/4 Galle rd,panadura. \n"+
                "\t\t076_5660311-038-1234567\n"+
                "\t\tEmail :- panaduraLibrary@gmail.com \n"+



                "\t\t~~~~~ Return Details ~~~~~\n\n"+
                "\t\tIssue ID:"+cmbIssueId.getValue()+"\n"+
                "\t\tBook ID:"+txtBookId.getText()+"\n"+
                "\t\tBook Title:"+txtBookTitle.getText()+"\n"+
                "\t\tMemberID:"+txtMemberId.getText()+"\n"+
                "\t\tBarrowed Date:"+txtBarrowedDate.getText()+"\n"+
                "\t\tFine:"+txtFine.getText()+"\n"+
                "\t\tCharge:"+txtCharge.getText()+"\n"+



                "\n===============================\n\n"+
                "\t\t\tThank You\n\n\n");
    }

    /** set issue id  */
    private void setIssueId(String issueId) {
        try {
            Issue rb = BookCrudController.getId(issueId);
            if (rb != null){
                txtBookId.setText(rb.getBookId());
                txtBookTitle.setText(rb.getTitle());
                txtMemberId.setText(rb.getMemberId());
                txtBarrowedDate.setText(rb.getBarrowedDate());
                txtCharge.setText(String.valueOf(rb.getCharge()));
            }else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** set issue id combobox  */
    private void setCmbIssueId(){
        try {
            cmbIssueId.setItems(FXCollections.observableArrayList(BookCrudController.getIssueId()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    ObservableList<ReturnBook> tmList = FXCollections.observableArrayList();

    /** return books  */
    public void returnOnAction(ActionEvent actionEvent) {

        ReturnBook rb = new ReturnBook(
                cmbIssueId.getValue(),
                txtBookId.getText(),
                txtBookTitle.getText(),
                txtMemberId.getText(),
                txtBarrowedDate.getText(),
                returnDate.getValue().toString(),
                Double.parseDouble(txtFine.getText()),
                Double.parseDouble(txtCharge.getText())
        );

        try {
            CrudUtil.execute("INSERT INTO ReturnBook VALUES (?,?,?,?,?,?,?,?)",
                    rb.getIssueId(),
                    rb.getBookId(),
                    rb.getTitle(),
                    rb.getMemberId(),
                    rb.getBarrowedDate(),
                    rb.getReturnDate(),
                    rb.getFine(),
                    rb.getCharge()

                    );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tmList.add(rb);
        tblReturn.setItems(tmList);
        receipt();
        //clear();
        calculateTotal();
    }

    /** calculate total  */
    private void calculateTotal(){
        double fine= Double.parseDouble(txtFine.getText());
        double charge = Double.parseDouble(txtCharge.getText());

        double total  = fine+charge;

        lblTotal.setText(String.valueOf(total));

    }


    /** print jasper report*/
    public void printOnAction(ActionEvent actionEvent) {

        String issueId = cmbIssueId.getValue();
        String bookId = txtBookId.getText();
        String bookName = txtBookTitle.getText();
        String memberId = txtMemberId.getText();
        String barrowedDate = txtBarrowedDate.getText();
        String rDate = String.valueOf(returnDate.getValue());
        String fine = txtFine.getText();
        String charge = txtCharge.getText();

        HashMap paramMap=new HashMap();

        paramMap.put("iId",issueId);
        paramMap.put("bId",bookId);
        paramMap.put("bTitle",bookName);
        paramMap.put("mId",memberId);
        paramMap.put("bDate",barrowedDate);
        paramMap.put("rDate",rDate);
        paramMap.put("fine",fine);
        paramMap.put("amount",charge);

//        System.out.println(issueId);
//        System.out.println(bookId);
//        System.out.println(bookName);
//        System.out.println(memberId);
//        System.out.println(barrowedDate);
//        System.out.println(rDate);
//        System.out.println(fine);
//        System.out.println(charge);

        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/returnBook.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint,false);

            txtReturnBill.setText("");

        } catch (JRException e) {
            e.printStackTrace();
        }


    }

    /** clear button for clear textfields */
    public void clearOnAction(ActionEvent actionEvent) {
        clear();
    }
}
