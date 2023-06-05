import express, { Request, Response } from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";

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
  productPicUrls: string[],
  publishedAt: Date,
) {
  const conn = await connection();
  const id = uuidv4();

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

  await conn.end();

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
  productPicUrls: string[],
  publishedAt: Date,
) {
  const conn = await connection();
  const sql = `
    UPDATE Product
    SET sellerId = ?, name = ?, sellerName = ?, type = ?, price = ?, isAvailable = ?, description = ?, productPicUrls = ?, publishedAt = ?
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
    JSON.stringify(productPicUrls),
    publishedAt,
    id,
  ]);

  await conn.end();

  return rows;
}

router.post("/", async (req: Request, res: Response) => {
  const {
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    productPicUrls,
    publishedAt,
  } = req.body;

  const id = await addProduct(
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    productPicUrls,
    publishedAt,
  );

  res.status(201).json({
    id,
  });
});

router.put("/", async (req: Request, res: Response) => {
  const {
    id,
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    productPicUrls,
    publishedAt,
  } = req.body;

  await updateProduct(
    id,
    sellerId,
    name,
    sellerName,
    type,
    price,
    isAvailable,
    description,
    productPicUrls,
    publishedAt,
  );

  res.status(201).json({
    id,
  });
});

export default router;
