import express from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";

const router = express.Router();

// Table OrderItem {
//   id varchar(255) [pk]
//   orderId varchar(255) [ref: - OrderHistory.id]
//   cartItemId varchar(255) [ref: - CartItem.id]
// }

async function createOrder(userId: string, cartItemId: string) {
  const conn = await connection();
  let order = null;
  const id = uuidv4();

  const sql = `
    INSERT INTO OrderItem (id, orderId, cartItemId) VALUES (?, ?, ?);
  `;

  const [rows] = await conn.execute(sql, [id, userId, cartItemId]);

  order = rows;

  await conn.end();

  if (order) return order;

  return [];
}

router.post("/", async (req, res) => {
  const { userId, cartItemId } = req.body;

  const order = await createOrder(userId, cartItemId);

  if (order) {
    res.status(201).json({
      message: "Order created",
      order,
    });
  } else {
    res.status(500).json({
      message: "Internal server error",
    });
  }
});

export default router;
