package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.Utilities;
import util.ValidationUtil;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class LoginFormController {
    public Label pwdShowTextField;


    public PasswordField pwdPassword;
    public JFXCheckBox p2;
    public TextField txtUserName;
    public AnchorPane signInContext;
    public JFXButton btnSignIn;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        pwdShowTextField.setVisible(false);
        pwdPassword.setVisible(true);

    }

    public void showPasswordOnAction(ActionEvent actionEvent) {
        if (p2.isSelected()) {
            pwdShowTextField.setText(pwdPassword.getText());
            pwdShowTextField.setVisible(true);
        }else{
            pwdShowTextField.setVisible(false);
        }
    }

    public void btnSignInOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

        Utilities.end=false;

//        Stage stage=(Stage) signInContext.getScene().getWindow();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/DashBoardForm.fxml"))));
//        stage.centerOnScreen();

        if (txtUserName.getText().matches("^[A-Z][a-z]*$")){
            if (pwdPassword.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$")){

                String username = txtUserName.getText();

                String password = null;

                ResultSet user = CrudUtil.execute("SELECT * FROM admin WHERE user_name LIKE '"+username+"%'");

                while (user.next()){
                    password = user.getString(3);
                }

                if (pwdPassword.getText().equals(password)){
                    Utilities.end=false;

                    Stage stage=(Stage) signInContext.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
                    stage.centerOnScreen();

                    Utilities.notifycateconfrm(username+"   Sign In Successful","SIGN IN");
                }else {
                    Utilities.notifycate("********** Password Is Not Match","ERROR");
                }


            }else {
                if (pwdPassword.getText().equals("")){

                    Utilities.notifycate("First Fill Password ","ERROR");
                }else {
                    Utilities.notifycate("************   Not Valid Password","ERROR");
                }
            }
        }else {
            if (txtUserName.getText().equals("")){

                Utilities.notifycate("First Fill User Name ","ERROR");
            }else {
                Utilities.notifycate(txtUserName.getText()+"   Not Valid Password Make Strong one","ERROR");
            }
        }


    }

    
    public void createAccontOnAction(ActionEvent actionEvent) throws IOException {
        signInContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SignUpForm.fxml"));
        signInContext.getChildren().add(parent);
    }

}
