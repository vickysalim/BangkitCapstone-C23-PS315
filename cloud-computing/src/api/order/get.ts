import express from "express";
import connection from "../../config/db";

const router = express.Router();

// Table OrderItem {
//   id varchar(255) [pk]
//   orderId varchar(255) [ref: - OrderHistory.id]
//   cartItemId varchar(255) [ref: - CartItem.id]
// }

async function getAllOrderFromUserId(id: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    SELECT * FROM OrderHistory WHERE userId = ?;
  `;

  const [rows] = await conn.execute(sql, [id]);

  order = rows;

  await conn.end();

  if (order) return order;

  return [];
}

async function getOrderFromUserId(id: string, orderId: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    SELECT * FROM OrderHistory WHERE userId = ? AND id = ?;
  `;

  const [rows] = await conn.execute(sql, [id, orderId]);

  order = rows;

  await conn.end();

  if (order) return order;

  return [];
}

router.get("/:id", async (req, res) => {
  const { id } = req.params;

  const order = await getAllOrderFromUserId(id);

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

router.get("/:id/:orderId", async (req, res) => {
  const { id, orderId } = req.params;

  const order = await getOrderFromUserId(id, orderId);

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
