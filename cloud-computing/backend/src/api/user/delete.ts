import express, { Request, Response } from "express";
import formidable from "formidable";
import connection from "../../config/db";
import { verifyToken } from "../../lib/helper";
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

async function deleteUser(id: string) {
  const storage = new Storage().bucket(process.env.GCP_BUCKET_NAME as string);
  const conn = await connection();
  let user = null;

  const getUserInfoSql = `
    SELECT * FROM
      User
    WHERE
      id = ?
  `;

  const [userInfoRows] = await conn.execute(getUserInfoSql, [id]);

  const theUser: any = userInfoRows[0 as keyof typeof userInfoRows];

  const sql = `
    DELETE FROM
      User
    WHERE
      id = ?
  `;

  const alsoDeleteAddressSql = `
    DELETE FROM
      UserAddress
    WHERE
      id = ?
  `;

  await conn.execute(alsoDeleteAddressSql, [id]);
  const [rows] = await conn.execute(sql, [id]);

  const sanitizedProfilePicUrl = theUser.profilePicUrl.replace(
    `https://storage.googleapis.com/${process.env.GCP_BUCKET_NAME}/`,
    "",
  );

  storage.file(sanitizedProfilePicUrl).delete();

  user = rows;

  if (user) return user;

  return [];
}

router.post("/", async (req: Request, res: Response) => {
  const form = formidable({ multiples: true });
  const authHeader = req.headers.authorization as string;

  const token = authHeader.split(" ")[1];

  const verifiedToken = await verifyToken(token);

  if (verifiedToken.code === "INVALID_TOKEN_ERROR") {
    res.status(403).json(verifiedToken);
    return;
  }

  form.parse(req, async (err, fields) => {
    const { id } = fields;

    if (verifiedToken.id !== id) {
      res.status(403).json({
        code: "UNAUTHORIZED_ERROR",
        id: null,
        message: "Unauthorized",
      });
      return;
    }

    const user = await deleteUser(id as string);

    if (!user) {
      res.status(404).json({
        message: "User not found",
      });
    }

    res.status(200).json({
      user,
    });
  });
});

export default router;
