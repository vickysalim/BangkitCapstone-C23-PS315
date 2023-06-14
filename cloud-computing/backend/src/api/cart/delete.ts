import express from "express";
import connection from "../../config/db";
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

async function deleteCartItem(id: string) {
  const conn = await connection();
  let cart = null;

  const sql = `
    DELETE FROM CartItem WHERE id = ?;
  `;

  const [rows] = await conn.execute(sql, [id]);

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
    const { id, userId } = fields;

    if (verifiedToken.id !== userId) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const cart = await deleteCartItem(id as string);

    if (cart) {
      res.status(200).json({
        message: "Cart item deleted",
      });
    } else {
      res.status(404).json({
        message: "Cart item not found",
      });
    }

  });
});

export default router;
