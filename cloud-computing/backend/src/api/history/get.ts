import express from "express";
import connection from "../../config/db";

const router = express.Router();

// TABLE `OrderHistory` (
//   `id` varchar(255) NOT NULL,
//   `userId` varchar(255) DEFAULT NULL,
//   `orderDate` date DEFAULT NULL,
//   `status` varchar(10) DEFAULT 'processed' COMMENT 'Can be either processed, unpaid, delivering, or delivered.'
// )

async function getOrderHistoryFromUserId(userId: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    SELECT * FROM OrderHistory WHERE userId = ?;
  `;

  const rows = await conn.execute(sql, [userId]);

  order = rows;

  if (order) return order;

  return [];
}

async function getOrderHistoryFromId(id: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    SELECT * FROM OrderHistory WHERE id = ?;
  `;

  const rows = await conn.execute(sql, [id]);

  order = rows;

  if (order) return order;

  return [];
}

router.get("/user/:id", async (req, res) => {
  const { id } = req.params;

  const order = await getOrderHistoryFromUserId(id);

  if (order) {
    res.status(200).json({
      message: "Success",
      data: order,
    });
  } else {
    res.status(404).json({
      message: "Orders not found",
    });
  }
});

router.get("/:id", async (req, res) => {
  const { id } = req.params;

  const order = await getOrderHistoryFromId(id);

  if (order) {
    res.status(200).json({
      message: "Success",
      data: order,
    });
  } else {
    res.status(404).json({
      message: "Order not found",
    });
  }
});

export default router;