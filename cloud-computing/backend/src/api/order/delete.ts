import express from "express";
import connection from "../../config/db";
import formidable from "formidable";

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

router.post("/", async (req, res) => {
  const form = formidable({ multiples: true });

  form.parse(req, async (err, fields) => {
    const { id } = fields;

    await deleteOrderItem(id as string);

    res.status(200).json({
      message: "Order item deleted successfully",
    });
  });
});

export default router;
