import fruitPost from "./fruits/post";
import vegetablePost from "./vegetables/post";
import { Router } from "express";

const router = Router();

router.use("/fruits", fruitPost);
router.use("/vegetables", vegetablePost);

export default router;
