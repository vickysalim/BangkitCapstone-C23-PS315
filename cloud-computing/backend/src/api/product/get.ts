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

  if (products) return products;

  return [];
}

async function getProductsByName(name: string) {
  const conn = await connection();
  let products = null;

  const sql = `
    SELECT * FROM Product WHERE name LIKE ?;
  `;

  const [rows] = await conn.execute(sql, [`%${name}%`]);

  products = rows;

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

  if (products) return products;

  return [];
}

router.get("/", async (req: Request, res: Response) => {
  const { page, size } = req.query;
  let startIndex = 0;
  let endIndex = 0;

  let pageReferred = page;
  let sizeReferred = size;

  const products: any = await getAllProducts();

  if (pageReferred && sizeReferred) {
    startIndex = (Number(pageReferred) - 1) * Number(sizeReferred);
    endIndex = Number(pageReferred) * Number(sizeReferred);
    res.status(200).json(products.slice(startIndex, endIndex));
  } else if (pageReferred && !sizeReferred) {
    startIndex = (Number(pageReferred) - 1) * 10;
    endIndex = Number(pageReferred) * 10;
    res.status(200).json(products.slice(startIndex, endIndex));
  } else {
    res.status(200).json(products);
  }
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

router.get("/name/:name", async (req: Request, res: Response) => {
  const { name } = req.params;

  const products = await getProductsByName(name);

  res.status(200).json(products);
});

router.get("/type/:type", async (req: Request, res: Response) => {
  const { type } = req.params;

  const products = await getProductsByType(type);

  res.status(200).json(products);
});

export default router;
