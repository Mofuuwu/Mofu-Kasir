/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mofukasir;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;


public class FormSupplierAdmin extends javax.swing.JFrame {
private Connection mysqlconfig;
    
    public FormSupplierAdmin() {
        initComponents();
        setTitle("Data Supplier");
        setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            mysqlconfig = Config.configDB();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        loadData();   

    }
    
    private void clearForm() {
        fieldNama.setText("");
        fieldAlamat.setText("");
        fieldKodePos.setText("");
        fieldNoTelepon.setText("");
        fieldEmail.setText("");
        fieldNoRekening.setText("");
    }
    
    private void loadData(){
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("KODE");
        tableModel.addColumn("NAMA");
        tableModel.addColumn("ALAMAT");
        tableModel.addColumn("KODE POS");
        tableModel.addColumn("TELEPON");
        tableModel.addColumn("EMAIL");
        tableModel.addColumn("REKENING");
        try {
            String sql = "SELECT * FROM supplier";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            ResultSet results = pstmt.executeQuery();
            
            while (results.next()) {
                tableModel.addRow( new String[] {
                    results.getString("kode_supplier"),
                    results.getString("nama"),
                    results.getString("alamat"),
                    results.getString("kode_pos"),
                    results.getString("no_telp"),
                    results.getString("email"),
                    results.getString("no_rek"),
                });
            }
            tabelSupplier.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
        }
    }
    
    private void cariSupplier(String keyword) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("KODE");
        tableModel.addColumn("NAMA");
        tableModel.addColumn("ALAMAT");
        tableModel.addColumn("KODE POS");
        tableModel.addColumn("TELEPON");
        tableModel.addColumn("EMAIL");
        tableModel.addColumn("REKENING");


        try {
            String sql = "SELECT * FROM supplier WHERE nama LIKE ?";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                tableModel.addRow(new String[] {
                    results.getString("kode_supplier"),
                    results.getString("nama"),
                    results.getString("alamat"),
                    results.getString("kode_pos"),
                    results.getString("no_telp"),
                    results.getString("email"),
                    results.getString("no_rek"),
                });
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Data Supplier tidak ditemukan.", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

            tabelSupplier.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        btnNavDataBarang = new javax.swing.JButton();
        btnNavDataPegawai = new javax.swing.JButton();
        btnNavDataTransaksi = new javax.swing.JButton();
        btnNavDataSupplier = new javax.swing.JButton();
        btnNavTransaksi = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSupplier = new javax.swing.JTable();
        btnTambah = new javax.swing.JButton();
        btnCari = new javax.swing.JButton();
        fieldCari = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        fieldNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldKodePos = new javax.swing.JTextField();
        fieldNoTelepon = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldEmail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldNoRekening = new javax.swing.JTextField();
        fieldAlamat = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 1, 36)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Mofuu Store");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNavDataBarang.setBackground(new java.awt.Color(0, 204, 204));
        btnNavDataBarang.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNavDataBarang.setForeground(new java.awt.Color(255, 255, 255));
        btnNavDataBarang.setText("Data Barang");
        btnNavDataBarang.setBorder(null);
        btnNavDataBarang.setBorderPainted(false);
        btnNavDataBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDataBarangActionPerformed(evt);
            }
        });

        btnNavDataPegawai.setBackground(new java.awt.Color(0, 204, 204));
        btnNavDataPegawai.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNavDataPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnNavDataPegawai.setText("Data Pegawai");
        btnNavDataPegawai.setBorder(null);
        btnNavDataPegawai.setBorderPainted(false);
        btnNavDataPegawai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavDataPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDataPegawaiActionPerformed(evt);
            }
        });

        btnNavDataTransaksi.setBackground(new java.awt.Color(0, 204, 204));
        btnNavDataTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNavDataTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnNavDataTransaksi.setText("Data Transaksi");
        btnNavDataTransaksi.setBorder(null);
        btnNavDataTransaksi.setBorderPainted(false);
        btnNavDataTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavDataTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDataTransaksiActionPerformed(evt);
            }
        });

        btnNavDataSupplier.setBackground(new java.awt.Color(58, 179, 151));
        btnNavDataSupplier.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNavDataSupplier.setForeground(new java.awt.Color(255, 255, 255));
        btnNavDataSupplier.setText("Data Supplier");
        btnNavDataSupplier.setBorder(null);
        btnNavDataSupplier.setBorderPainted(false);
        btnNavDataSupplier.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavDataSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavDataSupplierActionPerformed(evt);
            }
        });

        btnNavTransaksi.setBackground(new java.awt.Color(0, 204, 204));
        btnNavTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnNavTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnNavTransaksi.setText("Transaksi");
        btnNavTransaksi.setBorder(null);
        btnNavTransaksi.setBorderPainted(false);
        btnNavTransaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNavTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNavTransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNavDataBarang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavDataPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavDataTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavDataSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNavTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavDataBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavDataPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavDataTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavDataSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNavTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 179, 151));
        jLabel2.setText("Data Supplier - Admin");

        tabelSupplier.setBackground(new java.awt.Color(204, 204, 204));
        tabelSupplier.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        tabelSupplier.setForeground(new java.awt.Color(51, 51, 51));
        tabelSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelSupplier.setRowHeight(30);
        tabelSupplier.setRowMargin(5);
        tabelSupplier.setSelectionBackground(new java.awt.Color(0, 255, 255));
        tabelSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelSupplier);

        btnTambah.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(58, 179, 151));
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnCari.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnCari.setForeground(new java.awt.Color(58, 179, 151));
        btnCari.setText("Cari Nama");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        fieldCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCariActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnRefresh.setForeground(new java.awt.Color(58, 179, 151));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(58, 179, 151));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(58, 179, 151));
        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        fieldNama.setBackground(new java.awt.Color(255, 255, 255));
        fieldNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNamaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(58, 179, 151));
        jLabel4.setText("Kode Pos");

        fieldKodePos.setBackground(new java.awt.Color(255, 255, 255));
        fieldKodePos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldKodePosActionPerformed(evt);
            }
        });

        fieldNoTelepon.setBackground(new java.awt.Color(255, 255, 255));
        fieldNoTelepon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNoTeleponActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(58, 179, 151));
        jLabel5.setText("No Telepon");

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(58, 179, 151));
        jLabel6.setText("Email");

        fieldEmail.setBackground(new java.awt.Color(255, 255, 255));
        fieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldEmailActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(58, 179, 151));
        jLabel8.setText("No Rek");

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(58, 179, 151));
        jLabel3.setText("Alamat");

        fieldNoRekening.setBackground(new java.awt.Color(255, 255, 255));
        fieldNoRekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNoRekeningActionPerformed(evt);
            }
        });

        fieldAlamat.setBackground(new java.awt.Color(255, 255, 255));
        fieldAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldAlamatActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(58, 179, 151));
        jLabel9.setText("Nama");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnRefresh)
                                .addGap(18, 18, 18)
                                .addComponent(fieldCari, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldKodePos))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldNoTelepon))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldEmail))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldNoRekening))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldNama)
                                    .addComponent(fieldAlamat))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldKodePos, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(fieldNoRekening, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnCari)
                    .addComponent(btnRefresh)
                    .addComponent(btnDelete)
                    .addComponent(fieldCari, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNavDataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDataBarangActionPerformed
        dispose();
        DashboardAdmin.main(null);
    }//GEN-LAST:event_btnNavDataBarangActionPerformed

    private void btnNavDataPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDataPegawaiActionPerformed
        dispose();
        DataPegawaiAdmin.main(null);
    }//GEN-LAST:event_btnNavDataPegawaiActionPerformed

    private void btnNavDataTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDataTransaksiActionPerformed
        dispose();
        FormDataTransaksi.main(null);
    }//GEN-LAST:event_btnNavDataTransaksiActionPerformed

    private void btnNavDataSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDataSupplierActionPerformed
        dispose();
        FormSupplierAdmin.main(null);
    }//GEN-LAST:event_btnNavDataSupplierActionPerformed

    private void tabelSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSupplierMouseClicked
        int row = tabelSupplier.rowAtPoint(evt.getPoint());
        fieldNama.setText(tabelSupplier.getValueAt(row, 1).toString());
        fieldAlamat.setText(tabelSupplier.getValueAt(row, 2).toString());
        fieldKodePos.setText(tabelSupplier.getValueAt(row, 3).toString());
        fieldNoTelepon.setText(tabelSupplier.getValueAt(row, 4).toString());
        fieldEmail.setText(tabelSupplier.getValueAt(row, 5).toString());
        fieldNoRekening.setText(tabelSupplier.getValueAt(row, 6).toString());
    }//GEN-LAST:event_tabelSupplierMouseClicked

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        String nama = fieldNama.getText().trim();
        String alamat = fieldAlamat.getText().trim();
        String kodePos = fieldKodePos.getText().trim();
        String noTelepon = fieldNoTelepon.getText().trim();
        String email = fieldEmail.getText().trim();
        String noRekening = fieldNoRekening.getText().trim();

        
        if (nama.isEmpty() || alamat.isEmpty() || kodePos.isEmpty() || noTelepon.isEmpty() 
                || email.isEmpty() || noRekening.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tolong isi semua kolom yang diperlukan");
            return;
        }

        
        try {
            String query = "INSERT INTO supplier (nama, alamat, kode_pos, no_telp, email, no_rek) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(query);
            pstmt.setString(1, nama);
            pstmt.setString(2, alamat);
            pstmt.setString(3, kodePos);
            pstmt.setString(4, noTelepon);
            pstmt.setString(5, email);
            pstmt.setString(6, noRekening);

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan");
                clearForm();
                loadData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String keyword = fieldCari.getText();
        cariSupplier(keyword);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        dispose();
        FormSupplierAdmin.main(null);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void fieldCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCariActionPerformed
        btnCari.doClick();
    }//GEN-LAST:event_fieldCariActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int selectedRow = tabelSupplier.getSelectedRow();
        if (selectedRow != -1) {
            String namaSupplierBaru = fieldNama.getText();
            String alamatSupplierBaru = fieldAlamat.getText();
            String kodePosSupplierBaru = fieldKodePos.getText();
            String noTeleponSupplierBaru = fieldNoTelepon.getText();
            String emailSupplierBaru = fieldEmail.getText();
            String noRekeningSupplierBaru = fieldNoRekening.getText();

            String kodeSupplier = tabelSupplier.getValueAt(selectedRow, tabelSupplier
                    .getColumn("KODE").getModelIndex()).toString();

            try {
                
                String sql = "UPDATE supplier SET nama = ?, alamat = ?, kode_pos = ?, no_telp = ?, "
                        + "email= ?, no_rek = ? WHERE kode_supplier = ?";
                PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
                pstmt.setString(1, namaSupplierBaru);
                pstmt.setString(2, alamatSupplierBaru);
                pstmt.setString(3, kodePosSupplierBaru);
                pstmt.setString(4, noTeleponSupplierBaru);
                pstmt.setString(5, emailSupplierBaru);
                pstmt.setString(6, noRekeningSupplierBaru);
                pstmt.setString(7, kodeSupplier);

                int updatedRows = pstmt.executeUpdate();
                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate.", 
                            "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate data.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                pstmt.close();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris tabel terlebih dahulu.", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        
        int selectedRow = tabelSupplier.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Anda yakin ingin menghapus data ini?", 
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String kodeBarang = tabelSupplier.getValueAt(selectedRow, 0).toString();

        try {
            String sql = "DELETE FROM supplier WHERE kode_supplier = ?";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            pstmt.setString(1, kodeBarang);
            
            int deletedRows = pstmt.executeUpdate();
            if (deletedRows > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            pstmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void fieldNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNamaActionPerformed
        fieldAlamat.requestFocus();
    }//GEN-LAST:event_fieldNamaActionPerformed

    private void fieldKodePosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldKodePosActionPerformed
        fieldNoTelepon.requestFocus();
    }//GEN-LAST:event_fieldKodePosActionPerformed

    private void fieldNoTeleponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNoTeleponActionPerformed
        fieldEmail.requestFocus();
    }//GEN-LAST:event_fieldNoTeleponActionPerformed

    private void fieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldEmailActionPerformed
        fieldNoRekening.requestFocus();
    }//GEN-LAST:event_fieldEmailActionPerformed

    private void fieldNoRekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNoRekeningActionPerformed
        btnTambah.doClick();
    }//GEN-LAST:event_fieldNoRekeningActionPerformed

    private void fieldAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldAlamatActionPerformed
        fieldKodePos.requestFocus();
    }//GEN-LAST:event_fieldAlamatActionPerformed

    private void btnNavTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavTransaksiActionPerformed
        dispose();
        Transaksi.main(null);
    }//GEN-LAST:event_btnNavTransaksiActionPerformed

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
            java.util.logging.Logger.getLogger(FormSupplierAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSupplierAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSupplierAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSupplierAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSupplierAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNavDataBarang;
    private javax.swing.JButton btnNavDataPegawai;
    private javax.swing.JButton btnNavDataSupplier;
    private javax.swing.JButton btnNavDataTransaksi;
    private javax.swing.JButton btnNavTransaksi;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnTambah;
    private javax.swing.JTextField fieldAlamat;
    private javax.swing.JTextField fieldCari;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldKodePos;
    private javax.swing.JTextField fieldNama;
    private javax.swing.JTextField fieldNoRekening;
    private javax.swing.JTextField fieldNoTelepon;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelSupplier;
    // End of variables declaration//GEN-END:variables
}
