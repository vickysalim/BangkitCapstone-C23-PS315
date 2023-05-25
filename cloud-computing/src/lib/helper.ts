import conn from "server/config/db";

export function emailExists(email: string) {
  let user = null;

  const sql = `
    SELECT
      email
    FROM
      User
    WHERE
      email = ?
  `;

  conn.execute(sql, [email], (err, result) => {
    if (err) throw err;
    user = result;
  });

  if (user) return true;

  return false;
}

export function phoneExists(phone: string) {
  let user = null;

  const sql = `
    SELECT
      phone
    FROM
      User
    WHERE
      phone = ?
  `;

  conn.execute(sql, [phone], (err, result) => {
    if (err) throw err;
    user = result;
  });

  if (user) return true;

  return false;
}

export function idExists(id: string) {
  let user = null;

  const sql = `
    SELECT
      id
    FROM
      User
    WHERE
      id = ?
  `;

  conn.execute(sql, [id], (err, result) => {
    if (err) throw err;
    user = result;
  });

  if (user) return true;

  return false;
}
