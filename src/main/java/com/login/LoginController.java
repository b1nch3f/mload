/*
*Copyright 2016 the original author or authors.

*Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

*The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
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
                        Parent p = FXMLLoader.load(getClass().getResource("/com/landingpage/LandingPageUI.fxml"));
                        Scene scene = new Scene(p);
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
