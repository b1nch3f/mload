/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.landingpage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import com.opencsv.CSVReader;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import com.util.CredentialsManager;
import com.util.CustomMetadataUtil;

/**
 * FXML Controller class
 *
 * @author sangram
 */
public class LandingPageUIController implements Initializable {
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
    private TextField objectNameTextField;
    
    @FXML
    private Button selectFileButton;
    
    @FXML
    private Button uploadButton;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        if(CredentialsManager.fullUserName.trim().length() != 0 ) {
            welcomeLabel.setText("Welcome "+CredentialsManager.fullUserName);
        }
        
        selectFileButton.setOnAction(
        new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if(CredentialsManager.mdConnection == null) {
                    try {
                        Parent blah = FXMLLoader.load(getClass().getResource("/com/login/Login.fxml"));
                        Scene scene = new Scene(blah);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.setScene(scene);
                        appStage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if(CredentialsManager.mdConnection != null) {
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    File file = fileChooser.showOpenDialog(appStage);
                    if (file != null) {
                        filePath = file.getAbsolutePath();
                        System.out.println(filePath);
                    } else {
                        System.out.println("File access cancelled by user.");
                    }
                }
            }
        });
        
        uploadButton.setOnAction((event) -> {
            if(CredentialsManager.mdConnection == null) {
                try {
                    Parent blah = FXMLLoader.load(getClass().getResource("/com/login/Login.fxml"));
                    Scene scene = new Scene(blah);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.setScene(scene);
                    appStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LandingPageUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(CredentialsManager.mdConnection != null && filePath != null) {
                System.out.println(filePath);
                String csvFile = filePath;
                ArrayList<String> header = new ArrayList<String>();
                ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

                try {
                    CSVReader reader = new CSVReader(new FileReader(csvFile));
                    String[] line;
                    while ((line = reader.readNext()) != null) {
                        ArrayList<String> body = new ArrayList<String>();

                        if(init == 0) {
                            header.addAll(Arrays.asList(line));
                            init += 1;
                            continue;
                        } else {
                            body.addAll(Arrays.asList(line));
                        }

                        data.add(body);
                    }
                    System.out.println(data);

                    objectNameText = objectNameTextField.getText();

                    String result = "";
                    try {
                        result = CustomMetadataUtil.run(objectNameText, header, data);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //msgTest.setText(result);
                    System.out.println(result);

                } catch (IOException e) {
                }
            } else if(CredentialsManager.mdConnection != null && filePath == null) {
                System.out.println("Please select a csv file to proceed");
            }
        });
    }
    
}
