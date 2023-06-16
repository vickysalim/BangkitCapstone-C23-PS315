import express from "express";
import connection from "../../config/db";
import { uuidv4 } from "../../lib/uuid";
import { verifyToken } from "../../lib/helper";
import upload from "../../lib/multer";

const router = express.Router();

// Table CartItem {
//   id varchar(255) [pk]
//   userId varchar(255) [ref: > User.id]
//   productId varchar(255) [ref: - Product.id]
//   status varchar(10) [default: "draft", Note: "It can be draft, send, or complete. If complete, delete item gracefully and asynchronously using triggers."]

//   amount int

//   Note: 'Stores individual cart item data.'
// }

async function createCart(userId: string, sellerId: string, productId: string, amount: number) {
  const conn = await connection();
  let cart = null;
  const id = uuidv4();

  const sql = `
    INSERT INTO CartItem (id, userId, sellerId, productId, amount) VALUES (?, ?, ?, ?, ?);
  `;

  const rows = await conn.execute(sql, [id, userId, sellerId, productId, amount]);

  cart = rows;

  if (cart) return cart;

  return [];
}

async function updateCart(id: string, productId: string, amount: number) {
  const conn = await connection();
  let cart = null;

  const sql = `
    UPDATE CartItem SET amount = ? WHERE userId = ? AND productId = ? AND status <> ?;
  `;

  const rows = await conn.execute(sql, [amount, id, productId, 'complete']);

  cart = rows;

  if (cart) return cart;

  return [];
}

async function updateCartStatus(id: string, sellerId: string, status: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    UPDATE CartItem SET status = ? WHERE userId = ? AND sellerId = ?;
  `;

  const rows = await conn.execute(sql, [status, id, sellerId]);

  cart = rows;

  if (cart) return cart;

  return [];
}

router.post("/", upload.none(), async (req, res) => {
  const { userId, sellerId, productId, amount } = req.body;
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  if (verifiedToken.id !== userId) {
    res.status(403).json({
      code: "UNAUTHORIZED_ERROR",
      message: "Unauthorized",
    });
    return;
  }

  const cart = await createCart(userId as string, sellerId as string, productId as string, Number(amount));

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

router.post("/update", upload.none(), async (req, res) => {
  const { userId, productId, amount } = req.body;

  const cart = await updateCart(userId as string, productId as string, Number(amount));

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

router.post("/update-status", upload.none(), async (req, res) => {
  const { userId, sellerId, status } = req.body;

  const cart = await updateCartStatus(userId as string, sellerId as string, status as string);

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
