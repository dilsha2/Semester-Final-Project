package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import model.Book;
import model.Donation;
import model.Member;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class DonationDetailsFormController {
    public JFXTextField txtDonationId;
    public JFXTextField txtMemberName;
    public JFXTextField txtBookName;
    public JFXTextField txtCategory;
    public JFXTextField txtQuantity;
    public TableView <Donation> tblDonation;
    public TableColumn colDonationId;
    public TableColumn colMemberName;
    public TableColumn colBookName;
    public TableColumn colCategory;
    public TableColumn colQty;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {

        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        colDonationId.setCellValueFactory(new PropertyValueFactory("donation_id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory("name"));
        colBookName.setCellValueFactory(new PropertyValueFactory("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory("category"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));

        loadDonations();
        autoDonatioId();

        tblDonation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setValue(newValue);
            }else{
                clear();
            }
        });

        Pattern namePattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern bookNamePattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern catgoryPattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");

        map.put(txtMemberName,namePattern);
        map.put(txtBookName,bookNamePattern);
        map.put(txtCategory,catgoryPattern);
        map.put(txtQuantity,qtyPattern);

    }

    /** auto donation id */
    public void autoDonatioId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT donation_id FROM Donation_detail ORDER BY donation_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("donation_id");
            int i =id.length();

            String txt= id.substring(0,2);
            String num=id.substring(2,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtDonationId.setText(ftxt);
        }else{
            txtDonationId.setText("DI1000");
        }
    }

    /** clear textfields */
    private void clear() {
        txtDonationId.clear();
        txtBookName.clear();
        txtQuantity.clear();
        txtCategory.clear();
        txtMemberName.clear();
        ValidationUtil.validate(map,btnAdd);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblDonation.refresh();
    }

    /** set values */
    private void setValue(Donation d) {
        txtDonationId.setText(d.getDonation_id());
        txtMemberName.setText(d.getName());
        txtBookName.setText(d.getTitle());
        txtCategory.setText(d.getCategory());
        txtQuantity.setText(String.valueOf(d.getQty()));
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnAdd.setDisable(true);

    }

    /** add book */
    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Donation d = new Donation(
                txtDonationId.getText(), txtMemberName.getText(), txtBookName.getText(), txtCategory.getText(),
                Integer.parseInt(txtQuantity.getText())

        );

        try {
            if (CrudUtil.execute("INSERT INTO donation_detail VALUES (?,?,?,?,?)",d.getDonation_id(),d.getName(),
                    d.getTitle(),d.getCategory(),d.getQty())){
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").showAndWait();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        loadDonations();
        clear();
        autoDonatioId();
    }

    /** update book */
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Donation d = new Donation(
                txtDonationId.getText(), txtMemberName.getText(), txtBookName.getText(), txtCategory.getText(),
                Integer.parseInt(txtQuantity.getText())

        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE donation_detail SET member_name=?, title=?, category=?, qty=? WHERE donation_id=?", d.getName(),
                    d.getTitle(),d.getCategory(),d.getQty(),d.getDonation_id());

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadDonations();
        clear();
        autoDonatioId();
    }

    /** load all */
    private void loadDonations() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM donation_detail");

        while (resultSet.next()){
            observableList.add(
                    new Donation(resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getInt(5)
                    )
            );
        }
        tblDonation.setItems(observableList);
    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get().equals(ButtonType.YES)) {
            CrudUtil.execute("DELETE FROM donation_detail WHERE donation_id=?", txtDonationId.getText());

            loadDonations();
            clear();
            autoDonatioId();
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

    public void printOnAction(ActionEvent actionEvent) throws JRException {
        JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/Donations.jasper"));
        ObservableList<Donation> items = tblDonation.getItems();
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(items));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(tblDonation.getItems().toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
