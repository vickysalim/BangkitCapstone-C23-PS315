import express from "express";

import MessageResponse from "../interfaces/MessageResponse";
import userRouter from "./user/index";
import locationRouter from "./location/index";

const router = express.Router();

router.get<{}, MessageResponse>("/", (req, res) => {
  res.json({
    message: "API - 👋🌎🌍🌏",
  });
});

router.use("/user", userRouter);
router.use("/location", locationRouter);

export default router;
