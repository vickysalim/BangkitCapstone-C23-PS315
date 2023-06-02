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

async function getAllProducts() {
  const conn = await connection();
  let products = null;

  const sql = `
    SELECT * FROM Product;
  `;

  const [rows] = await conn.execute(sql);

  products = rows;

  await conn.end();

  if (products) return products;

  return [];
}

async function getProductById(id: string) {
  const conn = await connection();
  let product = null;

  const sql = `
    SELECT * FROM Product WHERE id = ?;
  `;

  const [rows] = await conn.execute(sql, [id]);

  product = rows[0 as keyof typeof rows];

  await conn.end();

  if (product) return product;

  return [];
}

async function getProductsBySellerId(sellerId: string) {
  const conn = await connection();
  let products = null;

  const sql = `
    SELECT * FROM Product WHERE sellerId = ?;
  `;

  const [rows] = await conn.execute(sql, [sellerId]);

  products = rows;

  await conn.end();

  if (products) return products;

  return [];
}

async function getProductsByType(type: string) {
  const conn = await connection();
  let products = null;

  const sql = `
    SELECT * FROM Product WHERE type = ?;
  `;

  const [rows] = await conn.execute(sql, [type]);

  products = rows;

  await conn.end();

  if (products) return products;

  return [];
}

router.get("/", async (req: Request, res: Response) => {
  const products = await getAllProducts();

  res.status(200).json(products);
});

router.get("/:id", async (req: Request, res: Response) => {
  const { id } = req.params;

  const product = await getProductById(id);

  res.status(200).json(product);
});

router.get("/seller/:sellerId", async (req: Request, res: Response) => {
  const { sellerId } = req.params;

  const products = await getProductsBySellerId(sellerId);

  res.status(200).json(products);
});

router.get("/type/:type", async (req: Request, res: Response) => {
  const { type } = req.params;

  const products = await getProductsByType(type);

  res.status(200).json(products);
});

export default router;
