package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import util.Utilities;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {
    public AnchorPane signUpContext;

    public TextField txtUserName;
    public TextField txtEmail;
    public PasswordField pwdPassword;

    public void btnSignUpOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
//        signUpContext.getChildren().clear();
//        Parent parent = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
//        signUpContext.getChildren().add(parent);

        if (txtUserName.getText().matches("^[A-Z][a-z]*[ ][A-Z][a-z]*$")) {
            if (txtEmail.getText().matches("^([A-z\\d.]{3,})@(gmail|yahoo|Outlook|Inbox|iCloud|Mail|AOL|Zoho)(.com|.co.uk)$")) {
                if (pwdPassword.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,12}$")) {

                    String name = txtUserName.getText();
                    String email = txtEmail.getText();
                    String password = pwdPassword.getText();
                    if (CrudUtil.execute("INSERT INTO admin VALUES (?,?,?)", email, name, password)) {
                        Utilities.navigate(signUpContext, "LoginForm.fxml");
                        Utilities.notifycateconfrm("SignUp Successful", "SIGNUP");
                    }
                } else {
                    if (pwdPassword.getText().equals("")) {

                        Utilities.notifycate("First Fill Password ", "ERROR");
                    } else {
                        Utilities.notifycate("************   Not Valid Password Make Strong one", "ERROR");
                    }
                }


            } else {
                if (txtEmail.getText().equals("")) {

                    Utilities.notifycate("First Fill Email ", "ERROR");
                } else {
                    Utilities.notifycate(txtEmail.getText() + "   Not Valid", "ERROR");
                }
            }


        } else {
            if (txtUserName.getText().equals("")) {

                Utilities.notifycate("First Fill User Name ", "ERROR");
            } else {
                Utilities.notifycate(txtUserName.getText() + "   Not Valid", "ERROR");
            }
        }
    }
}
