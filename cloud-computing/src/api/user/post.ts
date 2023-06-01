import express, { Request, Response } from "express";
import conn from "../../config/db";
import { v4 as uuidv4 } from "uuid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { emailExists, idExists, phoneExists } from "../../lib/helper";

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

function addUser(
  fullName: string,
  email: string,
  password: string,
  phone: string,
  isSeller: boolean,
  profilePicUrl?: string,
) {
  const id = uuidv4();
  const hashedPassword = bcrypt.hashSync(password, 10);

  let user = null;
  let error: {
    code: string | null;
    message: string | null;
  } | null = {
    code: null,
    message: null,
  };

  if (emailExists(email)) {
    error.code = "EMAIL_EXISTS_ERROR";
    error.message = "Email already exists";
    return error;
  }

  if (phoneExists(phone)) {
    error.code = "PHONE_EXISTS_ERROR";
    error.message = "Phone already exists";
    return error;
  }

  const sql = `
    INSERT INTO
      User
    VALUES
      (?, ?, ?, ?, ?, ?)
  `;

  conn.execute(
    sql,
    [id, fullName, email, hashedPassword, phone, isSeller, profilePicUrl],
    (err: any, result: any) => {
      if (err) throw err;
      user = result;
    },
  );

  if (user) return user;

  return {};
}

// MUST USE ID FROM USER TABLE
function addUserAddress(
  id: string,
  address: string,
  province: string,
  city: string,
  kecamatan: string,
  kodePos: string,
) {
  let userAddress = null;
  const error: {
    code: string | null;
    message: string | null;
  } = {
    code: null,
    message: null,
  };

  if (!idExists(id)) {
    error.code = "ID_NOT_FOUND_ERROR";
    error.message = "ID not found";
    return error;
  }

  const sql = `
    INSERT INTO
      UserAddress
    VALUES
      (?, ?, ?, ?, ?, ?)
  `;

  conn.execute(
    sql,
    [id, address, province, city, kecamatan, kodePos],
    (err: any, result: any) => {
      if (err) throw err;
      userAddress = result;
    },
  );

  if (userAddress) return userAddress;

  return {};
}

function updateUser(
  id: string,
  fullName: string,
  email: string,
  phone: string,
  isSeller: boolean,
  profilePicUrl?: string,
) {
  let user = null;

  const sql = `
    UPDATE
      User
    SET
      fullName = ?,
      email = ?,
      phone = ?,
      isSeller = ?,
      profilePicUrl = ?
    WHERE
      id = ?
  `;

  conn.execute(
    sql,
    [fullName, email, phone, isSeller, profilePicUrl, id],
    (err: any, result: any) => {
      if (err) throw err;
      user = result;
    },
  );

  if (user) return user;

  return {};
}

function updateUserAddress(
  id: string,
  address: string,
  province: string,
  city: string,
  kecamatan: string,
  kodePos: string,
) {
  let userAddress = null;

  const sql = `
    UPDATE
      UserAddress
    SET
      address = ?,
      province = ?,
      city = ?,
      kecamatan = ?,
      kodePos = ?
    WHERE
      id = ?
  `;

  conn.execute(
    sql,
    [address, province, city, kecamatan, kodePos, id],
    (err: any, result: any) => {
      if (err) throw err;
      userAddress = result;
    },
  );

  if (userAddress) return userAddress;

  return {};
}

function updatePassword(id: string, password: string, newPassword: string) {
  let user: any = null;
  const error: {
    code: string | null;
    message: string | null;
  } = {
    code: null,
    message: null,
  };

  const compare = `
    SELECT
      password
    FROM
      User
    WHERE
      id = ?
  `;

  conn.execute(compare, [id], (err: any, result: any) => {
    if (err) throw err;
    user = result;
  });

  if (user) {
    if (bcrypt.compareSync(password, user.password)) {
      const hashedPassword = bcrypt.hashSync(newPassword, 10);

      const sql = `
        UPDATE
          User
        SET
          password = ?
        WHERE
          id = ?
      `;

      conn.execute(sql, [hashedPassword, id], (err: any, result: any) => {
        if (err) throw err;
        user = result;
      });

      if (user) return user;

      return {};
    } else {
      error.code = "PASSWORD_NOT_MATCH_ERROR";
      error.message = "Password not match";
      return error;
    }
  } else {
    error.code = "ID_NOT_FOUND_ERROR";
    error.message = "ID not found";
    return error;
  }
}

function login(email: string, password: string) {
  let user: {
    cred: any;
    jwt: string | null;
  } = {
    cred: null,
    jwt: null,
  };
  const error: {
    code: string | null;
    message: string | null;
  } = {
    code: null,
    message: null,
  };

  const sql = `
    SELECT
      *
    FROM
      User
    WHERE
      email = ?
  `;

  conn.execute(sql, [email], (err: any, result: any) => {
    if (err) throw err;
    user.cred = result;
  });

  if (user) {
    if (bcrypt.compareSync(password, user.cred.password)) {
      user.jwt = jwt.sign(
        { id: user.cred.id },
        process.env.JWT_SECRET_KEY as string,
      );

      return user;
    } else {
      error.code = "PASSWORD_NOT_MATCH_ERROR";
      error.message = "Password not match";
      return error;
    }
  } else {
    error.code = "EMAIL_NOT_FOUND_ERROR";
    error.message = "Email not found";
    return error;
  }
}

router.post("/add", (req: Request, res: Response) => {
  const { fullName, email, password, phone, isSeller, profilePicUrl } =
    req.body;

  const user = addUser(
    fullName,
    email,
    password,
    phone,
    isSeller,
    profilePicUrl,
  );

  res.status(201).json(user);
});

router.post("/add-address", (req: Request, res: Response) => {
  const { id, address, province, city, kecamatan, kodePos } = req.body;

  const userAddress = addUserAddress(
    id,
    address,
    province,
    city,
    kecamatan,
    kodePos,
  );

  res.status(201).json(userAddress);
});

router.put("/update", (req: Request, res: Response) => {
  const { id, fullName, email, phone, isSeller, profilePicUrl } = req.body;

  const user = updateUser(id, fullName, email, phone, isSeller, profilePicUrl);

  res.status(201).json(user);
});

router.put("/update-address", (req: Request, res: Response) => {
  const { id, address, province, city, kecamatan, kodePos } = req.body;

  const userAddress = updateUserAddress(
    id,
    address,
    province,
    city,
    kecamatan,
    kodePos,
  );

  res.status(201).json(userAddress);
});

router.put("/update-pass", (req: Request, res: Response) => {
  const { id, password, newPassword } = req.body;

  const user = updatePassword(id, password, newPassword);

  res.json(user);
});

router.post("/authenticate", (req: Request, res: Response) => {
  const { email, password } = req.body;

  const user = login(email, password);

  res.status(201).json(user);
});

export default router;
