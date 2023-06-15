import express, { Request, Response } from "express";
import connection from "../../config/db";
import { verifyToken } from "../../lib/helper";
import { Storage } from "@google-cloud/storage";
import upload from "../../lib/multer";

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

  const productInfo = `
    SELECT * FROM Product WHERE id = ?;
  `;

  const sql = `
    DELETE FROM Product WHERE id = ?;
  `;

  const [productInfoRows] = await conn.execute(productInfo, [id]);

  const product: any = productInfoRows[0 as keyof typeof productInfoRows];

  const productPicUrls = JSON.parse(
    product.productPicUrls as string,
  ) as string[];

  const storage = new Storage().bucket(process.env.GCP_BUCKET_NAME as string);

  for (const productPicUrl of productPicUrls) {
    const sanitizedFileName = productPicUrl.replace(
      `https://storage.googleapis.com/${process.env.GCP_BUCKET_NAME}/`,
      "",
    );

    await storage.file(sanitizedFileName).delete();
  }

  const rows = await conn.execute(sql, [id]);

  return rows;
}

router.post("/", upload.none(), async (req: Request, res: Response) => {
  const { id } = req.body;
  const token = req.headers.authorization?.split(" ")[1];

  const verifyTokenResult = await verifyToken(token as string);

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

export default router;
