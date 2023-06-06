import request from "supertest";

import app from "../src/app";

describe("Root app path configuration", () => {
  describe("GET /", () => {
    it("Responds with a JSON message and 200 status code", (done) => {
      request(app)
        .get("/")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(
          200,
          {
            message: "Server OK",
          },
          done,
        );
    });
  });

  describe("GET /what-is-this-even", () => {
    it("Responds with a not found message and 404 status code", (done) => {
      request(app)
        .get("/what-is-this-even")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(404, done);
    });
  });
});

describe("User endpoints", () => {
  describe("GET /api/v1/user/get", () => {
    it("Responds with a JSON message and 200 status code", () => {
      request(app)
        .get("/api/v1/user/get")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(200);
    });
  });

  describe("GET /api/v1/user/get/:id", () => {
    it("Responds with a JSON message and 200 status code", () => {
      request(app)
        .get("/api/v1/user/get/45a318cd-0386-47ac-bfd8-0c2d2b882a90")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(200);
    });

    it("Responds with a JSON message and 404 status code if user not found", () => {
      request(app)
        .get("/api/v1/user/get/1")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(404, {
          message: "User not found",
        });
    });
  });

  describe("GET /api/v1/user/get/email/:email", () => {
    it("Responds with a JSON message and 200 status code", () => {
      request(app)
        .get("/api/v1/user/get/email/johndoe@example.com")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(200);
    });

    it("Responds with a JSON message and 404 status code if user not found", () => {
      request(app)
        .get("/api/v1/user/get/email/1")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(404, {
          message: "User not found",
        });
    });
  });

  describe("GET /api/v1/user/get/phone/:phone", () => {
    it("Responds with a JSON message and 200 status code", () => {
      request(app)
        .get("/api/v1/user/get/phone/1234567890")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(200);
    });

    it("Responds with a JSON message and 404 status code if user not found", () => {
      request(app)
        .get("/api/v1/user/get/phone/1")
        .set("Accept", "application/json")
        .expect("Content-Type", /json/)
        .expect(404, {
          message: "User not found",
        });
    });
  });
});
