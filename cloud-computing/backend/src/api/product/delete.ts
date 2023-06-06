import express, { Request, Response } from "express";
import connection from "../../config/db";
import { verifyToken } from "../../lib/helper";
import formidable from "formidable";

const router = express.Router();

// Product schema:
// id: varchar(255) (UUIDv4 using uuidjs) (primary key)
// sellerId: varchar(255) (UUIDv4 using uuidjs) (foreign key to User.id)
// name: varchar(255)
// sellerName: varchar(100)
// type: varchar(10) (enum: ["vegetable", "fruit", "other"])
// price: int
// isAvailable: boolean
// description: text
// productPicUrls: varchar(255) (array of strings)
// publishedAt: date

async function deleteProductById(id: string) {
  const conn = await connection();
  const sql = `
    DELETE FROM Product WHERE id = ?;
  `;

  const [rows] = await conn.execute(sql, [id]);

  return rows;
}

router.post("/", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const token = req.headers.authorization?.split(" ")[1];

  const verifyTokenResult = await verifyToken(token as string);

  form.parse(req, async (err, fields) => {
    const { id } = fields;

    if (verifyTokenResult.code !== "TOKEN_VERIFIED") {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });

      return;
    }

    const result = await deleteProductById(id as string);

    res.status(200).send(result);
  });
});

export default router;
