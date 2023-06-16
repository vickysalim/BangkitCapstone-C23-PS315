import express from "express";
import connection from "../../config/db";
import upload from "../../lib/multer";
import { verifyToken } from "../../lib/helper";

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
    DELETE FROM CartItem WHERE id = ? AND status <> ?;
  `;

  const rows = await conn.execute(sql, [id, "complete"]);
  await conn.end();
  
  cart = rows;



  if (cart) return cart;

  return [];
}

router.post("/", upload.none(), async (req, res) => {
  const { id, userId } = req.body;
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

export default router;
