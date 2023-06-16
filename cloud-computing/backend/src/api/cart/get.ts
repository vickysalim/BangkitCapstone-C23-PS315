import express from "express";
import connection from "../../config/db";

const router = express.Router();

// Table CartItem {
//   id varchar(255) [pk]
//   userId varchar(255) [ref: > User.id]
//   productId varchar(255) [ref: - Product.id]
//   status varchar(10) [default: "draft", Note: "It can be draft, send, or complete. If complete, delete item gracefully and asynchronously using triggers."]

//   amount int

//   Note: 'Stores individual cart item data.'
// }

async function getAllCartFromUserId(id: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    SELECT * FROM CartItem WHERE userId = ?;
  `;

  const rows = await conn.execute(sql, [id]);

  cart = rows;



  if (cart) return cart;

  return [];
}

async function getCartBySellerFromUserId(id: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    SELECT c.sellerId, u.fullName, SUM(p.price * c.amount) AS totalPrice FROM CartItem c JOIN user u ON c.sellerId = u.id JOIN product p ON c.productId = p.id WHERE c.userId = ? AND c.status <> ? GROUP BY c.sellerId;
  `;

  const rows = await conn.execute(sql, [id, 'complete']);

  cart = rows;

  if (cart) {
    for (const item of rows as any) {
      const sellerId = item.sellerId;

      const itemSql = `
        SELECT c.*, p.name AS productName, p.price AS productPrice FROM CartItem c JOIN product p ON c.productId = p.id WHERE c.userId = ? AND c.sellerId = ? AND c.status <> ?;
      `;

      const productRows = await conn.execute(itemSql, [id, sellerId, 'complete']);

      item.products = productRows;
    }
  }

  return cart ? cart : [];
}

async function getCartFromUserIdAndSellerId(id: string, sellerId: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    SELECT c.*, p.name AS productName, p.price AS productPrice FROM CartItem c JOIN product p ON c.productId = p.id WHERE c.userId = ? AND c.sellerId = ? AND c.status <> ?;
  `;

  const rows = await conn.execute(sql, [id, sellerId, 'complete']);

  cart = rows;

  if (cart) return cart;

  return [];
}

async function getCartFromUserId(id: string, productId: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    SELECT * FROM CartItem WHERE userId = ? AND productId = ? AND status <> ?;
  `;

  const rows = await conn.execute(sql, [id, productId, 'complete']);

  cart = rows;



  if (cart) return cart;

  return [];
}

router.get("/:id", async (req, res) => {
  const { id } = req.params;

  const cart = await getAllCartFromUserId(id);

  if (cart) {
    res.status(200).json({
      message: "Cart item found",
      data: cart,
    });
  } else {
    res.status(404).json({
      message: "Cart item not found",
    });
  }
});

router.get("/seller/:id", async (req, res) => {
  const { id } = req.params;

  const cart = await getCartBySellerFromUserId(id);

  if (cart) {
    res.status(200).json({
      message: "Cart item found",
      data: cart,
    });
  } else {
    res.status(404).json({
      message: "Cart item not found",
    });
  }
});

router.get("/seller/:id/:sellerId", async (req, res) => {
  const { id, sellerId } = req.params;

  const cart = await getCartFromUserIdAndSellerId(id, sellerId);

  if (cart) {
    res.status(200).json({
      message: "Cart item found",
      data: cart,
    });
  } else {
    res.status(404).json({
      message: "Cart item not found",
    });
  }
});

router.get("/:id/:productId", async (req, res) => {
  const { id, productId } = req.params;

  const cart = await getCartFromUserId(id, productId);

  if (cart) {
    res.status(200).json({
      message: "Cart item found",
      data: cart,
    });
  } else {
    res.status(404).json({
      message: "Cart item not found",
    });
  }
});

export default router;