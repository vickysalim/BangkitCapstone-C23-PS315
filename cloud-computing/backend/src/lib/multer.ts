import multer from "multer";

const upload = multer({
  storage: multer.memoryStorage(),
  dest: "uploads/",
  limits: {
    fileSize: 12 * 1024 * 1024, // no larger than 5mb
  },
});

export default upload;
