package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import sample.entities.terrain;
import sample.service.terraincrud;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherTerrainController implements Initializable {
    public static int id2;
    @FXML
    private JFXTextField prixterrain;

    @FXML
    private JFXTextField tyepterrain;

    @FXML
    private JFXTextField adresseterrain;

    @FXML
    private JFXTextField nomterrain;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain terrain;
        terraincrud pcd = new terraincrud();
        try {
            terrain = pcd.SearchTerrainId(id2);
            nomterrain.setText(terrain.getNomterrain());
            adresseterrain.setText(terrain.getAdresseterrain());
            tyepterrain.setText(terrain.getTypeterrain());
            prixterrain.setText(String.valueOf(terrain.getPrixterrain()));
        } catch (Exception ex) {
            Logger.getLogger(EditTerrainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML

    void back(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();

    }
}
