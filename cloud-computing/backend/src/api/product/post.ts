import express, { Request, Response } from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";
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

async function addProduct(
  sellerId: string,
  name: string,
  sellerName: string,
  type: string,
  price: number,
  isAvailable: boolean,
  description: string,
  productPics: formidable.File | formidable.File[],
  publishedAt: Date,
) {
  const storage = new Storage();
  const conn = await connection();
  const id = uuidv4();

  let productPicUrls: string[] = [];

  productPics = Array.isArray(productPics) ? productPics : [productPics];

  for (let i = 0; i < productPics.length; i++) {
    const productPic = productPics[i];

    const productPicExtension = productPic.mimetype?.split("/")[1];

    const productPicName = `/uploads/${id}-${i}.${productPicExtension}`;

    await storage.bucket(process.env.GCP_BUCKET_NAME as string).upload(productPic.filepath, {
      destination: productPicName,
      gzip: true,
      metadata: {
        cacheControl: "public, max-age=31536000",
      },
    });

    productPicUrls.push(
      `https://storage.googleapis.com/${process.env.GCP_BUCKET_NAME}${productPicName}`,
    );
  }

  const sql = `
    INSERT INTO Product (id, sellerId, name, sellerName, type, price, isAvailable, description, productPicUrls, publishedAt)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
  `;

  const [rows] = await conn.execute(sql, [
    id,
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    JSON.stringify(productPicUrls),
    publishedAt,
  ]);

  return rows;
}

async function updateProduct(
  id: string,
  sellerId: string,
  name: string,
  sellerName: string,
  type: string,
  price: number,
  isAvailable: boolean,
  description: string,
  publishedAt: Date,
) {
  const conn = await connection();
  const sql = `
    UPDATE Product
    SET sellerId = ?, name = ?, sellerName = ?, type = ?, price = ?, isAvailable = ?, description = ?, publishedAt = ?
    WHERE id = ?;
  `;

  const [rows] = await conn.execute(sql, [
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    publishedAt,
    id,
  ]);

  return rows;
}

router.post("/", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields, files) => {
    const {
      sellerId,
      name,
      sellerName,
      type,
      price,
      isAvailable,
      description,
      publishedAt,
    } = fields;

    const { productPics } = files;

    if (verifiedToken.id !== sellerId) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const id = await addProduct(
      sellerId as string,
      name as string,
      sellerName as string,
      type as string,
      Number(price),
      isAvailable === "true" ? true : false,
      description as string,
      productPics,
      new Date(publishedAt as string),
    );

    res.status(201).json({
      id,
    });
  });
});

router.post("/update", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {

    const {
      id,
      sellerId,
      name,
      sellerName,
      type,
      price,
      isAvailable,
      description,
      publishedAt,
    } = fields;

    await updateProduct(
      id as string,
      sellerId as string,
      name as string,
      sellerName as string,
      type as string,
      Number(price),
      isAvailable === "true" ? true : false,
      description as string,
      new Date(publishedAt as string),
    );

    res.status(201).json({
      id,
    });
  });
});

export default router;
