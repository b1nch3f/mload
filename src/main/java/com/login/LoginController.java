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
import com.util.Router;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sangram
 */
public class LoginController extends Router implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button loginButton;
    
    @FXML
    private TextField usernameTextField;
    
    @FXML
    private PasswordField userpasswordPasswordField;
    
    @FXML
    private TextField serverURLTextField;
    
    @FXML
    private Label msgLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loginButton.setOnAction((ActionEvent event) -> {
            if(usernameTextField.getText().trim().length() == 0) {
                msgLabel.setText("Please enter the username");
            } else if(userpasswordPasswordField.getText().trim().length() == 0) {
                msgLabel.setText("Please enter the password");
            } else if(serverURLTextField.getText().trim().length() == 0) {
                msgLabel.setText("Please enter the server URL");
            } else {
                CredentialsManager.userNameText = usernameTextField.getText();
                CredentialsManager.passwordText = userpasswordPasswordField.getText();
                CredentialsManager.serverURL = serverURLTextField.getText();
                try {
                    PartnerLogin.login(CredentialsManager.userNameText, CredentialsManager.passwordText, CredentialsManager.serverURL);
                    if(CredentialsManager.fullUserName.trim().length() != 0 ) {
                        try {
                            String path = routes.get("default");
                            goTo(event, path);
                        } catch (Exception ex) {
                            Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        msgLabel.setText("Login failed");
                    }
                } catch(ConnectionException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }   
    
}
