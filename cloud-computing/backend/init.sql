-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 14, 2023 at 09:56 AM
-- Server version: 10.11.4-MariaDB
-- PHP Version: 8.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `sifresh`
--

-- --------------------------------------------------------

--
-- Table structure for table `CartItem`
--

CREATE TABLE `CartItem` (
  `id` varchar(255) NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `sellerId` varchar(32) DEFAULT NULL,
  `productId` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT 'draft' COMMENT 'It can be draft, send, or complete. If complete, delete item gracefully and asynchronously using triggers.',
  `amount` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Stores individual cart item data.';

-- --------------------------------------------------------

--
-- Table structure for table `FreshnessDataset`
--

CREATE TABLE `FreshnessDataset` (
  `id` varchar(255) NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `productId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `isFresh` tinyint(1) DEFAULT NULL,
  `nutritionDesc` text DEFAULT NULL,
  `pictureUrl` varchar(500) DEFAULT "/uploads/freshness/default.jpg"
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `OrderHistory`
--

CREATE TABLE `OrderHistory` (
  `id` varchar(255) NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `orderDate` date DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL COMMENT 'Can be either processed, unpaid, delivering, or delivered.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `OrderItem`
--

CREATE TABLE `OrderItem` (
  `id` varchar(255) NOT NULL,
  `orderId` varchar(255) DEFAULT NULL,
  `cartItemId` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Product`
--

CREATE TABLE `Product` (
  `id` varchar(255) NOT NULL,
  `sellerId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `sellerName` varchar(100) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `isAvailable` tinyint(1) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `productPicUrls` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '[IS AN ARRAY]: Contains urls to images uploaded inside the public bucket.' CHECK (json_valid(`productPicUrls`)),
  `publishedAt` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Stores product data.';

-- --------------------------------------------------------

--
-- Table structure for table `Review`
--

CREATE TABLE `Review` (
  `id` varchar(255) NOT NULL,
  `productId` varchar(255) DEFAULT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
  `id` varchar(255) NOT NULL,
  `fullName` varchar(100) DEFAULT NULL,
  `email` varchar(75) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `isSeller` tinyint(1) DEFAULT 0,
  `profilePicUrl` varchar(255) DEFAULT '/assets/user/default.jpeg'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Stores user data.';

-- --------------------------------------------------------

--
-- Table structure for table `UserAddress`
--

CREATE TABLE `UserAddress` (
  `id` varchar(255) NOT NULL,
  `address` text DEFAULT NULL,
  `province` varchar(75) DEFAULT NULL,
  `city` varchar(75) DEFAULT NULL,
  `kecamatan` varchar(75) DEFAULT NULL,
  `kodePos` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `CartItem`
--
ALTER TABLE `CartItem`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `sellerId` (`sellerId`),
  ADD KEY `userId` (`userId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `FreshnessDataset`
--
ALTER TABLE `FreshnessDataset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`),
  ADD KEY `productId` (`productId`);

--
-- Indexes for table `OrderHistory`
--
ALTER TABLE `OrderHistory`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `OrderItem`
--
ALTER TABLE `OrderItem`
  ADD PRIMARY KEY (`id`),
  ADD KEY `orderId` (`orderId`),
  ADD KEY `cartItemId` (`cartItemId`);

--
-- Indexes for table `Product`
--
ALTER TABLE `Product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sellerId` (`sellerId`);

--
-- Indexes for table `Review`
--
ALTER TABLE `Review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `productId` (`productId`),
  ADD KEY `userId` (`userId`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `UserAddress`
--
ALTER TABLE `UserAddress`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `CartItem`
--
ALTER TABLE `CartItem`
  ADD CONSTRAINT `CartItem_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  ADD CONSTRAINT `CartItem_ibfk_2` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`);

--
-- Constraints for table `FreshnessDataset`
--
ALTER TABLE `FreshnessDataset`
  ADD CONSTRAINT `FreshnessDataset_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

--
-- Constraints for table `OrderHistory`
--
ALTER TABLE `OrderHistory`
  ADD CONSTRAINT `OrderHistory_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

--
-- Constraints for table `OrderItem`
--
ALTER TABLE `OrderItem`
  ADD CONSTRAINT `OrderItem_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `OrderHistory` (`id`),
  ADD CONSTRAINT `OrderItem_ibfk_2` FOREIGN KEY (`cartItemId`) REFERENCES `CartItem` (`id`);

--
-- Constraints for table `Product`
--
ALTER TABLE `Product`
  ADD CONSTRAINT `Product_ibfk_1` FOREIGN KEY (`sellerId`) REFERENCES `User` (`id`);

--
-- Constraints for table `Review`
--
ALTER TABLE `Review`
  ADD CONSTRAINT `Review_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `Product` (`id`),
  ADD CONSTRAINT `Review_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

--
-- Constraints for table `UserAddress`
--
ALTER TABLE `UserAddress`
  ADD CONSTRAINT `UserAddress_ibfk_1` FOREIGN KEY (`id`) REFERENCES `User` (`id`);
COMMIT;
