package sample.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.MyConnection;
import sample.entities.terrain;
import sample.service.terraincrud;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class terrainController implements Initializable {


    @FXML
    private TableView<terrain> table;

    @FXML
    private TableColumn<terrain, Integer> nterrain;

    @FXML
    private TableColumn<terrain, String> nomterrain;

    @FXML
    private TableColumn<terrain, String> adresseterrain;

    @FXML
    private TableColumn<terrain, String> typeterrain;

    @FXML
    private TableColumn<terrain, Integer> prixterrain;

    @FXML
    private JFXTextField terrainname;

    @FXML
    private JFXTextField place;

    ObservableList<terrain> ProductList = FXCollections.observableArrayList();



    terraincrud pcr=new terraincrud();

    public void showProducts() {
        System.out.println("DEBUGG!!!!");
        ObservableList<terrain> list =pcr.getProductList();
        nterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("idterrain"));
        nomterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("nomterrain"));
        adresseterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("adresseterrain"));
        typeterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("typeterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("prixterrain"));
        table.setItems(list);

    }

    @FXML

    void insertTerrain(ActionEvent event) throws IOException, Exception {




        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/addterrain.fxml"));
        Scene scene = new Scene(root);
        //    ComboBox comobox = new ComboBox();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        refreshTable();




    }

    @FXML
    private void refreshTable() {

        System.out.println("DEBUGG!!!!");
        ObservableList<terrain> list =pcr.getProductList();
        nterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("idterrain"));
        nomterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("nomterrain"));
        adresseterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("adresseterrain"));
        typeterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("typeterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("prixterrain"));
        table.setItems(list);



    }

    @FXML
    void DeleteTerrain(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confimation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete? ");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK){
            terrain Terrain= new terrain();
            int idterrain =table.getSelectionModel().getSelectedItem().getIdterrain();
            //  DbEtudiant.delete(idEtudiant);
            int status = terraincrud.delete(idterrain);
            if (status>0){System.out.println("delet succeful");}else{System.out.println("delet failed");}
            //  table.setItems(data);
            refreshTable();

        }}

    @FXML
    void Editeterrain(ActionEvent event) throws IOException {


        EditTerrainController.id= table.getSelectionModel().getSelectedItem().getIdterrain();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/editterrain.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        refreshTable();




    }


    @FXML
    void back(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }


    @FXML
    private void getterrainbyname(ActionEvent event) {


        ProductList.clear();

        try{
            String sql= "SELECT * FROM `terrain` WHERE nomterrain LIKE ?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            pst.setString(1,terrainname.getText());
            ResultSet rs =pst.executeQuery();

            terrain p;

            while (rs.next()) {
                p = new terrain(rs.getInt("idterrain"), rs.getString("nomterrain"), rs.getString("adresseterrain"), rs.getString("typeterrain"), rs.getInt("prixterrain"));
                ProductList.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        nterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("idterrain"));
        nomterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("nomterrain"));
        adresseterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("adresseterrain"));
        typeterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("typeterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("prixterrain"));

        table.setItems(ProductList);


        terrainname.setText("");
        place.setText("");

    }

    @FXML
    private void getterrainbyplace(ActionEvent event) {




        ProductList.clear();

        try{
            String sql= "SELECT * FROM `terrain` WHERE adresseterrain LIKE ?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            pst.setString(1,place.getText());
            ResultSet rs =pst.executeQuery();

            terrain p;

            while (rs.next()) {
                p = new terrain(rs.getInt("idterrain"), rs.getString("nomterrain"), rs.getString("adresseterrain"), rs.getString("typeterrain"), rs.getInt("prixterrain"));
                ProductList.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        nterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("idterrain"));
        nomterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("nomterrain"));
        adresseterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("adresseterrain"));
        typeterrain.setCellValueFactory(new PropertyValueFactory<terrain, String>("typeterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<terrain, Integer>("prixterrain"));

        table.setItems(ProductList);

        terrainname.setText("");
        place.setText("");




    }
}
