---
title: "Update User"
description: "POST them up"
---

### Updating user data

URL: `/api/v1/user/post/update`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data, application/x-www-form-urlencoded
Authenticated: True

Input Fields:
- fullName
- email
- password
- phone       (Numbers only)
- isSeller    ("true" or "false" only)
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

### Updating user address

URL: `/api/v1/user/post/update-address`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data, application/x-www-form-urlencoded
Authenticated: True

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

### Updating user profile picture

URL: `/api/v1/user/post/update-profile-pic`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data
Authenticated: True

Input Fields:
- id          (The ID of the user you have just created)
- profilePic  File
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
