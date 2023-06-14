import express from "express";
import connection from "../../config/db";
import formidable from "formidable";
import { verifyToken } from "../../lib/helper";

const router = express.Router();

// TABLE `OrderHistory` (
//   `id` varchar(255) NOT NULL,
//   `userId` varchar(255) DEFAULT NULL,
//   `orderDate` date DEFAULT NULL,
//   `status` varchar(10) DEFAULT 'processed' COMMENT 'Can be either processed, unpaid, delivering, or delivered.'
// )

async function deleteHistory(id: string) {
  const conn = await connection();
  let order = null;

  const sql = `
    DELETE FROM OrderHistory WHERE id = ?;
  `;

  const [rows] = await conn.execute(sql, [id]);

  order = rows;

  if (order) return order;

  return [];
}

router.post("/", async (req, res) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization;

  const token = authHeader?.split(" ")[1];

  const verifiedToken = await verifyToken(token as string);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
  } else {
    form.parse(req, async (err, fields) => {
      const { id, userId } = fields;

      if (verifiedToken.id !== userId) {
        res.status(403).json({
          code: "UNAUTHORIZED_ERROR",
          message: "Unauthorized",
        });
        return;
      }

      const order = await deleteHistory(id as string);

      if (order) {
        res.status(200).json({
          message: "Success",
          data: order,
        });
      } else {
        res.status(404).json({
          message: "Orders not found",
        });
      }
    });
  }
});

export default router;