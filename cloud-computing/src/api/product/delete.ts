import express, { Request, Response } from "express";
import connection from "../../config/db";

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

  await conn.end();

  return rows;
}

router.delete("/:id", async (req: Request, res: Response) => {
  const { id } = req.params;

  const result = await deleteProductById(id);

  res.status(200).send(result);
});

export default router;