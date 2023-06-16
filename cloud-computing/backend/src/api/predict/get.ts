import express from "express";
import connection from "../../config/db";

const router = express.Router();

// CREATE TABLE `FreshnessDataset` (
//   `id` varchar(255) NOT NULL,
//   `userId` varchar(255) DEFAULT NULL,
//   `productId` varchar(255) DEFAULT NULL,
//   `name` varchar(255) DEFAULT NULL,
//   `type` varchar(10) DEFAULT NULL,
//   `isFresh` tinyint(1) DEFAULT NULL,
//   `nutritionDesc` text DEFAULT NULL,
//   `pictureUrl` varchar(500) DEFAULT NULL
// ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

async function getAllFreshnessDataFromUser(userId: string) {
  const conn = await connection();
  let data = null;

  const sql = `
    SELECT * FROM FreshnessDataset WHERE userId = ?;
  `;

  const rows = await conn.execute(sql, [userId]);

  data = rows;

  if (data) return data;

  return [];
}

async function getAllFreshnessDataFromProduct(productId: string) {
  const conn = await connection();
  let data = null;

  const sql = `
    SELECT * FROM FreshnessDataset WHERE productId = ?;
  `;

  const rows = await conn.execute(sql, [productId]);

  data = rows;

  if (data) return data;

  return [];
}

async function getFreshnessDataById(id: string) {
  const conn = await connection();
  let data = null;

  const sql = `
    SELECT * FROM FreshnessDataset WHERE id = ?;
  `;

  const rows = await conn.execute(sql, [id]);

  data = rows[0 as keyof typeof rows];

  if (data) return data;

  return [];
}

router.get("/:userId", async (req, res) => {
  const { userId } = req.params;
  const { page, size } = req.query;

  const data = await getAllFreshnessDataFromUser(userId);

  if (page && !size) {
    const start = (parseInt(page as string) - 1) * 10;
    const end = start + 10;

    const paginatedData = data.slice(start, end);

    res.status(200).json(paginatedData);
  } else if (page && size) {
    const start = (parseInt(page as string) - 1) * parseInt(size as string);
    const end = start + parseInt(size as string);

    const paginatedData = data.slice(start, end);

    res.status(200).json(paginatedData);
  }

  res.status(200).json(data);

});

router.get("/product/:productId", async (req, res) => {
  const { productId } = req.params;

  const data = await getAllFreshnessDataFromProduct(productId);

  res.status(200).json(data);
});

router.get("/id/:id", async (req, res) => {
  const { id } = req.params;

  const data = await getFreshnessDataById(id);

  res.status(200).json(data);
});

export default router;