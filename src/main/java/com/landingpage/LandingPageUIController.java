/*
*Copyright 2016 the original author or authors.

*Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

*The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.landingpage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.stage.FileChooser;
import com.util.CredentialsManager;
import com.util.CustomMetadataUtil;
import com.util.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author sangram
 */
public class LandingPageUIController extends Router implements Initializable {
    public static String filePath = null;
    public static int init = 0;
    public static String objectNameText = "";
    final FileChooser fileChooser = new FileChooser();

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Button selectFileButton;
    
    @FXML
    private Button uploadButton;
    
    @FXML
    private ComboBox objectNameComboBox;
    
    @FXML
    private Label msgLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CredentialsManager.fullUserName.trim().length() != 0 ) {
            welcomeLabel.setText("Welcome "+CredentialsManager.fullUserName);
            
            ObservableList<String> options = 
            FXCollections.observableArrayList(
                CustomMetadataUtil.listCustomMetadata()
            );
            
            objectNameComboBox.getItems().addAll(options);
        } else {
            objectNameComboBox.getItems().add((String)"--NONE--");
        }
        
        selectFileButton.setOnAction((final ActionEvent event) -> {
            if (CredentialsManager.mdConnection == null) {
                try {
                    String path = routes.get("login");
                    goTo(event, path);
                } catch (Exception ex) {
                    Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(CredentialsManager.mdConnection != null) {
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                File file = fileChooser.showOpenDialog(appStage);
                if (file != null) {
                    filePath = file.getAbsolutePath();
                } else {
                    msgLabel.setText("File access cancelled by user.");
                }
            }
        });
        
        uploadButton.setOnAction((ActionEvent event) -> {
            if (CredentialsManager.mdConnection == null) {
                try {
                    String path = routes.get("login");
                    goTo(event, path);
                }catch (Exception ex) {
                    Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(CredentialsManager.mdConnection != null && filePath != null) {
                System.out.println(filePath);
                String csvFile = filePath;
                ArrayList<String> header = new ArrayList<>();
                ArrayList<ArrayList<String>> data = new ArrayList<>();
                
                try {
                    CSVReader reader = new CSVReader(new FileReader(csvFile));
                    String[] line;
                    while ((line = reader.readNext()) != null) {
                        ArrayList<String> body = new ArrayList<>();
                        
                        if(init == 0) {
                            header.addAll(Arrays.asList(line));
                            init += 1;
                            continue;
                        } else {
                            body.addAll(Arrays.asList(line));
                        }
                        
                        data.add(body);
                    }
                    init = 0;
                    
                    objectNameText = (String) objectNameComboBox.getValue();
                    
                    Boolean result = false;
                    try {
                        if(objectNameText != null) {
                            result = CustomMetadataUtil.upsertCustomMetadata(objectNameText, header, data);
                            if(result) {
                                msgLabel.setText("Upload Success!");
                            }
                        } else {
                            msgLabel.setText("Please select a custom metadata type");
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if(CredentialsManager.mdConnection != null && filePath == null) {
                msgLabel.setText("Please select a csv file to proceed");
            }
        });
    }
    
}
