/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

/**
 *
 * @author ridho.naibaho
 */

import java.sql.*;

public class koneksi {
    private Connection conn;
    public Connection openKoneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Berhasil connect mysql Driver");        
        } catch (ClassNotFoundException e) {
            System.out.println("Gagal connect Driver : " + e);     
        }
        
        String url = "jdbc:mysql://localhost/db_kasir";
        
        try {
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println("Berhasil connect DB"); 
        } catch (SQLException e) {
            System.out.println("Gagal connect DB : " + e);  
        }
        return conn;
    }
    
    public void closeKoneksi() throws SQLException{
        try{
            if(conn != null){
                System.out.print("Tutup Koneksi\n");
            }
        }catch(Exception ex){
            System.out.println("Tidak dapat membuka koneksi atau terdapat kesalahan konfigurasi database : " + ex);  
        }
    } 
}
