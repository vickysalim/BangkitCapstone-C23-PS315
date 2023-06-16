import connection from "../config/db";
import jwt, { JwtPayload } from "jsonwebtoken";

export async function emailExists(email: string) {
  const conn = await connection();
  let user = null;

  const sql = `
    SELECT
      email
    FROM
      User
    WHERE
      email = ?
  `;

  const rows = await conn.execute(sql, [email]);
  await conn.end();

  user = rows;

  if (user.hasOwnProperty("user")) return true;

  return false;
}

export async function phoneExists(phone: string) {
  const conn = await connection();
  let user = null;

  const sql = `
    SELECT
      phone
    FROM
      User
    WHERE
      phone = ?
  `;

  const rows = await conn.execute(sql, [phone]);
  await conn.end();

  user = rows;

  if (user.hasOwnProperty("user")) return true;

  return false;
}

export async function verifyToken(token: string) {
  const conn = await connection();
  const error: {
    code: string | null;
    id: string | null;
    message: string | null;
  } = {
    code: null,
    id: null,
    message: null,
  };

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET_KEY as string) as JwtPayload;

    const sql = `
      SELECT
        id
      FROM
        User
      WHERE
        id = ?
    `;

    const rows: any = await conn.execute(sql, [decoded.id]);
    await conn.end();

    if (!rows.length) {
      throw new Error();
    }

    return {
      code: "TOKEN_VERIFIED",
      id: decoded.id,
      message: "Token verified",
    };
  } catch (err) {
    error.code = "INVALID_TOKEN_ERROR";
    error.id = null;
    error.message = "Invalid token";

    return error;
  }
}