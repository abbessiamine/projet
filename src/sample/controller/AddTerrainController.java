package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sample.entities.terrain;
import sample.service.terraincrud;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddTerrainController implements Initializable {


    @FXML
    private JFXTextField nom;

    @FXML
    private JFXTextField adresse;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton view;

    @FXML
    private JFXTextField type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO

    }

    @FXML
    private void AjouterTerrain(ActionEvent event) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confimation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete? ");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {

            String nomt = nom.getText();
            String adresset = adresse.getText();
            String Prixt = price.getText();
            String typet = type.getText();
            float prixt = Float.parseFloat(Prixt);
            terrain p = new terrain(123, nomt, adresset, typet, (int) prixt);
            terraincrud pcd = new terraincrud();
            pcd.addterrain(p);
            ((Node) event.getSource()).getScene().getWindow().hide();
        }

    }
    @FXML
    void ViewStudent(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
