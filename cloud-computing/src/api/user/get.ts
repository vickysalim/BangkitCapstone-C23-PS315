import express, { Request, Response } from "express";
import conn from "../../config/db";

const router = express.Router();

// User schema:
// id: varchar(255) (UUIDv4 using uuidjs) (primary key)
// fullName: varchar(100)
// email: varchar(75)
// password: varchar(255) (hashed using bcrypt with 10 rounds)
// phone: varchar(15)
// isSeller: boolean
// profilePicUrl: varchar(255)

// UserAddress schema:
// id: varchar(255) (UUIDv4 using uuidjs) (primary key) linked to User.id
// address: text
// province: varchar(75)
// city: varchar(75)
// kecamatan: varchar(75)
// kodePos: varchar(10)

function getAllUsers() {
  let users = null;

  const sql = `
    SELECT
      User.*, UserAddress.address, UserAddress.province, UserAddress.city, UserAddress.kecamatan, UserAddress.kodePos
    FROM
      User
    LEFT JOIN
      UserAddress
    ON
      User.id = UserAddress.id
  `;

  conn.execute(sql, (err: any, result: any) => {
    if (err) throw err;
    users = result;
  });

  if (users) return users;

  return [];
}

function getUserById(id: string) {
  let user = null;

  const sql = `
    SELECT
      User.*, UserAddress.address, UserAddress.province, UserAddress.city, UserAddress.kecamatan, UserAddress.kodePos
    FROM
      User
    LEFT JOIN
      UserAddress
    ON
      User.id = UserAddress.id
    WHERE
      User.id = ?
  `;

  conn.execute(sql, [id], (err: any, result: any) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return {};
}

function getUserByEmail(email: string) {
  let user = null;

  const sql = `
    SELECT
      User.*, UserAddress.address, UserAddress.province, UserAddress.city, UserAddress.kecamatan, UserAddress.kodePos
    FROM
      User
    LEFT JOIN
      UserAddress
    ON
      User.id = UserAddress.id
    WHERE
      User.email = ?
  `;

  conn.execute(sql, [email], (err: any, result: any) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return {};
}

function getUserByPhone(phone: string) {
  let user = null;

  const sql = `
    SELECT
      User.*, UserAddress.address, UserAddress.province, UserAddress.city, UserAddress.kecamatan, UserAddress.kodePos
    FROM
      User
    LEFT JOIN
      UserAddress
    ON
      User.id = UserAddress.id
    WHERE
      User.phone = ?
  `;

  conn.execute(sql, [phone], (err: any, result: any) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return {};
}

router.get("/", (req: Request, res: Response) => {
  const users = getAllUsers();

  res.status(200).json({
    users,
  });
});

router.get("/:id", (req: Request, res: Response) => {
  const { id } = req.params;

  const user = getUserById(id as string);

  res.status(200).json({
    user,
  });
});

router.get("/email/:email", (req: Request, res: Response) => {
  const { email } = req.params;

  const user = getUserByEmail(email as string);

  res.status(200).json({
    user,
  });
});

router.get("/phone/:phone", (req: Request, res: Response) => {
  const { phone } = req.params;

  const user = getUserByPhone(phone as string);

  res.status(200).json({
    user,
  });
});

export default router;
