---
title: "Create User"
description: "POST them up"
---

### Creating the basic user data

URL: `/api/v1/user/post/add`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data
Authenticated: False

Input Fields:
- fullName
- email
- password
- phone       (Numbers only)
- isSeller    ("true" or "false" only)
- profilePic  (Only .jpg, .jpeg, and .png are accepted)
```

**Output:**

```json
{
  "id": "--- uuid ---",
  "fullName": "John Doe",
  "email": "johndoe@example.com",
  "password": "--- bcrypt salted password ---",
  "phone": "1234567890",
  "isSeller": 0,
  "profilePicUrl": "--- GCS URL to the uploaded profile pic ---"
}
```

### Adding addresses

URL: `/api/v1/user/post/add-address`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data, application/x-www-form-urlencoded
Authenticated: False

Input Fields:
- id          (The ID of the user you have just created)
- address
- province
- city
- kecamatan
- kodePos
```

**Output:**

```json
{
  "id": "--- uuid ---",
  "address": "123 Main Street",
  "province": "California",
  "city": "Los Angeles",
  "kecamatan": "Downtown",
  "kodePos": "90001"
}
```
