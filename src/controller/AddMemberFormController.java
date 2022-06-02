package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Member;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddMemberFormController {
    public JFXTextField txtMemberId;
    public JFXTextField txtMemberName;
    public JFXTextField txtNic;
    public JFXTextField txtContact;
    public JFXTextField txtRegestrationFee;
    public TableView <Member> tblAddMember;
    public TableColumn colMemberId;
    public TableColumn colMemberName;
    public TableColumn colMemberNic;
    public TableColumn colContact;
    public TableColumn colRegFee;
    public TableColumn colRegDate;
    public JFXDatePicker txtRegestrationDate;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {

        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        colMemberId.setCellValueFactory(new PropertyValueFactory("id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory("name"));
        colMemberNic.setCellValueFactory(new PropertyValueFactory("nic"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colRegFee.setCellValueFactory(new PropertyValueFactory("regFee"));
        colRegDate.setCellValueFactory(new PropertyValueFactory("regDate"));

        loadMembers();
        autoMemberId();

        //tblAddMember.setItems();
        tblAddMember.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                loadField(newValue);
            }else{
                clear();
            }
        });

        Pattern namePattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern nicPattern = Pattern.compile("^([0-9]{9}[V]|[0-9]{12})$");
        Pattern contactPattern = Pattern.compile("^(?:7|0|(?:\\+94))[0-9]{9}$");
        Pattern chargePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtMemberName,namePattern);
        map.put(txtNic,nicPattern);
        map.put(txtContact,contactPattern);
        map.put(txtRegestrationFee,chargePattern);
    }

    /** member id auto */
    public void autoMemberId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT member_id FROM member ORDER BY member_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("member_id");
            int i =id.length();

            String txt= id.substring(0,2);
            String num=id.substring(2,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtMemberId.setText(ftxt);
        }else{
            txtMemberId.setText("MI1000");
        }
    }

    /** clear text fields*/
    private void clear() {
        txtMemberId.clear();
        txtMemberName.clear();
        txtContact.clear();
        txtNic.clear();
        txtRegestrationFee.clear();
        txtRegestrationDate.setValue(null);
        ValidationUtil.validate(map,btnAdd);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblAddMember.refresh();
    }

    /** load data for textfield */
    private void loadField(Member m) {
        txtMemberId.setText(m.getId());
        txtMemberName.setText(m.getName());
        txtNic.setText(m.getNic());
        txtContact.setText(m.getContact());
        txtRegestrationFee.setText(String.valueOf(m.getRegFee()));
        txtRegestrationDate.setValue(LocalDate.parse(m.getRegDate()));
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnAdd.setDisable(true);
    }

    /** all members */
    private void loadMembers() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
       ResultSet resultSet = CrudUtil.execute("SELECT * FROM Member");
       while (resultSet.next()){
           observableList.add(
                   new Member(resultSet.getString(1),
                           resultSet.getString(2),
                           resultSet.getString(3),
                           resultSet.getString(4),
                           resultSet.getDouble(5),
                           resultSet.getString(6)
                           )
           );
       }
       tblAddMember.setItems(observableList);
    }

    /** add member */
    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Member m = new Member(
                txtMemberId.getText(),txtMemberName.getText(),txtNic.getText(),txtContact.getText(),
               Double.parseDouble(txtRegestrationFee.getText()), txtRegestrationDate.getValue().toString()
        );
       try {
           if (CrudUtil.execute("INSERT INTO Member VALUES (?,?,?,?,?,?)",m.getId(),m.getName(),m.getNic(),
           m.getContact(),m.getRegFee(),m.getRegDate())){
               new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
           }
       }catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
    }
       loadMembers();
       clear();
       autoMemberId();
    }

    /** update member */
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Member m = new Member(
                txtMemberId.getText(),txtMemberName.getText(),txtNic.getText(),txtContact.getText(),
                Double.parseDouble(txtRegestrationFee.getText()),txtRegestrationDate.getValue().toString()
        );

        try {
            boolean isUpdated=CrudUtil.execute("UPDATE Member SET member_name=?, nic=?, contact=?, reg_fee=?, reg_date=? WHERE member_id=? ", m.getName(), m.getNic(), m.getContact(),
                    m.getRegFee(), m.getRegDate(),m.getId());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }


        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadMembers();
        clear();
        autoMemberId();
    }

    /** delete member */
    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES,ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            CrudUtil.execute("DELETE FROM Member WHERE member_id=?",txtMemberId.getText());

            loadMembers();
            clear();
            autoMemberId();

        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnAdd);

        if (keyEvent.getCode()== KeyCode.ENTER){
            Object response = ValidationUtil.validate(map,btnAdd);
            if (response instanceof JFXTextField){
                JFXTextField textField = (JFXTextField) response;
                textField.requestFocus();
            }else if (response instanceof Boolean){

            }
        }

    }

    /** jasper report print */
    public void printOnAction(ActionEvent actionEvent) throws JRException {

        JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/Members.jasper"));
        ObservableList<Member> items = tblAddMember.getItems();
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(items));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(tblAddMember.getItems().toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
