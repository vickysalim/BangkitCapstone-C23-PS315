import express from "express";
import conn from "../../config/db";
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

function addProduct(
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
  const id = uuidv4();

  const sql = `
    INSERT INTO Product (id, sellerId, name, sellerName, type, price, isAvailable, description, productPicUrls, publishedAt)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
  `;

  conn.execute(
    sql,
    [
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
    ],
    (err: any, result: any) => {
      if (err) throw err;
      return result;
    },
  );

  return id;
}

function updateProduct(
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
  const sql = `
    UPDATE Product
    SET sellerId = ?, name = ?, sellerName = ?, type = ?, price = ?, isAvailable = ?, description = ?, productPicUrls = ?, publishedAt = ?
    WHERE id = ?;
  `;

  conn.execute(
    sql,
    [
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
    ],
    (err: any, result: any) => {
      if (err) throw err;
      return result;
    },
  );
}

router.post("/", (req, res) => {
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

  const id = addProduct(
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

  res.json({
    id,
  });
});

router.put("/", (req, res) => {
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

  updateProduct(
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

  res.json({
    id,
  });
});

export default router;
