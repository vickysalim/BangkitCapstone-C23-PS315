import getRouter from "./get";
import { Router } from "express";

const router = Router();

router.use("/get", getRouter);

export default router;
