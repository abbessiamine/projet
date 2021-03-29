package sample.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.MyConnection;
import sample.entities.client;
import sample.entities.reservation;
import sample.entities.terrain;
import sample.service.reseravtioncrud;
import sample.service.terraincrud;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListReservationController implements Initializable {


    @FXML
    private JFXButton edit;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton reserver;

    @FXML
    private JFXButton searchname;

    @FXML
    private DatePicker date;

    @FXML
    private JFXButton terrain;

    @FXML
    private TableView<reservation> table;

    @FXML
    private TableColumn<reservation, Integer> nreservation;

    @FXML
    private TableColumn<reservation, String> datereservation;

    @FXML
    private TableColumn<reservation, String> heurereseravtion;

    @FXML
    private TableColumn<reservation, Integer> idterrain;

    @FXML
    private TableColumn<reservation, Integer> prixterrain;

    @FXML
    private TableColumn<reservation, Integer> iduser;

    @FXML
    private JFXButton reset;

    reseravtioncrud pcr=new reseravtioncrud();
    ObservableList<reservation> ReservationList = FXCollections.observableArrayList();


    public void showProducts() {
        System.out.println("DEBUGG!!!!");
        ObservableList<reservation> list =pcr.getProductList();
        nreservation.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idreservation"));
        datereservation.setCellValueFactory(new PropertyValueFactory<reservation, String>("datereservation"));
        heurereseravtion.setCellValueFactory(new PropertyValueFactory<reservation, String>("heurereservation"));
        idterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("prixreservation"));
        iduser.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idclient"));
        table.setItems(list);

    }


    @FXML

    void Reserver(ActionEvent event) throws IOException, Exception {




        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/reserver.fxml"));
        Scene scene = new Scene(root);
        //    ComboBox comobox = new ComboBox();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        refreshTable();


    }

    @FXML

    void Editer(ActionEvent event) throws IOException, Exception {




        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/editreservation.fxml"));
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
        ObservableList<reservation> list =pcr.getProductList();
        nreservation.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idreservation"));
        datereservation.setCellValueFactory(new PropertyValueFactory<reservation, String>("datereservation"));
        heurereseravtion.setCellValueFactory(new PropertyValueFactory<reservation, String>("heurereservation"));
        idterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("prixreservation"));
        iduser.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idclient"));
        table.setItems(list);



    }

    @FXML
    void Terrain(ActionEvent event) throws IOException {


        AfficherTerrainController.id2= table.getSelectionModel().getSelectedItem().getIdterrain();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../view/afficherterrain.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
        refreshTable();




    }
    @FXML
    void DeleteReservation(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confimation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to delete? ");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK){
            reservation reservation= new reservation();
            int idreservation =table.getSelectionModel().getSelectedItem().getIdreservation();
            //  DbEtudiant.delete(idEtudiant);
            int status = reseravtioncrud.delete(idreservation);
            if (status>0){System.out.println("delet succeful");}else{System.out.println("delet failed");}
            //  table.setItems(data);
            refreshTable();

        }}

    @FXML
    private void getterrainbydate(ActionEvent event) {



        try{
            String sql= "SELECT * FROM `reservation` WHERE datereservation LIKE ?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            String date1 = String.valueOf(date.getValue());
            pst.setString(1,date1);
            ResultSet rs =pst.executeQuery();

            reservation p;

            while (rs.next()) {
                p = new reservation(rs.getInt("idreservation"), rs.getString("datereservation"), rs.getString("heurereservation"), rs.getInt("idclient"), rs.getInt("idterrain"), rs.getInt("prixreservation"));
                ReservationList.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        nreservation.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idreservation"));
        datereservation.setCellValueFactory(new PropertyValueFactory<reservation, String>("datereservation"));
        heurereseravtion.setCellValueFactory(new PropertyValueFactory<reservation, String>("heurereservation"));
        idterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idterrain"));
        prixterrain.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("prixreservation"));
        iduser.setCellValueFactory(new PropertyValueFactory<reservation, Integer>("idclient"));

        table.setItems(ReservationList);





    }

    @FXML
    private void Promo(ActionEvent event) {
        int ticket;

        try {


            int idc = table.getSelectionModel().getSelectedItem().getIdclient();
            int prix= table.getSelectionModel().getSelectedItem().getPrixreservation();
            int idr= table.getSelectionModel().getSelectedItem().getIdreservation();
            client client = new client();
                String sql = "SELECT * FROM `client` WHERE id LIKE ?";
                PreparedStatement pst
                        = new MyConnection().cn.prepareStatement(sql);
                pst.setInt(1, idc);
                ResultSet result = pst.executeQuery();

                if (result.next()) {

                    client.setId(result.getInt(1));
                    client.setTicket(result.getInt(2));
                }

                ticket = client.getTicket();
                if (ticket >= 5) {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Confimation Dialog");
                    alert2.setHeaderText(null);
                    alert2.setContentText("number of ticket=" + ticket);
                    Optional<ButtonType> action2 = alert2.showAndWait();
                    if (action2.get() == ButtonType.OK) {
                        prix = prix / 2;

                        String sql1 = "UPDATE `reservation` SET `prixreservation`=?  WHERE `idreservation`=? ";
                        PreparedStatement pst1
                                = new MyConnection().cn.prepareStatement(sql1);

                        pst1.setInt(1, prix);
                        pst1.setInt(2, idr);

                        pst1.executeUpdate();

                        ticket = ticket - 5;
                        String sql2 = "UPDATE `client` SET `ticket`=?  WHERE `id`=? ";
                        PreparedStatement pst2
                                = new MyConnection().cn.prepareStatement(sql2);

                        pst2.setInt(1, ticket);
                        pst2.setInt(2, idc);

                        pst2.executeUpdate();
                    }
                }
                    else {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confimation Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("ticket not enough\n "+"number of ticket=" + ticket);
                        Optional<ButtonType> action = alert.showAndWait();
                        if (action.get() == ButtonType.OK){}


                    }





            } catch (Exception ex) {
                ex.printStackTrace();
            }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();
    }
}
