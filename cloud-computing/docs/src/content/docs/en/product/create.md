---
title: "Create Product"
description: "POST them up"
---

### Create a product

URL: `/api/v1/product/post/`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data
Authenticated: True

Input Fields:
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
  "name": "Brokoli",
  "sellerName": "Irvan Malik A",
  "type": "vegetable",
  "price": 50000,
  "isAvailable": 1, // 1 for true, 0 for false
  "description": "Ini deskripsi brokoli banh",
  "productPicUrls": [
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-0.png",
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-1.png",
    "https://storage.googleapis.com/sifresh-bucket-one/uploads/5481585b-074c-4d8b-92a4-926f357ac387/e54123ce-08e5-412f-9c7b-165a577017f7-2.png"
  ],
  "publishedAt": "2023-06-07T17:00:00.000Z"
}
```
