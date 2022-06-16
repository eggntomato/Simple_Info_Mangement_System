-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 16, 2022 lúc 04:57 PM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `qlkh`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `guest`
--

CREATE TABLE `guest` (
  `id` int(11) NOT NULL,
  `ten` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `ns` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dc` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sdt` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `guest`
--

INSERT INTO `guest` (`id`, `ten`, `ns`, `dc`, `sdt`, `avatar`) VALUES
(3, 'Nguyễn Văn Bắc', '31/12/2000', 'Thái Nguyên', '0912121212', 'http://192.168.1.69/qlkh/img/anh1.jpg'),
(4, 'Nguyễn Xina', '21/04/2000', 'Thái Nguyên', '0912886677', 'http://192.168.1.69/qlkh/img/johnxina.png'),
(5, 'Nguyễn Văn Vinh', '21/12/2012', 'Thái Nguyên', '0988988988', ''),
(6, 'Nguyễn Văn Test', '21/12/2012', 'Bắc Giang', '0199877652', ''),
(35, 'Nguyen Van Test 2022', '21/12/2000', 'Bac Giang', '093333333', ''),
(44, 'Test API 123', '21/04/1999', 'Thai Nguyen', '0936993666', ''),
(45, 'Nguyen Van BA', '2022', 'DC', '0988929211', ''),
(46, 'Nguyen Van Vinh', '2022', 'Thai Nguyen', 'So Dien Thoai', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `login`
--

INSERT INTO `login` (`id`, `username`, `password`) VALUES
(1, 'admin', '12345'),
(2, 'admin1', 'admin1');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_unique` (`username`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `guest`
--
ALTER TABLE `guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT cho bảng `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
