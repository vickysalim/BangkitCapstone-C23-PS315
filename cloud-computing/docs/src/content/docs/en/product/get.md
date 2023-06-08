---
title: "Get Product Information"
description: "GET them up"
---

### Get all products

URL: `/api/v1/product/get/`

**Input Details:**

```yaml
Method: GET
Authenticated: False
```

**Output:**

```json
[
  {
    "id": "9ee95895-4f91-4441-aa16-515ea9828875",
    "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
    "name": "Brokoli",
    "sellerName": "Irvan Malik A",
    "type": "vegetable",
    "price": 50000,
    "isAvailable": 1,
    "description": "Ini deskripsi brokoli banh",
    "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-2.png\"]",
    "publishedAt": "2023-06-07T17:00:00.000Z"
  },
  {
    "id": "e54123ce-08e5-412f-9c7b-165a577017f7",
    "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
    "name": "Pepaya",
    "sellerName": "Irvan Malik A",
    "type": "fruit",
    "price": 100000,
    "isAvailable": 0,
    "description": "Ini deskripsi pepaya ngab",
    "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-2.png\"]",
    "publishedAt": "2023-06-07T17:00:00.000Z"
  }
]
```

### Get product by ID

URL: `/api/v1/product/get/:id`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :id
```

**Output:**

```json
{
  "id": "e54123ce-08e5-412f-9c7b-165a577017f7",
  "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
  "name": "Pepaya",
  "sellerName": "Irvan Malik A",
  "type": "fruit",
  "price": 100000,
  "isAvailable": 0,
  "description": "Ini deskripsi pepaya ngab",
  "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-2.png\"]",
  "publishedAt": "2023-06-07T17:00:00.000Z"
}
```

### Get all products by the seller ID

URL: `/api/v1/product/get/seller/:sellerId`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :sellerId
```

**Output:**

```json
[
  {
    "id": "9ee95895-4f91-4441-aa16-515ea9828875",
    "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
    "name": "Brokoli",
    "sellerName": "Irvan Malik A",
    "type": "vegetable",
    "price": 50000,
    "isAvailable": 1,
    "description": "Ini deskripsi brokoli banh",
    "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-2.png\"]",
    "publishedAt": "2023-06-07T17:00:00.000Z"
  },
  {
    "id": "e54123ce-08e5-412f-9c7b-165a577017f7",
    "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
    "name": "Pepaya",
    "sellerName": "Irvan Malik A",
    "type": "fruit",
    "price": 100000,
    "isAvailable": 0,
    "description": "Ini deskripsi pepaya ngab",
    "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-2.png\"]",
    "publishedAt": "2023-06-07T17:00:00.000Z"
  }
]
```

### Get all products by product type

URL: `/api/v1/product/get/type/:type`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :type   ("fruit" or "vegetable")
```

**Output:**

```json
[
  {
    "id": "9ee95895-4f91-4441-aa16-515ea9828875",
    "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
    "name": "Brokoli",
    "sellerName": "Irvan Malik A",
    "type": "vegetable",
    "price": 50000,
    "isAvailable": 1,
    "description": "Ini deskripsi brokoli banh",
    "productPicUrls": "[\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-0.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-1.png\",\"https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/9ee95895-4f91-4441-aa16-515ea9828875-2.png\"]",
    "publishedAt": "2023-06-07T17:00:00.000Z"
  },
  [... more of same type product]
]
```
