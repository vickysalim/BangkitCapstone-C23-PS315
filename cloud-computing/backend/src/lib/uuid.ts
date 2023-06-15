import { randomUUID } from "crypto";

export function uuidv4(): string {
  return randomUUID();
}