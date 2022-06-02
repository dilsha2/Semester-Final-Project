package controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashBoardFormController {
    public Label lblDate;
    public Label lblTime;
    public AnchorPane dashboardContext;
    public Label lblBooks;
    public Label lblMemebers;
    public Label lblIssuedBooks;
    public Label lblReturnedBooks;

    public void initialize(){

        loadDateTime();
        try {
            loadLables();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** date time*/
    public void loadDateTime(){
        //set Date
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        //set time
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /** load lables */
    private void loadLables() throws SQLException, ClassNotFoundException {
        ResultSet result1 = CrudUtil.execute("SELECT COUNT(book_id)  FROM book");
        result1.next();
        lblBooks.setText(String.valueOf(result1.getString(1)));

        ResultSet result2 = CrudUtil.execute("SELECT COUNT(member_id)  FROM member");
        result2.next();
        lblMemebers.setText(String.valueOf(result2.getString(1)));

        ResultSet result3 = CrudUtil.execute("SELECT COUNT(issue_id)  FROM issue");
        result3.next();
        lblIssuedBooks.setText(String.valueOf(result3.getString(1)));

        ResultSet result4 = CrudUtil.execute("SELECT COUNT(issue_id)  FROM ReturnBook");
        result4.next();
        lblReturnedBooks.setText(String.valueOf(result4.getString(1)));
    }


    public void addMemberOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AddMemberForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void bookOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BookForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        Stage stage=(Stage) dashboardContext.getScene().getWindow();
        stage.close();
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.close();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.show();
    }

    public void issueBookOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IssueBookForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void returnBookOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ReturnBookForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void searchBookOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/searchBookForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void donationDetailsOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/donationDetailsForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void returnedBookListOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/ReturnedBookListForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void issuedBookOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IssuedBooksListForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }


    public void incomeOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IncomeForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage=(Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }
}
