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

router.delete("/:id", async (req, res) => {
  const { id } = req.params;

  const cart = await deleteCartItem(id);

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
