import express, { Request, Response } from "express";
import connection from "../../config/db";

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

async function getAllUsers() {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql);

  users = rows;

  await conn.end();

  if (users) return users;

  return [];
}

async function getUserById(id: string) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [id]);

  user = rows;

  await conn.end();

  if (user) return user;

  return {};
}

async function getUserByEmail(email: string) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [email]);

  user = rows;

  await conn.end();

  if (user) return user;

  return {};
}

async function getUserByPhone(phone: string) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [phone]);

  user = rows;

  await conn.end();

  if (user) return user;

  return {};
}

router.get("/", async (req: Request, res: Response) => {
  const users = await getAllUsers();

  res.status(200).json({
    users,
  });
});

router.get("/:id", async (req: Request, res: Response) => {
  const { id } = req.params;

  const user = await getUserById(id as string);

  if (!user) {
    res.status(404).json({
      message: "User not found",
    });
  }

  res.status(200).json({
    user,
  });
});

router.get("/email/:email", async (req: Request, res: Response) => {
  const { email } = req.params;

  const user = await getUserByEmail(email as string);

  res.status(200).json({
    user,
  });
});

router.get("/phone/:phone", async (req: Request, res: Response) => {
  const { phone } = req.params;

  const user = await getUserByPhone(phone as string);

  res.status(200).json({
    user,
  });
});

export default router;
