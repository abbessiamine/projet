package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.MyConnection;
import sample.entities.terrain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class terraincrud {


    public void addterrain(terrain p) {
        try {
            String requete = "INSERT INTO terrain (nomterrain,adresseterrain,typeterrain,prixterrain) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            pst.setString(1, p.getNomterrain());
            pst.setString(2, p.getAdresseterrain());
            pst.setString(3, p.getTypeterrain());
            pst.setInt(4, (int) p.getPrixterrain());
            pst.executeUpdate();
            System.out.println("Personne ajoutée!");
        } catch (SQLException ex) {
            Logger.getLogger(terraincrud.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public ObservableList<terrain> getProductList() {

        ObservableList<terrain> ProductList = FXCollections.observableArrayList();
        String requete = "SELECT * FROM terrain";
        try {
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            //Statement st;
            ResultSet rs;
            try {
                //System.out.println("AHAYYYAA!!!!");
                //st=conn.createStatement();
                //System.out.println("AHAYYYAA222!!!!");
                rs = pst.executeQuery(requete);
                terrain p;

                while (rs.next()) {
                    p = new terrain(rs.getInt("idterrain"), rs.getString("nomterrain"), rs.getString("adresseterrain"), rs.getString("typeterrain"), rs.getInt("prixterrain"));
                    ProductList.add(p);
                }

            } catch (Exception ex) {
                //System.out.println("AHAYYYAA L7KEEEYAAAAA!!!!");
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(terraincrud.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ProductList;
    }

    public static int delete(int id) throws SQLException, Exception{
        int s=0;
        try{
            String sql = "DELETE FROM `terrain` WHERE `idterrain`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            //Statement st;
            pst.setInt(1, id);
            s=pst.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }return s;
    }

    public static int update(terrain terrain) throws SQLException, Exception{
        int s=0;
        try{
            String sql = "UPDATE `terrain` SET `nomterrain`=?, `adresseterrain`=?, `typeterrain`=?, `prixterrain`=? WHERE `idterrain`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);

            pst.setString(1, terrain.getNomterrain());
            pst.setString(2, terrain.getAdresseterrain());
            pst.setString(3, terrain.getTypeterrain());
            pst.setInt(4, (int) terrain.getPrixterrain());
            pst.setInt(5, terrain.getIdterrain());
            s=pst.executeUpdate();


        }catch(SQLException e){
            e.printStackTrace();
        }return s;
    }


    public static terrain SearchTerrainId(int id) throws SQLException, Exception{
        terrain terrain = new terrain();
        try{
            String sql = "SELECT * FROM `terrain` WHERE `idterrain`=?";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet result =pst.executeQuery();

            if(result.next()){

                terrain.setIdterrain(result.getInt(1));
                terrain.setNomterrain(result.getString(2));
                terrain.setAdresseterrain(result.getString(3));
                terrain.setTypeterrain(result.getString(4));
                terrain.setPrixterrain(result.getInt(5));
            }


        }catch(SQLException e){
            e.printStackTrace();
        }return terrain;
    }
}
