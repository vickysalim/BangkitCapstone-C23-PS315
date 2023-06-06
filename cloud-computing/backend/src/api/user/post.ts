import express, { Request, Response } from "express";
import connection from "../../config/db";
import { v4 as uuidv4 } from "uuid";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";
import { emailExists, phoneExists, verifyToken } from "../../lib/helper";
import formidable from "formidable";
import { Storage } from "@google-cloud/storage";

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

async function addUser(
  fullName: string,
  email: string,
  password: string,
  phone: string,
  isSeller: boolean,
  profilePic?: formidable.File,
) {
  const conn = await connection();
  const storage = new Storage();
  const id = uuidv4();
  const hashedPassword = bcrypt.hashSync(password, 10);

  const profilePicExtension = profilePic?.mimetype?.split("/")[1];

  if (!profilePicExtension || !["jpg", "jpeg", "png"].includes(profilePicExtension)) {
    return {
      code: "PROFILE_PIC_EXTENSION_ERROR",
      message: "Profile picture extension is not supported",
    };
  }

  const profilePicUrl = "/uploads/" + id + "." + profilePicExtension;

  await storage.bucket(process.env.GCLOUD_STORAGE_BUCKET as string).upload(profilePic?.filepath as string, {
    gzip: true,
    metadata: {
      cacheControl: "public, max-age=31536000",
    },
  });

  let user = null;
  let error: {
    code: string | null;
    message: string | null;
  } | null = {
    code: null,
    message: null,
  };

  if (await emailExists(email)) {
    error.code = "EMAIL_EXISTS_ERROR";
    error.message = "Email already exists";
    return error;
  }

  if (await phoneExists(phone)) {
    error.code = "PHONE_EXISTS_ERROR";
    error.message = "Phone already exists";
    return error;
  }

  const sql = `
    INSERT INTO
      User
    VALUES
      (?, ?, ?, ?, ?, ?, ?)
  `;

  const [rows] = await conn.execute(sql, [
    id,
    fullName,
    email,
    hashedPassword,
    phone,
    isSeller,
    profilePicUrl,
  ]);

  user = rows;

  if (user) return user;

  return {};
}

// MUST USE ID FROM USER TABLE
async function addUserAddress(
  id: string,
  address: string,
  province: string,
  city: string,
  kecamatan: string,
  kodePos: string,
) {
  const conn = await connection();
  let userAddress = null;

  const sql = `
    INSERT INTO
      UserAddress
    VALUES
      (?, ?, ?, ?, ?, ?)
  `;

  const [rows] = await conn.execute(sql, [
    id,
    address,
    province,
    city,
    kecamatan,
    kodePos,
  ]);

  userAddress = rows;

  if (userAddress) return userAddress;

  return {};
}

async function updateUser(
  id: string,
  fullName: string,
  email: string,
  phone: string,
  isSeller: boolean,
  profilePicUrl?: string,
) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [
    fullName,
    email,
    phone,
    isSeller,
    profilePicUrl,
    id,
  ]);

  user = rows;

  if (user) return user;

  return {};
}

async function updateUserProfilePic(
  id: string,
  profilePic: formidable.File,
) {
  const conn = await connection();
  const storage = new Storage();

  const profilePicExtension = profilePic?.mimetype?.split("/")[1];

  if (!profilePicExtension || !["jpg", "jpeg", "png"].includes(profilePicExtension)) {
    return {
      code: "PROFILE_PIC_EXTENSION_ERROR",
      message: "Profile picture extension is not supported",
    };
  }

  const profilePicUrl = "/uploads/" + id + "." + profilePicExtension;

  // Delete old profile picture from storage
  const sql1 = `
    SELECT
      profilePicUrl
    FROM
      User
    WHERE
      id = ?
  `;

  const [rows1]: any = await conn.execute(sql1, [
    id,
  ]);

  const oldProfilePicUrl = rows1[0 as keyof typeof rows1].profilePicUrl as unknown as string;

  const oldProfilePicFilename = oldProfilePicUrl.split("/")[2];

  await storage.bucket(process.env.GCLOUD_STORAGE_BUCKET as string).file(oldProfilePicFilename).delete();

  await storage.bucket(process.env.GCLOUD_STORAGE_BUCKET as string).upload(profilePic?.filepath as string, {
    gzip: true,
    metadata: {
      cacheControl: "public, max-age=31536000",
    },
  });

  let user = null;

  const sql = `
    UPDATE
      User
    SET
      profilePicUrl = ?
    WHERE
      id = ?
  `;

  const [rows] = await conn.execute(sql, [
    profilePicUrl,
    id,
  ]);

  user = rows;

  if (user) return user;

  return {};
}

async function updateUserAddress(
  id: string,
  address: string,
  province: string,
  city: string,
  kecamatan: string,
  kodePos: string,
) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [
    address,
    province,
    city,
    kecamatan,
    kodePos,
    id,
  ]);

  userAddress = rows;

  if (userAddress) return userAddress;

  return {};
}

async function updatePassword(
  id: string,
  password: string,
  newPassword: string,
) {
  const conn = await connection();
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

  const [rows] = await conn.execute(compare, [id]);

  user = rows[0 as keyof typeof rows];

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

      const [rowsB] = await conn.execute(sql, [hashedPassword, id]);

      user = rowsB;

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

async function login(email: string, password: string) {
  const conn = await connection();
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

  const [rows] = await conn.execute(sql, [email]);

  user.cred = rows[0 as keyof typeof rows];

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

router.post("/add", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });

  form.parse(req, async (err, fields, files) => {
    const { fullName, email, password, phone, isSeller } = fields;
    const { profilePic } = files;

    const user = await addUser(
      fullName as string,
      email as string,
      password as string,
      phone as string,
      isSeller === "true" ? true : false,
      profilePic as formidable.File,
    );

    res.status(201).json(user);
  });
});

router.post("/add-address", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });

  form.parse(req, async (err, fields) => {
    const { id, address, province, city, kecamatan, kodePos } = fields;

    const userAddress = await addUserAddress(
      id as string,
      address as string,
      province as string,
      city as string,
      kecamatan as string,
      kodePos as string,
    );

    res.status(201).json(userAddress);
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
    const { id, fullName, email, phone, isSeller } = fields;

    if (verifiedToken.id !== id) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const user = await updateUser(
      id as string,
      fullName as string,
      email as string,
      phone as string,
      isSeller === "true" ? true : false,
    );

    res.status(201).json(user);
  });
});

router.post("/update-profile-pic", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields, files) => {
    const { id } = fields;
    const { profilePic } = files;

    if (verifiedToken.id !== id) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const user = await updateUserProfilePic(
      id as string,
      profilePic as formidable.File,
    );

    res.status(201).json(user);
  });
});

router.post("/update-address", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { id, address, province, city, kecamatan, kodePos } = fields;

    if (verifiedToken.id !== id) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const userAddress = await updateUserAddress(
      id as string,
      address as string,
      province as string,
      city as string,
      kecamatan as string,
      kodePos as string,
    );

    res.status(201).json(userAddress);
  });
});

router.post("/update-pass", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { id, password, newPassword } = fields;

    if (verifiedToken.id !== id) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        message: "Unauthorized",
      });
      return;
    }

    const user = await updatePassword(id as string, password as string, newPassword as string);

    res.json(user);
  });
});

router.post("/authenticate", async (req: Request, res: Response) => {
  const { email, password } = req.body;

  const user = await login(email, password);

  res.status(201).json(user);
});

export default router;
