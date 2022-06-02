package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Member;
import model.TableIssue;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class IssueBookFormController {
    public JFXTextField txtIssueId;
    public JFXComboBox<String> cmbBookId;
    public JFXTextField txtBookTitle;
    public JFXTextField txtCharge;
    public JFXDatePicker dateBarrowedDate;
    public JFXComboBox<String> cmbMemberId;
    public JFXDatePicker dateDueDate;
    public JFXTextField txtCategory;
    public JFXTextField txtMemberName;
    public TableView tblIssue;
    public TableColumn colIssueId;
    public TableColumn colBookId;
    public TableColumn colBookTitle;
    public TableColumn colMemberId;
    public TableColumn colBarrowedDate;
    public TableColumn colDueDate;
    public TableColumn colCharge;
    public TextArea txtBill;

    public void initialize() throws SQLException, ClassNotFoundException {
        colIssueId.setCellValueFactory(new PropertyValueFactory("issueId"));
        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colMemberId.setCellValueFactory(new PropertyValueFactory("memberId"));
        colBarrowedDate.setCellValueFactory(new PropertyValueFactory("barrowedDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory("dueDate"));
        colCharge.setCellValueFactory(new PropertyValueFactory("charge"));

        setCmbBookIdBookId();
        setCmbMemberId();
        autoIssueId();

        cmbBookId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setBookId(newValue);
            }else{
                clear();
            }
        });

        cmbMemberId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setMemberId(newValue);
            }else{
                clear();
            }
        });

    }

    /** clear text fields */
    private void clear() {
        txtIssueId.clear();
        cmbBookId.setValue(null);
        txtBookTitle.clear();
        txtCharge.clear();
        dateBarrowedDate.setValue(null);
        cmbMemberId.setValue(null);
        dateDueDate.setValue(null);
        txtCategory.clear();
        txtMemberName.clear();
    }

    /** print receipt in the textArea */
    private void receipt(){
        txtBill.appendText("\n\n\t\t\t WELCOME !!! \n\n" +
                "\t\t~~Library Panadura~~ \n" +
                "\t\tNo.100/4 Galle rd,panadura. \n"+
                "\t\t076_5660311-038-1234567\n"+
                "\t\tEmail :- panaduraLibrary@gmail.com \n"+


                "\n===============================\n\n"+
                "\t\t~~~~~ Barrow Details ~~~~~\n\n"+
                "\t\tIssue ID:"+txtIssueId.getText()+"\n"+
                "\t\tBook ID:"+cmbBookId.getValue()+"\n"+
                "\t\tBook Title:"+txtBookTitle.getText()+"\n"+
                "\t\tMemberID:"+cmbMemberId.getValue()+"\n"+
                "\t\tBarrowed Date:"+dateBarrowedDate.getValue().toString()+"\n"+
                "\t\tDue Date:"+dateDueDate.getValue().toString()+"\n"+
                "\t\tCharge:"+txtCharge.getText()+"\n"+


                "\n===============================\n\n"+
                "\t\t\tThank You\n\n\n");
    }

    /** auto id */
    public void autoIssueId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT issue_id FROM issue ORDER BY issue_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("issue_id");
            int i =id.length();

            String txt= id.substring(0,2);
            String num=id.substring(2,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtIssueId.setText(ftxt);
        }else{
            txtIssueId.setText("ID1000");
        }
    }

    /** set members id for combobox */
    private void setMemberId(String memberId) {
        try {
            Member m = MemberCrudController.getItem(memberId);

            if (m != null){
                txtMemberName.setText(m.getName());
            }else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /** set book id for combobox */
    private void setBookId(String bookId) {
        try {
            Book b = BookCrudController.getItem(bookId);

            if (b != null){
                txtBookTitle.setText(b.getTitle());
                txtCategory.setText(b.getCategory());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCmbBookIdBookId(){
        try {
            cmbBookId.setItems(FXCollections.observableArrayList(BookCrudController.getItemCodes()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setCmbMemberId(){
        try {
            cmbMemberId.setItems(FXCollections.observableArrayList(MemberCrudController.getItemCodes()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<TableIssue> tmList = FXCollections.observableArrayList();

    /** barrow book main */
    public void barrowOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //IssueTm isExists = isExists(cmbBookId.getValue());


        TableIssue tb = new TableIssue(
                txtIssueId.getText(),
                cmbBookId.getValue(),
                txtBookTitle.getText(),
                cmbMemberId.getValue(),
                dateBarrowedDate.getValue().toString(),
                dateDueDate.getValue().toString(),
                Double.parseDouble(txtCharge.getText())
        );

        try {
            CrudUtil.execute("INSERT INTO issue VALUES (?,?,?,?,?,?,?)",
                    tb.getIssueId(),
                    tb.getBookId(),
                    tb.getTitle(),
                    tb.getMemberId(),
                    tb.getBarrowedDate(),
                    tb.getDueDate(),
                    tb.getCharge()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tmList.add(tb);
        tblIssue.setItems(tmList);
        receipt();
        autoIssueId();
        //clear();


    }


    /** jasper report reciept */
    public void reportPrintOnAction(ActionEvent actionEvent) {

        String issueId = txtIssueId.getText();
        String bookId = cmbBookId.getValue();
        String bookTitle = txtBookTitle.getText();
        String memberId = cmbMemberId.getValue();
        String date = String.valueOf(dateBarrowedDate.getValue());
        String dueDate = String.valueOf(dateDueDate.getValue());
        String charge = txtCharge.getText();


        HashMap paramMap=new HashMap();

        paramMap.put("issueId",issueId);
        paramMap.put("bookId",bookId);
        paramMap.put("bookTitle",bookTitle);
        paramMap.put("memberId",memberId);
        paramMap.put("barrowedDate",date);
        paramMap.put("dueDate",dueDate);
        paramMap.put("charge",charge);


        try {
            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/isssueBook.jasper"));

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paramMap, new JREmptyDataSource(1));

            JasperViewer.viewReport(jasperPrint,false);

            txtBill.setText("");

        } catch (JRException e) {
            e.printStackTrace();
        }
    }


    /** clear button for textfields */
    public void clearOnAction(ActionEvent actionEvent) {
        clear();
    }
}
