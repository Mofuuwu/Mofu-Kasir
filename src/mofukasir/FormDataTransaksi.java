/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mofukasir;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author M Rifqi Setianto
 */
public class FormDataTransaksi extends javax.swing.JFrame {
private Connection mysqlconfig;
private String id_transaksi;
    /**
     * Creates new form FormDataTransaksi
     */
    public FormDataTransaksi() {
        initComponents();
        
        setTitle("Data Barang");
        setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            mysqlconfig = Config.configDB();
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        loadData();   

    }

    private void loadData(){
        DefaultTableModel tableModelTransaksi = new DefaultTableModel();
        tableModelTransaksi.addColumn("Kode Transaksi");
        tableModelTransaksi.addColumn("Tanggal Transaksi");
        tableModelTransaksi.addColumn("Total Transaksi");

        DefaultTableModel tableModelDetailTransaksi = new DefaultTableModel();
        tableModelDetailTransaksi.addColumn("Kode Produk");
        tableModelDetailTransaksi.addColumn("Nama Produk");
        tableModelDetailTransaksi.addColumn("Kuantitas");
        tableModelDetailTransaksi.addColumn("Harga Barang");
        
        tabelDetailTransaksi.setModel(tableModelDetailTransaksi);
        
        try {
            String sql = "SELECT * FROM transaksi";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            ResultSet results = pstmt.executeQuery();
            
            while (results.next()) {
                tableModelTransaksi.addRow( new String[] {
                    results.getString("id_transaksi"),
                    results.getString("tgl_transaksi"),
                    results.getString("jumlah_total"),
                });
            }
            tabelTransaksi.setModel(tableModelTransaksi);
        } catch (java.sql.SQLException e) {
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelDetailTransaksi = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

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

        btnNavDataTransaksi.setBackground(new java.awt.Color(58, 179, 151));
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

        btnNavDataSupplier.setBackground(new java.awt.Color(0, 204, 204));
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
                .addContainerGap(325, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(58, 179, 151));
        jLabel1.setText("Data Transaksi");

        tabelTransaksi.setBackground(new java.awt.Color(204, 204, 204));
        tabelTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        tabelTransaksi.setForeground(new java.awt.Color(51, 51, 51));
        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelTransaksi.setRowHeight(30);
        tabelTransaksi.setRowMargin(5);
        tabelTransaksi.setSelectionBackground(new java.awt.Color(0, 255, 255));
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelTransaksi);

        tabelDetailTransaksi.setBackground(new java.awt.Color(204, 204, 204));
        tabelDetailTransaksi.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        tabelDetailTransaksi.setForeground(new java.awt.Color(51, 51, 51));
        tabelDetailTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelDetailTransaksi.setRowHeight(30);
        tabelDetailTransaksi.setRowMargin(5);
        tabelDetailTransaksi.setSelectionBackground(new java.awt.Color(0, 255, 255));
        tabelDetailTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelDetailTransaksiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelDetailTransaksi);

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 179, 151));
        jLabel2.setText("Detail Transaksi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    }//GEN-LAST:event_btnNavDataTransaksiActionPerformed

    private void btnNavDataSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavDataSupplierActionPerformed
        dispose();
        FormSupplierAdmin.main(null);
    }//GEN-LAST:event_btnNavDataSupplierActionPerformed

    private void btnNavTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNavTransaksiActionPerformed
        Transaksi.main(null);
    }//GEN-LAST:event_btnNavTransaksiActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        DefaultTableModel tableModelDetailTransaksi = new DefaultTableModel();
        tableModelDetailTransaksi.addColumn("Kode Produk");
        tableModelDetailTransaksi.addColumn("Nama Produk");
        tableModelDetailTransaksi.addColumn("Kuantitas");
        tableModelDetailTransaksi.addColumn("Harga Barang");
        
        tabelDetailTransaksi.setModel(tableModelDetailTransaksi);
        
        int row = tabelTransaksi.rowAtPoint(evt.getPoint());
        id_transaksi = tabelTransaksi.getValueAt(row, 0).toString();
        try {
            String sql = "SELECT * FROM detail_transaksi WHERE id_transaksi = ?";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            pstmt.setString(1, id_transaksi);
            ResultSet results = pstmt.executeQuery();
            
            
            
            while (results.next()) {
                tableModelDetailTransaksi.addRow( new String[] {
                    results.getString("id_product"),
                    results.getString("nama_product"),
                    results.getString("kuantitas"),
                    results.getString("harga"),
                });
            }
            tabelDetailTransaksi.setModel(tableModelDetailTransaksi);
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
        }
    }//GEN-LAST:event_tabelTransaksiMouseClicked

    private void tabelDetailTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelDetailTransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelDetailTransaksiMouseClicked

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
            java.util.logging.Logger.getLogger(FormDataTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDataTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDataTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDataTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDataTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNavDataBarang;
    private javax.swing.JButton btnNavDataPegawai;
    private javax.swing.JButton btnNavDataSupplier;
    private javax.swing.JButton btnNavDataTransaksi;
    private javax.swing.JButton btnNavTransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelDetailTransaksi;
    private javax.swing.JTable tabelTransaksi;
    // End of variables declaration//GEN-END:variables
}
