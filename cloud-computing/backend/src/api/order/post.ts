import express from "express";
import connection from "../../config/db";
import { uuidv4 } from "../../lib/uuid";
import { verifyToken } from "../../lib/helper";
import upload from "../../lib/multer";

const router = express.Router();

// Table OrderItem {
//   id varchar(255) [pk]
//   orderId varchar(255) [ref: - OrderHistory.id]
//   cartItemId varchar(255) [ref: - CartItem.id]
// }

async function createOrder(userId: string, cartItemId: string) {
  const conn = await connection();
  let order = null;
  const id = uuidv4();

  const sql = `
    INSERT INTO OrderItem (id, orderId, cartItemId) VALUES (?, ?, ?);
  `;

  const rows = await conn.execute(sql, [id, userId, cartItemId]);

  order = rows;

  if (order) return order;

  return [];
}

router.post("/", upload.none(), async (req, res) => {
  const { userId, cartItemId } = req.body;
  const authHeader = req.headers.authorization;

  const token = authHeader?.split(" ")[1];

  const verifiedToken = await verifyToken(token as string);

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

  const order = await createOrder(userId as string, cartItemId as string);

  if (order) {
    res.status(201).json({
      code: "ORDER_CREATED",
      message: "Order created",
      order,
    });
  } else {
    res.status(500).json({
      code: "INTERNAL_SERVER_ERROR",
      message: "Internal server error",
    });
  }
});

export default router;
