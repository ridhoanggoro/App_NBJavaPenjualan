/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ui;

import java.sql.Connection;
import Utility.koneksi;
import Utility.sessionManager;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ridho.naibaho
 */
public class nota extends javax.swing.JFrame {
    public String id, namaPelanggan, jenis, telp, alamat;
    public String kdBarang, namaBarang, jenisBarang;
    public double hargaBeli, hargaJual;
    private final String userId = sessionManager.getLoggedInUserId();
    private final String namaKasir = sessionManager.getLoggedInUserName();
    NumberFormat numberFormatID = NumberFormat.getNumberInstance(new Locale("id", "ID"));
    private Connection conn = new koneksi().openKoneksi();
    DefaultTableModel tabMode = new DefaultTableModel(
    new Object[]{"Kode Barang", "Nama", "Harga Beli", "Harga Jual", "Qty", "Total"}, 0) {
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
                case 2: // Harga Beli
                case 3: // Harga Jual
                case 5: // Total
                    return Double.class;
                case 4: // Qty
                    return Integer.class;
                default:
                    return String.class;
            }
        }
    };

        

    /**
     * Creates new form nota
     */
    public nota() {
        initComponents();
        setLocationRelativeTo(null);
        lblIDKasir.setText(userId);
        lblNamaKasir.setText(namaKasir);
        setKosong();
        setAktif();
        autoNumber();
    }
    
    protected void setAktif() {
        txtQty.requestFocus();
        txtTgl.setEditor(new JSpinner.DateEditor(txtTgl, "yyyy/MM/dd"));
        tblTransaksi.setModel(tabMode);
    }
    
    protected void setKosong() {
        txtAlamat.setText("");
        txtHargaB.setText("");
        txtHargaJ.setText("");
        txtId.setText("");
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        txtQty.setText("");
        txtTotal.setText("");
        txtTotalHarga.setText("");
        txtNamaP.setText("");
        
        int rowCount = tabMode.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tabMode.removeRow(i);
        }

    }
    
    protected void autoNumber() {
        try {
            String sql = "SELECT MAX(id_nota) FROM nota"; 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                String lastId = rs.getString(1); 
                if (lastId != null) {
                    String prefix = lastId.substring(0, 2); 
                    int number = Integer.parseInt(lastId.substring(2)) + 1; 
                    String formattedNumber = String.format("%04d", number); 
                    txtIDNota.setText(prefix + formattedNumber);
                } else {
                    txtIDNota.setText("IN0001"); 
                }
            } else {
                 txtIDNota.setText("IN0001");
            }
            rs.close();
            st.close();

        } catch (SQLException e) { 
            JOptionPane.showMessageDialog(null, "Error generating auto number: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); 
            txtIDNota.setText("IN0001"); 
        }
    }
    
    protected void cariBarang(String kdBarang) {
        String sql = "SELECT nama_barang, harga_b, harga_j FROM barang WHERE kd_barang = ?"; 

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kdBarang);
            ResultSet hasil = pstmt.executeQuery();

            if (hasil.next()) {
                txtNamaBarang.setText(hasil.getString("nama_barang"));
                txtHargaB.setText(hasil.getString("harga_b"));
                txtHargaJ.setText(hasil.getString("harga_j"));
            } else {
                txtNamaBarang.setText("");
                txtHargaB.setText("");
                txtHargaJ.setText("");
                JOptionPane.showMessageDialog(null, "Barang dengan kode '" + kdBarang + "' tidak ditemukan.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencari data: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    public void itemTerpilih() {
        popuppelanggan pP = new popuppelanggan();
        pP.plgn = this;
        txtId.setText(id);
        txtNamaP.setText(namaPelanggan);
        txtAlamat.setText(alamat);
    }
    
    public void itemTerpilihBrg() {
        popupbarang pB = new popupbarang();
        pB.brg = this;
        txtKodeBarang.setText(kdBarang);
        txtNamaBarang.setText(namaBarang);
        txtHargaB.setText(String.format("%.2f", hargaBeli));
        txtHargaJ.setText(String.format("%.2f", hargaJual));
        txtQty.requestFocus();
    }
    
    public void hitung() {
        double total = 0; 
        for (int i = 0; i < tblTransaksi.getRowCount(); i++) {
            Object value = tblTransaksi.getValueAt(i, 5);
            if (value != null) {
                try {
                    double amount = Double.valueOf(value.toString()); 
                    total += amount;
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing nilai amount: " + value + " - " + e.getMessage());
                }
            }
        }
        txtTotalHarga.setText(String.format("%.2f", total)); 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIDNota = new javax.swing.JTextField();
        lblIDKasir = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNamaKasir = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTgl = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        txtKodeBarang = new javax.swing.JTextField();
        txtNamaBarang = new javax.swing.JTextField();
        txtHargaB = new javax.swing.JTextField();
        txtHargaJ = new javax.swing.JTextField();
        txtQty = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        btnCariBarang = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        btnCariP = new javax.swing.JButton();
        txtNamaP = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Form Nota - Bakso Agha");

        jLabel2.setText("ID Kasir");

        jLabel3.setText("ID Nota");

        txtIDNota.setText("jTextField1");

        lblIDKasir.setText("lblIDKasir");

        jLabel5.setText("Nama Kasir");

        lblNamaKasir.setText("lblIDKasir");

        jLabel6.setText("Tanggal Nota");

        txtTgl.setModel(new javax.swing.SpinnerDateModel());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Barang"));

        jLabel9.setText("Kode barang");

        jLabel10.setText("Nama barang");

        jLabel11.setText("Harga Jual");

        jLabel12.setText("Qty");

        jLabel13.setText("Harga Beli");

        jLabel14.setText("Total");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        txtKodeBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKodeBarangKeyPressed(evt);
            }
        });

        txtQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQtyActionPerformed(evt);
            }
        });

        btnCariBarang.setText("Cari Barang");
        btnCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariBarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHargaB, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHargaJ, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCariBarang))
                            .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariBarang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtHargaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtHargaJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTambah)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Pelanggan"));

        jLabel4.setText("ID pelanggan");

        jLabel7.setText("Nama");

        jLabel8.setText("Alamat");

        btnCariP.setText("Cari Pelanggan");
        btnCariP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPActionPerformed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAlamat);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCariP))
                    .addComponent(txtNamaP)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNamaP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Transaksi"));

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblTransaksi);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnHapus)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus)
                .addGap(5, 5, 5))
        );

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel15.setText("Total Harga");

        txtTotalHarga.setText("jTextField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIDKasir)
                                    .addComponent(txtIDNota, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(207, 207, 207)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblNamaKasir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblIDKasir)
                    .addComponent(jLabel5)
                    .addComponent(lblNamaKasir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIDNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal)
                    .addComponent(btnKeluar)
                    .addComponent(jLabel15)
                    .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPActionPerformed
        popuppelanggan popupPelanggan = new popuppelanggan();
        popupPelanggan.plgn = this;
        popupPelanggan.setVisible(true);
        popupPelanggan.setResizable(false);
    }//GEN-LAST:event_btnCariPActionPerformed

    private void btnCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariBarangActionPerformed
        popupbarang popupBarang = new popupbarang();
        popupBarang.brg = this;
        popupBarang.setVisible(true);
        popupBarang.setResizable(false);
    }//GEN-LAST:event_btnCariBarangActionPerformed

    private void txtQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQtyActionPerformed
        try {
            String hargaJText = txtHargaJ.getText().trim();
            String qtyText = txtQty.getText().trim();

            if (hargaJText.isEmpty() || qtyText.isEmpty()) {
                txtTotal.setText("0");
                return;
            }

            double hargaJ = Double.parseDouble(hargaJText);
            double qty = Double.parseDouble(qtyText);
            double total = hargaJ * qty;
            txtTotal.setText(String.format("%.2f", total)); 

        } catch (NumberFormatException e) {
            txtTotal.setText("Error");
            System.err.println("Error parsing harga atau kuantitas: " + e.getMessage());
        }
    }//GEN-LAST:event_txtQtyActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        try {
            String kode = txtKodeBarang.getText().trim();
            String nama = txtNamaBarang.getText().trim();
            String hargaJText = txtHargaJ.getText().trim();
            String hargaBText = txtHargaB.getText().trim();
            String qtyText = txtQty.getText().trim();

            // Validasi input tidak boleh kosong
            if (kode.isEmpty() || nama.isEmpty() || hargaJText.isEmpty() || hargaBText.isEmpty() || qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua kolom harus diisi.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Hentikan fungsi jika ada kolom yang kosong
            }

            double hargaJ;
            double hargaB;
            int qty;
            double total;

            try {
                hargaJ = Double.parseDouble(hargaJText);
                hargaB = Double.parseDouble(hargaBText);
                qty = Integer.parseInt(qtyText);
                total = hargaJ * qty; 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Input harga dan kuantitas harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            tabMode.addRow(new Object[]{kode, nama, hargaJ, hargaB, qty, total});
            tblTransaksi.setModel(tabMode);

            // Bersihkan input setelah berhasil ditambahkan
            txtKodeBarang.setText("");
            txtNamaBarang.setText("");
            txtHargaB.setText("");
            txtHargaJ.setText("");
            txtQty.setText("");
            txtTotal.setText(""); 
            hitung(); // Panggil fungsi hitung (pastikan fungsi ini melakukan hal yang benar)

        } catch (Exception e) {
            System.err.println("Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace(); // Cetak stack trace untuk debugging lebih lanjut
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambahkan data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int index = tblTransaksi.getSelectedRow();
        if (index != -1) {
            tabMode.removeRow(index);
            tblTransaksi.setModel(tabMode);
            hitung();
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih data yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        setKosong();
        setAktif();
        autoNumber();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void txtKodeBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKodeBarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cariBarang(txtKodeBarang.getText().toString());
        }
    }//GEN-LAST:event_txtKodeBarangKeyPressed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fd = sdf.format(txtTgl.getValue());

        String sqlNota = "INSERT INTO nota (id_nota, tgl_nota, id_pelanggan, id_kasir) VALUES (?, ?, ?, ?)";
        String sqlDetail = "INSERT INTO detail_nota (id_nota, kd_barang, harga_b, harga_j, qty) VALUES (?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Mulai transaksi

            // Simpan ke tabel nota
            try (PreparedStatement statNota = conn.prepareStatement(sqlNota)) {
                statNota.setString(1, txtIDNota.getText());
                statNota.setString(2, fd);
                statNota.setString(3, txtId.getText());
                statNota.setString(4, lblIDKasir.getText());
                statNota.executeUpdate();
            }

            // Simpan ke tabel detail_nota
            int rowCount = tblTransaksi.getRowCount();
            try (PreparedStatement statDetail = conn.prepareStatement(sqlDetail)) {
                for (int i = 0; i < rowCount; i++) {
                    String kodeBarang = tblTransaksi.getValueAt(i, 0).toString();
                    double hargaBeli = (Double) tblTransaksi.getValueAt(i, 2);
                    double hargaJual = (Double) tblTransaksi.getValueAt(i, 3);
                    int qty = (Integer) tblTransaksi.getValueAt(i, 4);

                    statDetail.setString(1, txtIDNota.getText());
                    statDetail.setString(2, kodeBarang);
                    statDetail.setDouble(3, hargaBeli);
                    statDetail.setDouble(4, hargaJual);
                    statDetail.setInt(5, qty);

                    statDetail.addBatch(); // Gunakan batch untuk efisiensi
                }
                statDetail.executeBatch();
            }

            conn.commit(); // Simpan semua perubahan
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");

        } catch (Exception e) {
            try {
                conn.rollback(); // Batalkan jika ada error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Kembalikan ke mode default
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        setKosong();
        setAktif();
        autoNumber();
    }//GEN-LAST:event_btnSimpanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(nota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(nota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(nota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new nota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCariBarang;
    private javax.swing.JButton btnCariP;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblIDKasir;
    private javax.swing.JLabel lblNamaKasir;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtHargaB;
    private javax.swing.JTextField txtHargaJ;
    private javax.swing.JTextField txtIDNota;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKodeBarang;
    private javax.swing.JTextField txtNamaBarang;
    private javax.swing.JTextField txtNamaP;
    private javax.swing.JTextField txtQty;
    private javax.swing.JSpinner txtTgl;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
