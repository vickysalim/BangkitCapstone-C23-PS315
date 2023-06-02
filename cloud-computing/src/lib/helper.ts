import connection from "../config/db";

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

  const [rows] = await conn.execute(sql, [email]);

  user = rows;

  if (user) return true;

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

  const [rows] = await conn.execute(sql, [phone]);

  user = rows;

  if (user) return true;

  return false;
}

