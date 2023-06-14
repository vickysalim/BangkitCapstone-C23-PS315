import express from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";
import formidable from "formidable";
import { verifyToken } from "src/lib/helper";

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

  const [rows] = await conn.execute(sql, [id, userId, sellerId, productId, amount]);

  cart = rows;



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



  if (cart) return cart;

  return [];
}

router.post("/", async (req, res) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { userId, sellerId, productId, amount } = fields;

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
});

router.post("/update", async (req, res) => {
  const form = formidable({ multiples: true });

  form.parse(req, async (err, fields) => {
    const { userId, productId, amount } = fields;

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
});

router.post("/update-status", async (req, res) => {
  const form = formidable({ multiples: true });

  form.parse(req, async (err, fields) => {
    const { userId, productId, status } = fields;

    const cart = await updateCartStatus(userId as string, productId as string, status as string);

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
});

export default router;
