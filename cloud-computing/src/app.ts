import express from "express";
import morgan from "morgan";
import helmet from "helmet";
import cors from "cors";

import * as middlewares from "./middlewares";
import api from "./api/index";
import MessageResponse from "./interfaces/MessageResponse";

const app = express();

app.use(morgan("dev"));
app.use(helmet());
app.use(cors());
app.use(express.json());

app.use("/api/v1", api);

app.get<{}, MessageResponse>("/", (req, res) => {
  res.status(200).json({
    message: "Server OK",
  });
});

app.use(middlewares.notFound);
app.use(middlewares.errorHandler);

export default app;
