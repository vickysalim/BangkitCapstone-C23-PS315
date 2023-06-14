import express from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";
import formidable from "formidable";
import { verifyToken } from "../../lib/helper";

const router = express.Router();

// TABLE `OrderHistory` (
//   `id` varchar(255) NOT NULL,
//   `userId` varchar(255) DEFAULT NULL,
//   `orderDate` date DEFAULT NULL,
//   `status` varchar(10) DEFAULT 'processed' COMMENT 'Can be either processed, unpaid, delivering, or delivered.'
// )

async function createOrderHistory(userId: string) {
  const conn = await connection();
  let order = null;
  const id = uuidv4();

  const sql = `
    INSERT INTO OrderHistory (id, userId) VALUES (?, ?);
  `;

  const [rows] = await conn.execute(sql, [id, userId]);

  order = rows;

  if (order) return order;

  return [];
}

async function updateStatus(id: string, userId: string, status: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    UPDATE OrderHistory SET status = ? WHERE id = ? AND userId = ?;
  `;

  const [rows] = await conn.execute(sql, [status, id, userId]);

  order = rows;

  if (order) return order;

  return [];
}

router.post("/", async (req, res) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization;

  const token = authHeader?.split(" ")[1];

  const verifiedToken = await verifyToken(token as string);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { userId } = fields;

    if (verifiedToken.id !== userId) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const order = await createOrderHistory(userId as string);

    if (order) {
      res.status(201).json({
        code: "SUCCESS",
        message: "Order history created successfully",
        data: order,
      });
    } else {
      res.status(500).json({
        code: "SERVER_ERROR",
        message: "Failed to create order history",
      });
    }
  });
});

router.post("/update", async (req, res) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization;

  const token = authHeader?.split(" ")[1];

  const verifiedToken = await verifyToken(token as string);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { id, userId, status } = fields;

    if (verifiedToken.id !== userId) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const order = await updateStatus(id as string, userId as string, status as string);

    if (order) {
      res.status(201).json({
        code: "SUCCESS",
        message: "Order history status updated successfully",
        data: order,
      });
    } else {
      res.status(500).json({
        code: "SERVER_ERROR",
        message: "Failed to update order history status",
      });
    }
  });
});

export default router;