import getRouter from "./get";
import postRouter from "./post";
import deleteRouter from "./delete";
import { Router } from "express";

const router = Router();

router.use("/get", getRouter);
router.use("/post", postRouter);
router.use("/del", deleteRouter);

export default router;
