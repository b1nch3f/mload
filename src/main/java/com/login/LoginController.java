/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login;

import com.landingpage.LandingPageUIController;
import com.sforce.ws.ConnectionException;
import com.util.CredentialsManager;
import com.util.PartnerLogin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sangram
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button loginButton;
    
    @FXML
    private TextField usernameTextField;
    
    @FXML
    private PasswordField userpasswordPasswordField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction((event) -> {
            CredentialsManager.userNameText = usernameTextField.getText();
            CredentialsManager.passwordText = userpasswordPasswordField.getText();
            try {
                PartnerLogin.login(CredentialsManager.userNameText, CredentialsManager.passwordText);
                if(CredentialsManager.fullUserName.trim().length() != 0 ) {
                    try {
                        Parent blah = FXMLLoader.load(getClass().getResource("/com/landingpage/LandingPageUI.fxml"));
                        Scene scene = new Scene(blah);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.setScene(scene);
                        appStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Login failed");
                }
            } catch(ConnectionException e) {
                System.out.println(e.getMessage());
            }
        });
    }   
    
}
