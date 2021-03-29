package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.MyConnection;
import sample.entities.client;
import sample.entities.reservation;
import sample.entities.terrain;
import sample.service.reseravtioncrud;
import sample.service.terraincrud;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReserverController implements Initializable {



    @FXML
    private JFXButton insertterrain;

    @FXML
    private JFXTextField terrainname;

    @FXML
    private JFXTextField place;

    @FXML
    private JFXButton searchplace;

    @FXML
    private JFXButton searchname;

    @FXML
    private DatePicker dateReservation;

    @FXML
    private JFXTextField heurereseravtion;


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
    private JFXButton reset;

    public int i;



    ObservableList<terrain> ProductList = FXCollections.observableArrayList();
    public  static int idclient= 1;


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
    private void AjouterReservation(ActionEvent event) throws Exception {
        reseravtioncrud rcd = new reseravtioncrud();

        terrain selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confimation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to Add reservation? ");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {


            int prix = (int) selected.getPrixterrain();
            int idterrain = selected.getIdterrain();

            String date = String.valueOf(dateReservation.getValue());
            String heure = heurereseravtion.getText();


            try {
                String sql = "SELECT * FROM `reservation` WHERE `datereservation`=? AND `heurereservation`=? AND  `idterrain`=?";
                PreparedStatement pst
                        = new MyConnection().cn.prepareStatement(sql);
                pst.setString(1, date);
                pst.setString(2, heure);
                pst.setInt(3, idterrain);
                ResultSet result = pst.executeQuery();
                if (!result.next()) {
                    // check reservation date
                    //1 valider
                    //2 reserver
                    i = 1;
                } else {
                    i = 2;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int h = Integer.valueOf(heure);

            if (i == 1) {
                if ((h>=0) && (h<=24)) {
                    reservation r = new reservation(123, date, heure, idclient, idterrain, prix);
                    rcd.addreservation(r);
                    // add ticket for reservation
                    // add ticket for reservation
                    client client = new client();
                    String sql = "SELECT * FROM `client` WHERE id LIKE ?";
                    PreparedStatement pst
                            = new MyConnection().cn.prepareStatement(sql);
                    pst.setInt(1, idclient);
                    ResultSet result = pst.executeQuery();

                    if (result.next()) {

                        client.setId(result.getInt(1));
                        client.setTicket(result.getInt(2));
                        client.setMail(result.getString(3));
                    }
                    int ticket = client.getTicket();
                    String mail = client.getMail();
                    ticket++;

                    String sql2 = "UPDATE `client` SET `ticket`=?  WHERE `id`=? ";
                    PreparedStatement pst2
                            = new MyConnection().cn.prepareStatement(sql2);

                    pst2.setInt(1, ticket);
                    pst2.setInt(2, idclient);

                    pst2.executeUpdate();

                    // email

                    sendEmail(mail, date);


                }
                else {
                    Alert alert5 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert5.setTitle("Confimation Dialog");
                    alert5.setHeaderText(null);
                    alert5.setContentText("time invalid syntax ");
                    Optional<ButtonType> action5 = alert5.showAndWait();
                    if (action5.get() == ButtonType.OK) {
                    }

                }

            }
            else {
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Confimation Dialog");
                alert2.setHeaderText(null);
                alert2.setContentText("deja reserver? ");
                Optional<ButtonType> action2 = alert2.showAndWait();
                if (action2.get() == ButtonType.OK) {
                }

            }
        }

        }
        else {  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confimation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("select terrain? ");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {}

        }


    }


    public void sendEmail(String mail,String date){
        String to = mail;
        String from = "abbessi1amine@gmail.com";
        String host = "smtp.gmail.com";
        final String username = "abbessi1amine@gmail.com";
        final String password = "miminoude97";

        //setup mail server

        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try{

            //create mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(new InternetAddress(from));
            m.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            m.setSubject("reservation terrain");
            m.setText("reservation confirmer\n le"+date);

            //send mail

            Transport.send(m);
            System.out.println("Message sent!");

        }   catch (MessagingException e){
            e.printStackTrace();
        }

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
    void back(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showProducts();

    }
}
