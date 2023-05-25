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

function deleteUser(id: string) {
  let user = null;

  const sql = `
    DELETE FROM
      User
    WHERE
      id = ?
  `;

  conn.execute(sql, [id], (err, result) => {
    if (err) throw err;
    user = result;
  });

  if (user) return user;

  return [];
}

router.delete("/:id", (req, res) => {
  const id = req.params.id;

  const user = deleteUser(id);

  res.json(user);
});

export default router;