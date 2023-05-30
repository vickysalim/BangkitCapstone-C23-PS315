import express, { Request, Response } from "express";
import conn from "../../config/db";

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

function deleteUser(id: string) {
  let user = null;

  const sql = `
    DELETE FROM
      User
    WHERE
      id = ?
  `;

  conn.execute(sql, [id], (err: any, result: any) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return [];
}

router.delete("/:id", (req: Request, res: Response) => {
  const { id } = req.params;

  const user = deleteUser(id);

  res.status(200).json(user);
});

export default router;
