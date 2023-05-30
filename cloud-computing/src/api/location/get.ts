import express from "express";

const router = express.Router();

async function getProvinces() {
  const res = await fetch(
    "https://www.emsifa.com/api-wilayah-indonesia/api/provinces.json",
  );
  const provinces = await res.json();

  return provinces;
}

async function getCities(provinceId: string) {
  const res = await fetch(
    `https://www.emsifa.com/api-wilayah-indonesia/api/regencies/${provinceId}.json`,
  );
  const cities = await res.json();

  return cities;
}

async function getDistricts(cityId: string) {
  const res = await fetch(
    `https://www.emsifa.com/api-wilayah-indonesia/api/districts/${cityId}.json`,
  );
  const districts = await res.json();

  return districts;
}

router.get("/", async (req, res) => {
  const provinces = await getProvinces();

  res.json({
    provinces,
  });
});

router.get("/:provinceId", async (req, res) => {
  const { provinceId } = req.params;

  const cities = await getCities(provinceId as string);

  res.json({
    cities,
  });
});

router.get("/:provinceId/:cityId", async (req, res) => {
  const { cityId } = req.params;

  const districts = await getDistricts(cityId as string);

  res.json({
    districts,
  });
});

export default router;
