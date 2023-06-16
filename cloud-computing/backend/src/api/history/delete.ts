import express from "express";
import connection from "../../config/db";
import upload from "../../lib/multer";
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

  const rows = await conn.execute(sql, [id]);
  await conn.end();

  order = rows;

  if (order) return order;

  return [];
}

router.post("/", upload.none(), async (req, res) => {
  const { id, userId } = req.body;
  const authHeader = req.headers.authorization;

  const token = authHeader?.split(" ")[1];

  const verifiedToken = await verifyToken(token as string);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
  } else {
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
  }
});

export default router;