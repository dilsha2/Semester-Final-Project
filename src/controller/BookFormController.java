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

public class BookFormController {
    public JFXTextField txtBook;
    public JFXTextField txtBookTitle;
    public JFXTextField txtCategory;
    public JFXTextField txtQuantity;
    public JFXTextField txtPrice;
    public TableView<Book> tblBooks;
    public TableColumn colBookId;
    public TableColumn colBookTitle;
    public TableColumn colCategory;
    public TableColumn colQuantity;
    public TableColumn colPrice;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() throws SQLException, ClassNotFoundException {


        btnAdd.setDisable(true);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        colBookId.setCellValueFactory(new PropertyValueFactory("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory("title"));
        colCategory.setCellValueFactory(new PropertyValueFactory("category"));
        colQuantity.setCellValueFactory(new PropertyValueFactory("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        loadBooks();
        autoBookId();

        tblBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue!=null){
               setValues(newValue);
           }else{
               clear();
           }
        });

        Pattern bookNamePattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern bookCategoryPattern = Pattern.compile("^[A-Z][a-z]*[ ][A-Z][a-z]*$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,}$");
        Pattern pricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map.put(txtBookTitle,bookNamePattern);
        map.put(txtCategory,bookCategoryPattern);
        map.put(txtQuantity,qtyPattern);
        map.put(txtPrice,pricePattern);
    }

    /** auto book id */
    public void autoBookId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT book_id FROM book ORDER BY book_id DESC LIMIT 1");

        if (result.next()){
            String id = result.getString("book_id");
            int i =id.length();

            String txt= id.substring(0,2);
            String num=id.substring(2,i);
            int n=Integer.parseInt(num);
            n++;

            String snum=Integer.toString(n);
            String ftxt=txt+snum;

            txtBook.setText(ftxt);
        }else{
            txtBook.setText("BI1000");
        }
    }

    /** clear textfields */
    private void clear() {
        txtBook.clear();
        txtBookTitle.clear();
        txtCategory.clear();
        txtQuantity.clear();
        txtPrice.clear();
        ValidationUtil.validate(map,btnAdd);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblBooks.refresh();
    }

    /** set values */
    private void setValues(Book b) {
        txtBook.setText(b.getBookId());
        txtBookTitle.setText(b.getTitle());
        txtCategory.setText(b.getCategory());
        txtPrice.setText(String.valueOf(b.getPrice()));
        txtQuantity.setText(String.valueOf(b.getQty()));
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnAdd.setDisable(true);
    }

    /** add book */
    public void addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Book b = new Book(
                txtBook.getText(), txtBookTitle.getText(), txtCategory.getText(),
                Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtQuantity.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO book VALUES (?,?,?,?,?)",b.getBookId(),b.getTitle(),b.getCategory(),
                    b.getPrice(),b.getQty())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
            }
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadBooks();
        clear();
        autoBookId();
    }

    /** update book */
    public void updateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Book b = new Book(
                txtBook.getText(),txtBookTitle.getText(),txtCategory.getText(),Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQuantity.getText())
        );

        try{
            boolean isUpdated = CrudUtil.execute("Update Book SET title=?, category=?, price=?, qty=? WHERE book_id=? ",
                    b.getTitle(),b.getCategory(),b.getPrice(),b.getQty(),b.getBookId());

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
        loadBooks();
        clear();
        autoBookId();
    }

    /** delete book */
    public void deleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES,ButtonType.NO);

        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            CrudUtil.execute("DELETE FROM Book WHERE book_id=?",txtBook.getText());

            loadBooks();
            clear();
            autoBookId();

        }
    }

    /** load book */
    private void loadBooks() throws SQLException, ClassNotFoundException {
        ObservableList observableList = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Book");

        while (resultSet.next()) {
            observableList.add(
                    new Book(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getInt(5)
                    )
            );
        }
        tblBooks.setItems(observableList);
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

    /** print jasper report */
    public void printOnAction(ActionEvent actionEvent) throws JRException {
        JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/Books.jasper"));
        ObservableList<Book> items = tblBooks.getItems();
//        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanCollectionDataSource(items));
        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, new JRBeanArrayDataSource(tblBooks.getItems().toArray()));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
