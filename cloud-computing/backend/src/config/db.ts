import mysql from "mariadb";

async function connection() {
  const getConnection = await mysql.createConnection({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    database: process.env.DB_NAME,
    socketPath: process.env.DB_SOCKET_PATH,
    socketTimeout: 100000,
  });

  return getConnection;
}

export default connection;
