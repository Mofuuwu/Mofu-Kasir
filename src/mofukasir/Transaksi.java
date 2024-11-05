
package mofukasir;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.sql.Statement;

import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.github.sarxos.webcam.Webcam;





/**
 *
 * @author M Rifqi Setianto
 */
public class Transaksi extends javax.swing.JFrame {
private Connection mysqlconfig;
private ArrayList<String[]> order = new ArrayList<>();

private String kodeBarang;
private String namaBarang;
private String hargaBarang;
private int quantity = 1;
private float diskon = 0.0f;

private Webcam webcam;
private WebcamPanel webcamPanel;
private boolean isRunning = true;

private void startWebcam() {
    IniWebcam.setPreferredSize(new Dimension(100, 100));
    webcam = null;
    for (Webcam w : Webcam.getWebcams()) {
        System.out.println("Webcam detected: " + w.getName());
        if (w.getName().contains("DroidCam")) { 
            webcam = w;
            break;
        }
    }

    if (webcam != null) {
        webcam.setViewSize(new Dimension(320, 240));

        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);

        // Tambahkan webcamPanel ke JPanel 'IniWebcam'
        IniWebcam.setLayout(new BorderLayout());
        IniWebcam.add(webcamPanel, BorderLayout.CENTER);
        IniWebcam.revalidate();

        // Mulai thread untuk memproses gambar dari webcam
        new Thread(() -> {
            do {
                try {
                    BufferedImage image = webcam.getImage();
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);

                    if (result.getText() != null) {
                        String qrText = result.getText();
                        System.out.println("QR Code Data: " + qrText);

                        // Tampilkan hasil scan ke inputIdTransaksi
                        SwingUtilities.invokeLater(() -> {
                            inputCariBarang.setText(qrText);
                        });

                        // Jika ingin menghentikan setelah scan pertama, set isRunning = false;
                        // isRunning = false;
                    }

                } catch (NotFoundException e) {
                    // QR code tidak ditemukan dalam frame ini, lanjutkan loop
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (isRunning);
        }).start();

    } else {
        JOptionPane.showMessageDialog(this, "Webcam tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void processQRCode() {
    String qrData = inputCariBarang.getText().trim();
    if (!qrData.isEmpty()) {
        // Contoh: Cari barang berdasarkan kode yang dipindai
        cariBarang("kode_barang", qrData);
    }
}

DefaultTableModel tableModelBarang;
DefaultTableModel tableModelTotalBelanja;

private void initTableModel() {
    
    tableModelBarang = new DefaultTableModel();
    tableModelBarang.addColumn("KODE");
    tableModelBarang.addColumn("NAMA");
    tableModelBarang.addColumn("HARGA");
    tableModelBarang.addColumn("STOK");
    tableModelBarang.addColumn("TANGGAL MASUK");
    tableModelBarang.addColumn("TANGGAL KADALUARSA");
    tabelBarang.setModel(tableModelBarang);
    
    tableModelTotalBelanja = new DefaultTableModel();
    tableModelTotalBelanja.addColumn("Kode");
    tableModelTotalBelanja.addColumn("Nama Barang");
    tableModelTotalBelanja.addColumn("Kuantitas");
    tableModelTotalBelanja.addColumn("Harga");
    tabelTotalBelanja.setModel(tableModelTotalBelanja);
}
    
    public Transaksi() {
        
        initComponents();
        initTableModel();
        setTitle("Data Barang");
        setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        try {
            mysqlconfig = Config.configDB();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        inputIdTransaksi.setEditable(false);
        inputTotalHarga.setEditable(false);
        startWebcam();
    }
    
    private String formatNumber(float number) {
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("id", "ID")); 
        // Format angka untuk Indonesia
        numberFormat.setGroupingUsed(true); // Menggunakan pemisah ribuan
        numberFormat.setMinimumFractionDigits(2); // Set minimum digit desimal
        numberFormat.setMaximumFractionDigits(2); // Set maksimum digit desimal
        return numberFormat.format(number);
    }

    
    private void updateTotalHarga() {
    float totalHarga = 0.0f;
    DefaultTableModel tableModel = (DefaultTableModel) tabelTotalBelanja.getModel();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        String hargaStr = tableModel.getValueAt(i, 3).toString().replace(".", "")
                .replace(",", ".");
        try {
            totalHarga += Float.parseFloat(hargaStr);
        } catch (NumberFormatException e) {
            // Handle exception
        }
    }
        inputTotalHarga.setText(formatNumber(totalHarga));
    }
    
    private void cariBarang(String search_param, String keyword) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("KODE");
        tableModel.addColumn("NAMA");
        tableModel.addColumn("HARGA");
        tableModel.addColumn("STOK");
        tableModel.addColumn("TANGGAL MASUK");
        tableModel.addColumn("TANGGAL KADALUARSA");

        try {
            String sql = "SELECT * FROM barang WHERE " + search_param + " LIKE ?";
            PreparedStatement pstmt = mysqlconfig.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            ResultSet results = pstmt.executeQuery();

            while (results.next()) {
                tableModel.addRow(new String[] {
                    results.getString("kode_barang"),
                    results.getString("nama_barang"),
                    results.getString("harga"),
                    results.getString("stok"),
                    results.getString("tanggal_masuk"),
                    results.getString("tanggal_kadaluarsa"),
                });
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Data barang tidak ditemukan.", 
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }

            tabelBarang.setModel(tableModel);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inputIdTransaksi = new javax.swing.JTextField();
        inputCariBarang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        btnCari1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelBarang = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        inputJumlahBarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        inputDiskonBarang = new javax.swing.JTextField();
        btnTambahBarang = new javax.swing.JButton();
        btnKurangBarang = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelTotalBelanja = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        inputTotalHarga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fieldBayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        fieldKembalian = new javax.swing.JTextField();
        btnTambahKeKeranjang = new javax.swing.JButton();
        BtnHapusBarang = new javax.swing.JButton();
        btnCari6 = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSelesai = new javax.swing.JButton();
        IniWebcam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));
        setForeground(new java.awt.Color(0, 102, 102));

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

        jButton1.setBackground(new java.awt.Color(0, 204, 204));
        jButton1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Data Barang");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 204, 204));
        jButton2.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Data Pegawai");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 204, 204));
        jButton3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Data Transaksi");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 204, 204));
        jButton5.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Data Supplier");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(58, 179, 151));
        jButton6.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Transaksi");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(58, 179, 151));
        jLabel1.setText("Transaksi");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Id Transaksi");

        inputIdTransaksi.setBackground(new java.awt.Color(255, 255, 255));
        inputIdTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputIdTransaksiActionPerformed(evt);
            }
        });

        inputCariBarang.setBackground(new java.awt.Color(255, 255, 255));
        inputCariBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputCariBarangActionPerformed(evt);
            }
        });
        inputCariBarang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                inputCariBarangPropertyChange(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cari Barang");

        btnCari.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnCari.setForeground(new java.awt.Color(58, 179, 151));
        btnCari.setText("Cari Kode");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnCari1.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnCari1.setForeground(new java.awt.Color(58, 179, 151));
        btnCari1.setText("Cari Nama");
        btnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(58, 179, 151));
        jLabel4.setText("Atau cari barang dengan scan barcode pada produk");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputCariBarang)
                            .addComponent(inputIdTransaksi))
                        .addGap(12, 12, 12)
                        .addComponent(btnCari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCari1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputIdTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(btnCari1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(inputCariBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabelBarang.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelBarangMouseClicked(evt);
            }
        });
        tabelBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelBarangKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelBarang);

        jLabel5.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Jumlah Barang");

        inputJumlahBarang.setBackground(new java.awt.Color(255, 255, 255));
        inputJumlahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputJumlahBarangActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Belanja");

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Diskon");

        inputDiskonBarang.setBackground(new java.awt.Color(255, 255, 255));
        inputDiskonBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputDiskonBarangActionPerformed(evt);
            }
        });

        btnTambahBarang.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnTambahBarang.setForeground(new java.awt.Color(58, 179, 151));
        btnTambahBarang.setText("+");
        btnTambahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBarangActionPerformed(evt);
            }
        });

        btnKurangBarang.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnKurangBarang.setForeground(new java.awt.Color(58, 179, 151));
        btnKurangBarang.setText("-");
        btnKurangBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKurangBarangActionPerformed(evt);
            }
        });

        btnBayar.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnBayar.setForeground(new java.awt.Color(58, 179, 151));
        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        tabelTotalBelanja.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelTotalBelanja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTotalBelanjaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelTotalBelanja);

        jLabel9.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Total Harga");

        inputTotalHarga.setBackground(new java.awt.Color(255, 255, 255));
        inputTotalHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTotalHargaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Bayar");

        fieldBayar.setBackground(new java.awt.Color(255, 255, 255));
        fieldBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldBayarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Kembalian");

        fieldKembalian.setBackground(new java.awt.Color(255, 255, 255));
        fieldKembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldKembalianActionPerformed(evt);
            }
        });

        btnTambahKeKeranjang.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnTambahKeKeranjang.setForeground(new java.awt.Color(58, 179, 151));
        btnTambahKeKeranjang.setText("Tambah Ke Keranjang");
        btnTambahKeKeranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTambahKeKeranjangMouseClicked(evt);
            }
        });
        btnTambahKeKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKeKeranjangActionPerformed(evt);
            }
        });

        BtnHapusBarang.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        BtnHapusBarang.setForeground(new java.awt.Color(58, 179, 151));
        BtnHapusBarang.setText("Hapus Barang");
        BtnHapusBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusBarangActionPerformed(evt);
            }
        });

        btnCari6.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnCari6.setForeground(new java.awt.Color(58, 179, 151));
        btnCari6.setText("Cetak Struk");
        btnCari6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCari6ActionPerformed(evt);
            }
        });

        btnClear.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(58, 179, 151));
        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSelesai.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        btnSelesai.setForeground(new java.awt.Color(58, 179, 151));
        btnSelesai.setText("Selesai");
        btnSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelesaiActionPerformed(evt);
            }
        });

        IniWebcam.setMaximumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout IniWebcamLayout = new javax.swing.GroupLayout(IniWebcam);
        IniWebcam.setLayout(IniWebcamLayout);
        IniWebcamLayout.setHorizontalGroup(
            IniWebcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        IniWebcamLayout.setVerticalGroup(
            IniWebcamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 88, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2)
                                    .addComponent(jScrollPane1)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(inputTotalHarga)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(fieldBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(fieldKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(34, 34, 34))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(inputDiskonBarang)
                                                .addGap(72, 72, 72)))
                                        .addGap(42, 42, 42))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(inputJumlahBarang)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnKurangBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTambahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9))
                                    .addComponent(btnTambahKeKeranjang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(BtnHapusBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnCari6, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IniWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(IniWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTambahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnKurangBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(inputJumlahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputDiskonBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTambahKeKeranjang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(BtnHapusBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(fieldKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCari6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        DashboardAdmin.main(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        DataPegawaiAdmin.main(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        FormDataTransaksi.main(null);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        dispose();
        FormSupplierAdmin.main(null);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void inputIdTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputIdTransaksiActionPerformed
        
    }//GEN-LAST:event_inputIdTransaksiActionPerformed

    private void inputCariBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputCariBarangActionPerformed
    String barcodeData = inputCariBarang.getText().trim();
    
    // Validasi jika input tidak kosong
    if (!barcodeData.isEmpty()) {
        // Panggil metode pencarian atau proses barcodeData
        cariBarang("kode_barang", barcodeData);
    } else {
        JOptionPane.showMessageDialog(this, "Barcode tidak valid. Silakan pindai ulang.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Fokus kembali ke JTextField untuk input berikutnya
    inputCariBarang.requestFocusInWindow();

    }//GEN-LAST:event_inputCariBarangActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String keyword = inputCariBarang.getText();
        cariBarang("kode_barang", keyword);
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari1ActionPerformed
        String keyword = inputCariBarang.getText();
        cariBarang("nama_barang", keyword);
    }//GEN-LAST:event_btnCari1ActionPerformed

    private void inputJumlahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputJumlahBarangActionPerformed
        
    }//GEN-LAST:event_inputJumlahBarangActionPerformed

    private void inputDiskonBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputDiskonBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputDiskonBarangActionPerformed

    private void btnTambahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBarangActionPerformed
        quantity += 1;
        inputJumlahBarang.setText(Integer.toString(quantity));
    }//GEN-LAST:event_btnTambahBarangActionPerformed

    private void btnKurangBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKurangBarangActionPerformed
        quantity -= 1;
        if (quantity < 1) {
            JOptionPane.showMessageDialog(this, "Jumlah Barang Tidak Boleh Kurang Dari 1", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            quantity = 1;
        } else {
            inputJumlahBarang.setText(Integer.toString(quantity));
        }
        
    }//GEN-LAST:event_btnKurangBarangActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // Mengambil nilai total harga dari inputTotalHarga
    String totalHargaText = inputTotalHarga.getText().replace(".", "").
            replace(",", ".");
    float totalHarga = 0.0f;
    try {
        totalHarga = Float.parseFloat(totalHargaText); // Konversi totalHarga ke float
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Total harga harus berupa angka yang valid.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        return; // Keluar dari method jika total harga tidak valid
    }

    // Mengambil nilai uang bayar dari fieldBayar
    String bayarText = fieldBayar.getText().replace(".", "").replace(",", ".");
    float uangBayar = 0.0f;
    try {
        uangBayar = Float.parseFloat(bayarText); // Konversi uangBayar ke float
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Uang bayar harus berupa angka yang valid.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        return; // Keluar dari method jika uang bayar tidak valid
    }

    // Hitung kembalian
    float kembalian = uangBayar - totalHarga;
    
    if (kembalian < 0) {
        JOptionPane.showMessageDialog(this, "Uang bayar tidak cukup untuk membayar total harga.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        return; // Keluar dari method jika uang bayar tidak cukup
    }

    // Menampilkan kembalian di fieldKembalian
    fieldKembalian.setText(formatNumber(kembalian)); // Format kembalian dengan pemisah ribuan
    fieldKembalian.setEditable(false); // Menonaktifkan editing fieldKembalian

    // Optional: Reset fieldBayar
    }//GEN-LAST:event_btnBayarActionPerformed

    private void inputTotalHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTotalHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTotalHargaActionPerformed

    private void fieldBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldBayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldBayarActionPerformed

    private void fieldKembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldKembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldKembalianActionPerformed

    private void btnTambahKeKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKeKeranjangActionPerformed
    // Ambil nilai dari inputJumlahBarang dan perbarui variabel quantity
    try {
        String jumlahText = inputJumlahBarang.getText().trim();
        if (!jumlahText.isEmpty()) {
            quantity = Integer.parseInt(jumlahText); // Konversi ke integer
            if (quantity < 1) {
                quantity = 1; // Atur ke nilai default jika kurang dari 1
            }
        } else {
            quantity = 1; // Atur ke nilai default jika input kosong
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Jumlah barang harus berupa angka yang valid.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        quantity = 1; // Atur ke nilai default jika terjadi kesalahan konversi
    }

    // Ambil nilai dari inputDiskonBarang dan perbarui variabel diskon
    try {
        String diskonText = inputDiskonBarang.getText().trim(); // Ambil dan trim whitespace
        if (!diskonText.isEmpty()) { // Pastikan input tidak kosong
            diskon = Float.parseFloat(diskonText) / 100; // Konversi ke persentase
        } else {
            diskon = 0.0f; // Atur diskon ke default jika input kosong
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Diskon harus berupa angka yang valid.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        diskon = 0.0f; // Atur diskon ke default jika terjadi kesalahan konversi
    }
    
    float harga = 0.0f;
    try {
        harga = Float.parseFloat(hargaBarang); // Konversi hargaBarang ke float
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Harga barang harus berupa angka yang valid.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        harga = 0.0f; // Atur harga ke default jika terjadi kesalahan konversi
    }
    
    if (harga > 0) {
        // Hitung harga akhir per unit
        float hargaFinalPerUnit = harga * (1 - diskon);
        // Hitung total harga berdasarkan kuantitas
        float totalHarga = hargaFinalPerUnit * quantity;
        String hargaFinalStr = formatNumber(totalHarga); // Format total harga dengan pemisah ribuan

        DefaultTableModel tableModel = (DefaultTableModel) tabelTotalBelanja.getModel();
        boolean itemExists = false;

        // Periksa apakah item sudah ada di tabel
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingNamaBarang = tableModel.getValueAt(i, 1).toString();
            if (existingNamaBarang.equals(namaBarang)) {
                // Item sudah ada, perbarui kuantitas dan harga
                int existingQuantity = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                float existingTotalHarga = Float.parseFloat(tableModel.getValueAt(i, 3).toString()
                        .replace(".", "").replace(",", "."));

                // Update kuantitas
                int newQuantity = existingQuantity + quantity;
                float newTotalHarga = hargaFinalPerUnit * newQuantity;

                // Update tabel
                tableModel.setValueAt(Integer.toString(newQuantity), i, 2);
                tableModel.setValueAt(formatNumber(newTotalHarga), i, 3);

                itemExists = true;
                break;
            }
        }

        // Jika item tidak ditemukan di tabel, tambahkan sebagai item baru
        if (!itemExists) {
            String[] item = {kodeBarang, namaBarang, Integer.toString(quantity), hargaFinalStr};
            tableModel.addRow(item);
        }

        updateTotalHarga();

        // Kosongkan tabelBarang
        DefaultTableModel tableModelBarang = (DefaultTableModel) tabelBarang.getModel();
        tableModelBarang.setRowCount(0); // Menghapus semua baris di tabelBarang

        kodeBarang = "";
        namaBarang = ""; 
        hargaBarang = "";
        quantity = 1; // Atur kembali kuantitas ke nilai awal jika perlu
        inputJumlahBarang.setText(null);
        inputDiskonBarang.setText(null);
    }
    }//GEN-LAST:event_btnTambahKeKeranjangActionPerformed

    private void tabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelBarangMouseClicked
        int row = tabelBarang.rowAtPoint(evt.getPoint());
        // Store values from the selected row
        kodeBarang = tabelBarang.getValueAt(row, 0).toString();
        namaBarang = tabelBarang.getValueAt(row, 1).toString();
        hargaBarang = tabelBarang.getValueAt(row, 2).toString();
        inputJumlahBarang.setText(Integer.toString(quantity));
    }//GEN-LAST:event_tabelBarangMouseClicked

    private void BtnHapusBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusBarangActionPerformed
        int selectedRow = tabelTotalBelanja.getSelectedRow();

        // Periksa apakah ada baris yang dipilih
        if (selectedRow >= 0) {
            // Hapus baris dari model tabel
            DefaultTableModel tableModel = (DefaultTableModel) tabelTotalBelanja.getModel();
            tableModel.removeRow(selectedRow);
            updateTotalHarga();

            // Jika Anda juga menyimpan data di ArrayList, hapus item yang sesuai dari ArrayList
            // Misalnya, jika data di ArrayList sama dengan data di tabel
            if (selectedRow < order.size()) {
                order.remove(selectedRow);
            }

            // Opsional: Tampilkan pesan konfirmasi jika diinginkan
            JOptionPane.showMessageDialog(this, "Barang berhasil dihapus dari keranjang.", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Jika tidak ada baris yang dipilih, tampilkan pesan kesalahan
            JOptionPane.showMessageDialog(this, "Pilih barang yang ingin dihapus terlebih dahulu.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }//GEN-LAST:event_BtnHapusBarangActionPerformed

    private void btnCari6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCari6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCari6ActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
            // Menghapus seleksi di tabel
        tabelBarang.clearSelection();
        tabelTotalBelanja.clearSelection();

        // Menghapus semua input di textfield
        inputJumlahBarang.setText("");
        inputDiskonBarang.setText("");
        inputTotalHarga.setText(""); // Anda mungkin ingin mengatur ini juga

        fieldBayar.setText("");
        fieldKembalian.setText(""); // Pastikan field ini juga kosong

        // Mengatur nilai variabel ke default
        quantity = 1; // Atur kembali kuantitas ke nilai awal
        namaBarang = ""; 
        hargaBarang = "";
        diskon = 0.0f; // Reset diskon ke default

        // Menghapus semua baris di tabel total belanja
        DefaultTableModel tableModelTotalBelanja = (DefaultTableModel) tabelTotalBelanja.getModel();
        tableModelTotalBelanja.setRowCount(0); // Menghapus semua baris

        // Menghapus semua baris di tabel barang
        DefaultTableModel tableModelBarang = (DefaultTableModel) tabelBarang.getModel();
        tableModelBarang.setRowCount(0); // Menghapus semua baris
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelesaiActionPerformed
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = now.format(formatter);
    
    try {
        // 1. Insert data into transaksi
        String queryTransaksi = "INSERT INTO transaksi (tgl_transaksi, jumlah_total) VALUES (?, ?)";
        PreparedStatement pstmtTransaksi = mysqlconfig.prepareStatement(queryTransaksi, Statement
                .RETURN_GENERATED_KEYS);
        pstmtTransaksi.setString(1, formattedDateTime);
        
        float totalHarga = 0.0f;
        try {
            String totalHargaText = inputTotalHarga.getText().replace(".", "")
                    .replace(",", ".");
            totalHarga = Float.parseFloat(totalHargaText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Total harga harus berupa angka yang valid.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            pstmtTransaksi.close();
            return; // Keluar dari method jika total harga tidak valid
        }
        pstmtTransaksi.setFloat(2, totalHarga);
        
        int affectedRows = pstmtTransaksi.executeUpdate();
        if (affectedRows == 0) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            pstmtTransaksi.close();
            return;
        }
        
        // Get the generated id_transaksi
        ResultSet generatedKeys = pstmtTransaksi.getGeneratedKeys();
        if (generatedKeys.next()) {
            int idTransaksi = generatedKeys.getInt(1); // ambil id_transaksi yang baru
            pstmtTransaksi.close();

            // 2. Insert data into detail_transaksi
            String queryDetailTransaksi = "INSERT INTO detail_transaksi (id_transaksi, id_product, "
                    + "nama_product, kuantitas, harga) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmtDetail = mysqlconfig.prepareStatement(queryDetailTransaksi);

            DefaultTableModel tableModel = (DefaultTableModel) tabelTotalBelanja.getModel();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String idProduct = (String) tableModel.getValueAt(i, 0); // Ambil ID produk dari kolom pertama
                String namaProduct = (String) tableModel.getValueAt(i, 1); // Ambil ID produk dari kolom pertama
                int kuantitas = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                String hargaStr = tableModel.getValueAt(i, 3).toString().replace(".", "")
                        .replace(",", ".");
                float harga = Float.parseFloat(hargaStr);
                
                pstmtDetail.setInt(1, idTransaksi);
                pstmtDetail.setString(2, idProduct);
                pstmtDetail.setString(3, namaProduct);
                pstmtDetail.setInt(4, kuantitas);
                pstmtDetail.setFloat(5, harga);
                pstmtDetail.addBatch(); // Tambahkan ke batch
            }
            pstmtDetail.executeBatch(); // Eksekusi batch

            pstmtDetail.close();
            JOptionPane.showMessageDialog(this, "Transaksi dan detail transaksi berhasil disimpan.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mendapatkan ID transaksi.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btnSelesaiActionPerformed

    private void tabelTotalBelanjaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTotalBelanjaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelTotalBelanjaMouseClicked

    private void inputCariBarangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_inputCariBarangPropertyChange
        
    }//GEN-LAST:event_inputCariBarangPropertyChange

    private void tabelBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelBarangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelBarangKeyReleased

    private void btnTambahKeKeranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTambahKeKeranjangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTambahKeKeranjangMouseClicked

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHapusBarang;
    private javax.swing.JPanel IniWebcam;
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCari1;
    private javax.swing.JButton btnCari6;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnKurangBarang;
    private javax.swing.JButton btnSelesai;
    private javax.swing.JButton btnTambahBarang;
    private javax.swing.JButton btnTambahKeKeranjang;
    private javax.swing.JTextField fieldBayar;
    private javax.swing.JTextField fieldKembalian;
    private javax.swing.JTextField inputCariBarang;
    private javax.swing.JTextField inputDiskonBarang;
    private javax.swing.JTextField inputIdTransaksi;
    private javax.swing.JTextField inputJumlahBarang;
    private javax.swing.JTextField inputTotalHarga;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelBarang;
    private javax.swing.JTable tabelTotalBelanja;
    // End of variables declaration//GEN-END:variables
}
