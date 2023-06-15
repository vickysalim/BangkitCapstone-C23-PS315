import express from "express";
import connection from "../../config/db";
import upload from "../../lib/multer";

const router = express.Router();

// Table OrderItem {
//   id varchar(255) [pk]
//   orderId varchar(255) [ref: - OrderHistory.id]
//   cartItemId varchar(255) [ref: - CartItem.id]
// }

async function deleteOrderItem(id: string) {
  const conn = await connection();

  const sql = `
    DELETE FROM OrderItem WHERE id = ?;
  `;

  await conn.execute(sql, [id]);


}

router.post("/", upload.none(), async (req, res) => {
  const { id } = req.body;

  await deleteOrderItem(id as string);

  res.status(200).json({
    message: "Order item deleted successfully",
  });
});

export default router;
