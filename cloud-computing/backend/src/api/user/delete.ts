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

async function deleteUser(id: string) {
  const conn = await connection();
  let user = null;

  const sql = `
    DELETE FROM
      User
    WHERE
      id = ?
  `;

  const [rows] = await conn.execute(sql, [id]);

  user = rows;

  await conn.end();

  if (user) return user;

  return [];
}

router.delete("/:id", async (req: Request, res: Response) => {
  const { id } = req.params;

  const user = await deleteUser(id);

  res.status(200).json(user);
});

export default router;
