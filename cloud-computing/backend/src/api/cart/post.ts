import express from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";

const router = express.Router();

// Table CartItem {
//   id varchar(255) [pk]
//   userId varchar(255) [ref: > User.id]
//   productId varchar(255) [ref: - Product.id]
//   status varchar(10) [default: "draft", Note: "It can be draft, send, or complete. If complete, delete item gracefully and asynchronously using triggers."]

//   amount int

//   Note: 'Stores individual cart item data.'
// }

async function createCart(userId: string, productId: string, amount: number) {
  const conn = await connection();
  let cart = null;
  const id = uuidv4();

  const sql = `
    INSERT INTO CartItem (id, userId, productId, amount) VALUES (?, ?, ?, ?);
  `;

  const [rows] = await conn.execute(sql, [id, userId, productId, amount]);

  cart = rows;

  await conn.end();

  if (cart) return cart;

  return [];
}

async function updateCart(id: string, productId: string, amount: number) {
  const conn = await connection();
  let cart = null;

  const sql = `
    UPDATE CartItem SET amount = ? WHERE userId = ? AND productId = ?;
  `;

  const [rows] = await conn.execute(sql, [amount, id, productId]);

  cart = rows;

  await conn.end();

  if (cart) return cart;

  return [];
}

async function updateCartStatus(id: string, productId: string, status: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    UPDATE CartItem SET status = ? WHERE userId = ? AND productId = ?;
  `;

  const [rows] = await conn.execute(sql, [id, status, productId]);

  cart = rows;

  await conn.end();

  if (cart) return cart;

  return [];
}

router.post("/", async (req, res) => {
  const { userId, productId, amount } = req.body;

  const cart = await createCart(userId, productId, amount);

  if (cart) {
    res.status(200).json({
      message: "Cart item created",
    });
  } else {
    res.status(404).json({
      message: "Cart item not created",
    });
  }
});

router.put("/update", async (req, res) => {
  const { userId, productId, amount } = req.body;

  const cart = await updateCart(userId, productId, amount);

  if (cart) {
    res.status(200).json({
      message: "Cart item updated",
    });
  } else {
    res.status(404).json({
      message: "Cart item not updated",
    });
  }
});

router.put("/update-status", async (req, res) => {
  const { userId, productId, status } = req.body;

  const cart = await updateCartStatus(userId, productId, status);

  if (cart) {
    res.status(200).json({
      message: "Cart item status updated",
    });
  } else {
    res.status(404).json({
      message: "Cart item status not updated",
    });
  }
});

export default router;
