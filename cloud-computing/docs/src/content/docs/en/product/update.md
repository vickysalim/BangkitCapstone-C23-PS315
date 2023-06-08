---
title: "Update Product"
description: "POST them up"
---

### Update a product

URL: `/api/v1/product/post/update`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data, application/x-www-form-urlencoded
Authenticated: True

Input Fields:
- id              (the product id)
- sellerId        (get it from your session cred)
- name
- sellerName      (get it from your session cred)
- type            ("fruit" or "vegetable")
- price           (number)
- isAvailable     ("true" or "false")
- description
- productPics     (array of files, or just a file)
- publishedAt     (any kind of date as long as it adheres to the ISO standard would suffice)
```

**Output:**

```json
{
  "id": "e54123ce-08e5-412f-9c7b-165a577017f7",
  "sellerId": "5481585b-074c-4d8b-92a4-926f357ac387",
  "name": "Pepaya",
  "sellerName": "Irvan Malik A",
  "type": "buah",
  "price": 100000,
  "isAvailable": 0,
  "description": "Ini deskripsi pepaya ngab",
  "productPicUrls": [
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-0.png",
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-1.png",
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-2.png"
  ],
  "publishedAt": "2023-06-07T17:00:00.000Z"
}
```
