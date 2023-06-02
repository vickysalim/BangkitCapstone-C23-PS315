import express from "express";
import connection from "../../config/db";

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

  await conn.end();
}

router.delete("/:id", async (req, res) => {
  const { id } = req.params;

  await deleteOrderItem(id);

  res.status(200).json({
    message: "Order item deleted successfully",
  });
});

export default router;
