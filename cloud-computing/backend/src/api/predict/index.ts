import fruitPost from "./fruits/post";
import vegetablePost from "./vegetables/post";
import get from "./get";
import { Router } from "express";

const router = Router();

router.use("/fruits", fruitPost);
router.use("/vegetables", vegetablePost);
router.use("/get", get);

export default router;
