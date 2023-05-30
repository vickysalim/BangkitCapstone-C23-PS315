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

function getAllProducts() {
  let products = null;

  const sql = `
    SELECT * FROM Product;
  `;

  conn.execute(sql, (err: any, result: any) => {
    if (err) throw err;
    products = result;
  });

  if (products) return products;

  return [];
}

function getProductById(id: string) {
  let product = null;

  const sql = `
    SELECT * FROM Product WHERE id = ?;
  `;

  conn.execute(sql, [id], (err: any, result: any) => {
    if (err) throw err;
    product = result;
  });

  if (product) return product;

  return [];
}

function getProductsBySellerId(sellerId: string) {
  let products = null;

  const sql = `
    SELECT * FROM Product WHERE sellerId = ?;
  `;

  conn.execute(sql, [sellerId], (err: any, result: any) => {
    if (err) throw err;
    products = result;
  });

  if (products) return products;

  return [];
}

function getProductsByType(type: string) {
  let products = null;

  const sql = `
    SELECT * FROM Product WHERE type = ?;
  `;

  conn.execute(sql, [type], (err: any, result: any) => {
    if (err) throw err;
    products = result;
  });

  if (products) return products;

  return [];
}

router.get("/", (req: Request, res: Response) => {
  const products = getAllProducts();

  res.status(200).json(products);
});

router.get("/:id", (req: Request, res: Response) => {
  const { id } = req.params;

  const product = getProductById(id);

  res.status(200).json(product);
});

router.get("/seller/:sellerId", (req: Request, res: Response) => {
  const { sellerId } = req.params;

  const products = getProductsBySellerId(sellerId);

  res.status(200).json(products);
});

router.get("/type/:type", (req: Request, res: Response) => {
  const { type } = req.params;

  const products = getProductsByType(type);

  res.status(200).json(products);
});

export default router;
