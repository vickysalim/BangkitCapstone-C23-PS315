import express, { Request, Response } from "express";
import connection from "../../config/db";
import { uuidv4 } from "../../lib/uuid";
import jwt from "jsonwebtoken";
import { emailExists, phoneExists, verifyToken } from "../../lib/helper";
import { Storage } from "@google-cloud/storage";
import { comparePassword, encryptPassword } from "../../lib/encrypt";
import upload from "../../lib/multer";

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
) {
  const conn = await connection();
  const id = uuidv4();
  const hashedPassword = encryptPassword(password);

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

  const getUserInfoByEmailAddress = `
    SELECT
      *
    FROM
      User
    WHERE
      email = ?
  `;

  await conn.execute(sql, [
    id,
    fullName,
    email,
    hashedPassword,
    phone,
    isSeller,
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/4c0ef67c-568b-4820-b523-9763924995b1.png",
  ]);

  const [rows] = await conn.execute(getUserInfoByEmailAddress, [email]);

  user = rows[0 as keyof typeof rows];

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

  const getUserAddress = `
    SELECT
      *
    FROM
      UserAddress
    WHERE
      id = ?
  `;

  await conn.execute(sql, [
    id,
    address,
    province,
    city,
    kecamatan,
    kodePos,
  ]);

  const [rows] = await conn.execute(getUserAddress, [id]);

  userAddress = rows[0 as keyof typeof rows];

  if (userAddress) return userAddress;

  return {};
}

async function updateUser(
  id: string,
  fullName: string,
  email: string,
  phone: string,
  isSeller: boolean,
) {
  const conn = await connection();
  let user = null;

  const getUserInfoById = `
    SELECT
      *
    FROM
      User
    WHERE
      id = ?
  `;

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

  const [rowsTemp] = await conn.execute(getUserInfoById, [id]);
  user = rowsTemp[0 as keyof typeof rowsTemp];

  await conn.execute(sql, [
    fullName,
    email,
    phone,
    isSeller,
    // eslint-disable-next-line @typescript-eslint/dot-notation
    user["profilePicUrl"],
    id,
  ]);

  const [rows] = await conn.execute(getUserInfoById, [id]);
  user = rows[0 as keyof typeof rows];

  if (user) return user;

  return {};
}

async function updateUserProfilePic(
  id: string,
  profilePic: Express.Multer.File,
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

  let profilePicUrl = "uploads/" + id + "." + profilePicExtension;

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

  const oldProfilePicUrlUnsanitized = rows1[0 as keyof typeof rows1].profilePicUrl as unknown as string;
  const oldProfilePicUrl = oldProfilePicUrlUnsanitized.replace(`https://storage.googleapis.com/${process.env.GCP_BUCKET_NAME}/`, "");

  await storage.bucket(process.env.GCP_BUCKET_NAME as string).file(oldProfilePicUrl).delete();

  await storage.bucket(process.env.GCP_BUCKET_NAME as string).upload(profilePic?.path as string, {
    destination: profilePicUrl,
    gzip: true,
    metadata: {
      cacheControl: "public, max-age=31536000",
    },
  });

  let user = null;
  profilePicUrl = `https://storage.googleapis.com/${process.env.GCP_BUCKET_NAME}/${profilePicUrl}`;

  const sql = `
    UPDATE
      User
    SET
      profilePicUrl = ?
    WHERE
      id = ?
  `;

  const getUserInfoById = `
    SELECT
      *
    FROM
      User
    WHERE
      id = ?
  `;

  await conn.execute(sql, [
    profilePicUrl,
    id,
  ]);

  const [rows] = await conn.execute(getUserInfoById, [id]);

  user = rows[0 as keyof typeof rows];

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

  const getUserAddress = `
    SELECT
      *
    FROM
      UserAddress
    WHERE
      id = ?
  `;

  await conn.execute(sql, [
    address,
    province,
    city,
    kecamatan,
    kodePos,
    id,
  ]);

  const [rows] = await conn.execute(getUserAddress, [id]);

  userAddress = rows[0 as keyof typeof rows];

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
    if (await comparePassword(password, user.password)) {
      const hashedPassword = await encryptPassword(newPassword);

      const sql = `
        UPDATE
          User
        SET
          password = ?
        WHERE
          id = ?
      `;

      const getUserInfoById = `
        SELECT
          *
        FROM
          User
        WHERE
          id = ?
      `;

      await conn.execute(sql, [hashedPassword, id]);

      const [rowsB] = await conn.execute(getUserInfoById, [id]);

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
    if (await comparePassword(password, user.cred.password)) {
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

router.post("/add", upload.none(), async (req: Request, res: Response) => {
  const { fullName, email, password, phone, isSeller } = req.body;

  const user = await addUser(
    fullName as string,
    email as string,
    password as string,
    phone as string,
    isSeller === "true" ? true : false,
  );

  res.status(201).json(user);
});

router.post("/add-address", upload.none(), async (req: Request, res: Response) => {
  const { id, address, province, city, kecamatan, kodePos } = req.body;

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

router.post("/update", upload.none(), async (req: Request, res: Response) => {
  const authHeader = req.headers.authorization as string;
  const { id, fullName, email, phone, isSeller } = req.body;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

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

router.post("/update-profile-pic", upload.single("profilePic"), async (req: Request, res: Response) => {
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  const { id } = req.body;
  const profilePic = req.file;

  if (verifiedToken.id !== id) {
    res.status(403).json({
      code: "UNAUTHORIZED_ERROR",
      message: "Unauthorized",
    });
    return;
  }

  const user = await updateUserProfilePic(
    id as string,
    profilePic as Express.Multer.File,
  );

  res.status(201).json(user);
});

router.post("/update-address", upload.none(), async (req: Request, res: Response) => {
  const { id, address, province, city, kecamatan, kodePos } = req.body;
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

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

router.post("/update-pass", upload.none(), async (req: Request, res: Response) => {
  const { id, password, newPassword } = req.body;
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }


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

router.post("/authenticate", upload.none(), async (req: Request, res: Response) => {
  const { email, password } = req.body;

  const user = await login(email as string, password as string);

  res.status(201).json(user);
});

router.post("/verify-token", upload.none(), async (req: Request, res: Response) => {
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  res.status(201).json(verifiedToken);
});

export default router;
