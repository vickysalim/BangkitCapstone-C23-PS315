CREATE TABLE `User` (
  `id` varchar(255) PRIMARY KEY,
  `fullName` varchar(100),
  `email` varchar(75),
  `password` varchar(255),
  `phone` varchar(15),
  `isSeller` boolean DEFAULT false,
  `profilePicUrl` varchar(255) DEFAULT "/assets/user/default.jpeg"
);

CREATE TABLE `UserAddress` (
  `id` varchar(255) PRIMARY KEY,
  `address` text,
  `province` varchar(75),
  `city` varchar(75),
  `kecamatan` varchar(75),
  `kodePos` varchar(10)
);

CREATE TABLE `Product` (
  `id` varchar(255) PRIMARY KEY,
  `sellerId` varchar(255),
  `name` varchar(255),
  `sellerName` varchar(100),
  `type` varchar(10),
  `price` int,
  `isAvailable` boolean,
  `description` text,
  `productPicUrls` varchar(255) COMMENT '[IS AN ARRAY]: Contains urls to images uploaded inside the public bucket.',
  `publishedAt` date
);

CREATE TABLE `CartItem` (
  `id` varchar(255) PRIMARY KEY,
  `userId` varchar(255),
  `productId` varchar(255),
  `status` varchar(10) DEFAULT "draft" COMMENT 'It can be draft, send, or complete. If complete, delete item gracefully and asynchronously using triggers.',
  `amount` int
);

CREATE TABLE `OrderHistory` (
  `id` varchar(255) PRIMARY KEY,
  `userId` varchar(255),
  `orderDate` date,
  `status` varchar(10) COMMENT 'Can be either processed, unpaid, delivering, or delivered.'
);

CREATE TABLE `OrderItem` (
  `id` varchar(255) PRIMARY KEY,
  `orderId` varchar(255),
  `cartItemId` varchar(255)
);

CREATE TABLE `Review` (
  `id` varchar(255) PRIMARY KEY,
  `productId` varchar(255),
  `userId` varchar(255),
  `rating` int,
  `title` varchar(255),
  `description` text
);

CREATE TABLE `FreshnessDataset` (
  `id` varchar(255) PRIMARY KEY,
  `userId` varchar(255),
  `name` varchar(255),
  `type` varchar(10),
  `isFresh` boolean,
  `nutritionDesc` text
);

ALTER TABLE `User` COMMENT = 'Stores user data.';

ALTER TABLE `Product` COMMENT = 'Stores product data.';

ALTER TABLE `CartItem` COMMENT = 'Stores individual cart item data.';

ALTER TABLE `UserAddress` ADD FOREIGN KEY (`id`) REFERENCES `User` (`id`);

ALTER TABLE `Product` ADD FOREIGN KEY (`sellerId`) REFERENCES `User` (`id`);

ALTER TABLE `CartItem` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `CartItem` ADD FOREIGN KEY (`productId`) REFERENCES `Product` (`id`);

ALTER TABLE `OrderHistory` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `OrderItem` ADD FOREIGN KEY (`orderId`) REFERENCES `OrderHistory` (`id`);

ALTER TABLE `OrderItem` ADD FOREIGN KEY (`cartItemId`) REFERENCES `CartItem` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`productId`) REFERENCES `Product` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);

ALTER TABLE `FreshnessDataset` ADD FOREIGN KEY (`userId`) REFERENCES `User` (`id`);
