// PBKDF2 Password Encryption

import { pbkdf2 } from "crypto";

export async function encryptPassword(password: string) {
  return new Promise((resolve, reject) => {
    pbkdf2(
      password,
      process.env.PASSWORD_SALT as string,
      100000,
      64,
      "sha512",
      (err, derivedKey) => {
        if (err) reject(err);
        resolve(derivedKey.toString("hex"));
      },
    );
  });
}

export async function comparePassword(
  password: string,
  hashedPassword: string,
) {
  const hash = await encryptPassword(password);

  return hash === hashedPassword;
}
