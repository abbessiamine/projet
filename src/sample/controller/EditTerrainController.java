package sample.controller;


import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.entities.terrain;
import sample.service.terraincrud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditTerrainController implements Initializable {

    public static int id;


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

        //get from data base
        terrain terrain;
        terraincrud pcd = new terraincrud();
        try {
            terrain = pcd.SearchTerrainId(id);
            nomterrain.setText(terrain.getNomterrain());
            adresseterrain.setText(terrain.getAdresseterrain());
            tyepterrain.setText(terrain.getTypeterrain());
            prixterrain.setText(String.valueOf(terrain.getPrixterrain()));
        } catch (Exception ex) {
            Logger.getLogger(EditTerrainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void UpdateTerrain(ActionEvent event) throws IOException, Exception  {


        String nom = nomterrain.getText();
        String adresse = adresseterrain.getText();
        String typeterrain = tyepterrain.getText();
        int prix = Integer.parseInt(prixterrain.getText());


        terrain terrain = new terrain();

        terrain.setNomterrain(nom);
        terrain.setAdresseterrain(adresse);
        terrain.setTypeterrain(typeterrain);
        terrain.setPrixterrain(prix);
        terrain.setIdterrain(id);
        int status = terraincrud.update(terrain);
        if (status>0){System.out.println("uppdate succeful");}else{System.out.println(" update failed");}
        //close the previous windows
        ((Node)event.getSource()).getScene().getWindow().hide();

    }

    @FXML

    void back(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();

    }
}
