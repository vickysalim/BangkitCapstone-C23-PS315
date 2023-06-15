import multer from "multer";
import MulterGoogleCloudStorage from "multer-cloud-storage";

const upload = multer({
  storage: new MulterGoogleCloudStorage({
    acl: "publicRead",
    destination: "uploads/",
    projectId: process.env.GOOGLE_CLOUD_PROJECT,
    filename: (req: { body: { id: any; }; }, file: { originalname: any; }, cb: (arg0: null, arg1: string) => void) => {
      const fileName = `${Date.now()}-${req.body.id}-${file.originalname}`;
      cb(null, fileName);
    },
    keyFilename: process.env.GOOGLE_APPLICATION_CREDENTIALS,
    bucket: process.env.GCP_BUCKET_NAME as string,
  }),
  dest: "uploads/",
  limits: {
    fileSize: 12 * 1024 * 1024, // no larger than 5mb
  },
});

export default upload;
