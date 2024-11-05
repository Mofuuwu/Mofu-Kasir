-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 05, 2024 at 03:34 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasirtoko_muhammadrifqi`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(12) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(10203, 'muhammadrifqi26', '260806'),
(10204, 'hasaniroqi223', '232346'),
(10205, 'kehidupan', 'kehidupan'),
(10206, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `kode_barang` int NOT NULL,
  `nama_barang` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `harga` int NOT NULL,
  `stok` int NOT NULL,
  `tanggal_masuk` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `tanggal_kadaluarsa` varchar(100) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`kode_barang`, `nama_barang`, `harga`, `stok`, `tanggal_masuk`, `tanggal_kadaluarsa`) VALUES
(1, 'indomie', 6000, 20, '2024-01-25', '2024-5-20'),
(7, 'rafif', 100000, 1, '2024-02-30', '2024-03-01'),
(15, 'buku tulis', 3000, 20, '2024-08-20', '2024-12-29'),
(16, 'bolpoin', 1500, 100, '2024-01-25', '2024-08-25'),
(17, 'telur ayam', 3000, 90, '2024-01-25', '2024-12-25'),
(18, 'pensil', 1000, 20, '2024-9-20', '2024-12-30'),
(19, 'laptop', 1000000, 10, '2024-09-09', '2024-09-09');

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_detail` int NOT NULL,
  `id_transaksi` int NOT NULL,
  `id_product` int NOT NULL,
  `nama_product` varchar(100) NOT NULL,
  `kuantitas` int NOT NULL,
  `harga` decimal(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_detail`, `id_transaksi`, `id_product`, `nama_product`, `kuantitas`, `harga`) VALUES
(4, 9, 7, 'rafif', 2, 200000.00),
(5, 9, 1, 'indomie', 6, 36000.00),
(6, 10, 17, 'telur ayam', 7, 21000.00),
(7, 10, 1, 'indomie', 8, 48000.00),
(8, 10, 16, 'bolpoin', 12, 18000.00),
(9, 11, 7, 'rafif', 6, 600000.00),
(10, 11, 1, 'indomie', 6, 36000.00),
(11, 12, 1, 'indomie', 7, 42000.00),
(12, 13, 1, 'indomie', 1, 6000.00),
(13, 13, 15, 'buku tulis', 1, 3000.00),
(14, 14, 7, 'rafif', 2, 200000.00),
(15, 15, 15, 'buku tulis', 6, 18000.00),
(16, 16, 1, 'indomie', 4, 24000.00),
(17, 16, 15, 'buku tulis', 24, 72000.00);

-- --------------------------------------------------------

--
-- Table structure for table `kasir`
--

CREATE TABLE `kasir` (
  `id_petugas` int NOT NULL,
  `nama_petugas` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(12) COLLATE utf8mb4_general_ci NOT NULL,
  `tgl_daftar` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `kasir`
--

INSERT INTO `kasir` (`id_petugas`, `nama_petugas`, `email`, `alamat`, `username`, `password`, `tgl_daftar`) VALUES
(1, 'rafif dwi saputra', 'rafifdws@gmail.com', 'pancurawis', 'rafifdw10', '202345', '2024-01-24 15:30:00'),
(2, 'muhammad moses', 'moseshijrah77@gmail.com', 'kalikidang', 'mmoses20', '224567', '2024-01-24 15:30:00'),
(4, 'rafif', 'rafif', 'rafif', 'rafif', 'rafif', '2024-04-25 00:00:00'),
(5, 'regina', 'regina!2@gmail.com', 'mondstadt', 'regina', 'regina', '2024-04-28 00:00:00'),
(8, 'arianto', 'ari@gmail.com', 'purwokerto', 'ari', 'ari.1', '2024-05-16 00:00:00'),
(9, 'angga', 'jsfhjrgj', 'euhdjgrjf', 'angga', 'angga', '2024-05-31 00:00:00'),
(10, 'esdrtygj', 'rdfg', 'zfn', 'kehidupan', 'kehidupan', '2024-08-07 00:00:00'),
(11, 'efw', 'pp', 'pp', 'pp', 'pp', '2024-08-07 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `kode_supplier` int NOT NULL,
  `nama` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `alamat` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `kode_pos` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `no_telp` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `no_rek` varchar(100) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`kode_supplier`, `nama`, `alamat`, `kode_pos`, `no_telp`, `email`, `no_rek`) VALUES
(1, 'Rafif dwi sudarno', 'Mersi', '91836', '088757546675', 'rafifffff@gmail.com', '8732761891388731'),
(2, 'Ibnuu Fuso', 'Tanjung', '928719', '029271836282', 'Elvira02919272@gmail.com', '019281937'),
(6, 'Salis', 'Ajibarang', '02839', '01928282729', 'salisrobi@gmail.com', '02291729173');

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int NOT NULL,
  `tgl_transaksi` datetime NOT NULL,
  `jumlah_total` decimal(65,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `tgl_transaksi`, `jumlah_total`) VALUES
(9, '2024-09-08 22:36:49', 236000.00),
(10, '2024-09-08 22:42:45', 87000.00),
(11, '2024-09-09 11:44:10', 636000.00),
(12, '2024-09-09 11:48:41', 42000.00),
(13, '2024-09-17 20:39:26', 9000.00),
(14, '2024-09-17 21:44:30', 200000.00),
(15, '2024-09-25 11:05:42', 18000.00),
(16, '2024-09-25 11:46:29', 96000.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indexes for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_product` (`id_product`),
  ADD KEY `id_transaksi` (`id_transaksi`);

--
-- Indexes for table `kasir`
--
ALTER TABLE `kasir`
  ADD PRIMARY KEY (`id_petugas`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`kode_supplier`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10207;

--
-- AUTO_INCREMENT for table `barang`
--
ALTER TABLE `barang`
  MODIFY `kode_barang` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id_detail` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `kasir`
--
ALTER TABLE `kasir`
  MODIFY `id_petugas` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `kode_supplier` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`),
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`id_product`) REFERENCES `barang` (`kode_barang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
