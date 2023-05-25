import express from "express";
import conn from "server/config/db";

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

  conn.execute(sql, (err, result) => {
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

  conn.execute(sql, [id], (err, result) => {
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

  conn.execute(sql, [email], (err, result) => {
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

  conn.execute(sql, [phone], (err, result) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return {};
}

router.get("/getAll", (req, res) => {
  const users = getAllUsers();

  res.json({
    users,
  });
});

router.get("/getById", (req, res) => {
  const { id } = req.query;

  const user = getUserById(id as string);

  res.json({
    user,
  });
});

router.get("/getByEmail", (req, res) => {
  const { email } = req.query;

  const user = getUserByEmail(email as string);

  res.json({
    user,
  });
});

router.get("/getByPhone", (req, res) => {
  const { phone } = req.query;

  const user = getUserByPhone(phone as string);

  res.json({
    user,
  });
});

export default router;