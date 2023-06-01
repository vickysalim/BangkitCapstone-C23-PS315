import express, { Request, Response } from "express";
import conn from "../../config/db";

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

function deleteProductById(id: string) {
  const sql = `
    DELETE FROM Product WHERE id = ?;
  `;

  conn.execute(sql, [id], (err: any, result: any) => {
    if (err) throw err;
    return result;
  });
}

router.delete("/:id", (req: Request, res: Response) => {
  const { id } = req.params;

  const result = deleteProductById(id);

  res.status(200).send(result);
});

export default router;