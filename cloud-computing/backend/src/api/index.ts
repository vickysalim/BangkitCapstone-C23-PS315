import express from "express";

import MessageResponse from "../interfaces/MessageResponse";
import userRouter from "./user/index";
import productRouter from "./product/index";
import locationRouter from "./location/index";
import cartRouter from "./cart/index";
import orderHistoryRouter from "./history/index";
import orderItemRouter from "./order/index";

const router = express.Router();

router.get<{}, MessageResponse>("/", (req, res) => {
  res.json({
    message: "API - 👋🌎🌍🌏",
  });
});

router.use("/user", userRouter);
router.use("/product", productRouter);
router.use("/location", locationRouter);
router.use("/cart", cartRouter);
router.use("/order", orderItemRouter);
router.use("/history", orderHistoryRouter);
router.use("/order/item", orderItemRouter);

export default router;
