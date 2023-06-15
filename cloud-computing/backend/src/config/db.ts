import mysql from "mariadb";

async function connection() {
  const conn = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    connectionLimit: 10,
    idleTimeout: 60000,
  });

  const getConnection = await conn.getConnection();

  return getConnection;
}

export default connection;
