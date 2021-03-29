package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.MyConnection;
import sample.entities.reservation;
import sample.entities.terrain;
import sample.service.terraincrud;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class reseravtioncrud {

    public static int idu =1;




    public ObservableList<reservation> getProductList() {

        ObservableList<reservation> ReservationList = FXCollections.observableArrayList();
        try {
        String requete =  "SELECT * FROM `reservation` WHERE `idclient`=1";

            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            //pst.setInt(1, idu);

            ResultSet rs;
            try {

                rs = pst.executeQuery(requete);
                reservation p;

                while (rs.next()) {
                    p = new reservation(rs.getInt("idreservation"), rs.getString("datereservation"), rs.getString("heurereservation"), rs.getInt("idclient"), rs.getInt("idterrain"), rs.getInt("prixreservation"));
                    ReservationList.add(p);
                }

            } catch (Exception ex) {
                //System.out.println("AHAYYYAA L7KEEEYAAAAA!!!!");
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(terraincrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ReservationList;
    }


    public void addreservation(reservation r) {
        try {
            String requete = "INSERT INTO reservation (datereservation,heurereservation,idclient,idterrain,prixreservation) "
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement rst
                    = new MyConnection().cn.prepareStatement(requete);
            rst.setString(1, r.getDatereservation());
            rst.setString(2, r.getHeurereservation());
            rst.setInt(3, r.getIdclient() );
            rst.setInt(4,r.getIdterrain());
            rst.setInt(5,r.getPrixreservation());
            rst.executeUpdate();
            System.out.println("reservation ajoutée!");
        } catch (SQLException ex) {
            Logger.getLogger(reseravtioncrud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    public static int delete(int id) throws SQLException, Exception{
        int s=0;
        try{
            String sql = "DELETE FROM `reservation` WHERE `idreservation`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            //Statement st;
            pst.setInt(1, id);
            s=pst.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }return s;
    }

    public static reservation Searchreservationbydate(String datereservation) throws SQLException, Exception{
        reservation reservation = new reservation();
        try{
            String sql = "SELECT * FROM `reservation` WHERE `datereservation`=? AND `heurereservation`=? AND  `idterrain`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            pst.setString(1, datereservation);
            ResultSet result =pst.executeQuery();

            if(result.next()){

                reservation.setIdterrain(result.getInt(1));
                reservation.setDatereservation(result.getString(2));
                reservation.setHeurereservation(result.getString(3));
                reservation.setIdclient(result.getInt(4));
                reservation.setIdterrain(result.getInt(5));
                reservation.setPrixreservation(result.getInt(6));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }return reservation;
    }





    public static reservation SearchReservationId(int id) throws SQLException, Exception{
        reservation reservation = new reservation();
        try{
            String sql = "SELECT * FROM `reservation` WHERE `idreservation`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet result =pst.executeQuery();

            if(result.next()){

                reservation.setIdreservation(result.getInt(1));
                reservation.setDatereservation(result.getString(2));
                reservation.setHeurereservation(result.getString(3));
                reservation.setIdclient(result.getInt(4));
                reservation.setIdterrain(result.getInt(5));
                reservation.setIdclient(result.getInt(6));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }return reservation;
    }
}
