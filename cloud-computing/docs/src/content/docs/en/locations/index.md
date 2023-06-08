---
title: "Get Location Information"
description: "GET them up"
---

> Disclaimer: SiFresh uses [Emsifa's Wilayah Indonesia API](https://github.com/emsifa/api-wilayah-indonesia) internally. So giving them a good mention doesn't hurt.

### Get Provinces

URL: `/api/v1/location/get/`

**Input Details:**

```yaml
Method: GET
Authenticated: False
```

**Output:**

```json
{
  "provinces": [
    {
      "id": "11",
      "name": "ACEH"
    },
    {
      "id": "12",
      "name": "SUMATERA UTARA"
    },
    {
      "id": "13",
      "name": "SUMATERA BARAT"
    },
    [... more provinces]
  ]
}
```

### Get Cities

URL: `/api/v1/location/get/:provinceId`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :provinceId
```

**Output:**

```json
{
  "cities": [
    {
      "id": "1101",
      "province_id": "11",
      "name": "KABUPATEN SIMEULUE"
    },
    {
      "id": "1102",
      "province_id": "11",
      "name": "KABUPATEN ACEH SINGKIL"
    },
    {
      "id": "1103",
      "province_id": "11",
      "name": "KABUPATEN ACEH SELATAN"
    },
    [... more cities]
  ]
}
```

### Get Districts (Kecamatan)

URL: `/api/v1/location/get/:provinceId/:cityId`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :provinceId
- :cityId
```

**Output:**

```json
{
  "districts": [
    {
      "id": "1103010",
      "regency_id": "1103",
      "name": "TRUMON"
    },
    {
      "id": "1103011",
      "regency_id": "1103",
      "name": "TRUMON TIMUR"
    },
    {
      "id": "1103012",
      "regency_id": "1103",
      "name": "TRUMON TENGAH"
    },
    [... more districts]
  ]
}
```
