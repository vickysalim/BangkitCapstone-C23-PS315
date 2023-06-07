---
title: "Get User Information"
description: "GET them up"
---

### Get all users

URL: `/api/v1/user/get/`

**Input Details:**

```yaml
Method: GET
Authenticated: False
```

**Output:**

```json
{
  "users": [
    {
      "id": "--- uuid ---",
      "fullName": "John Doe",
      "email": "johndoe@example.com",
      "password": "--- bcrypt salted password ---",
      "phone": "1234567890",
      "isSeller": 0,
      "profilePicUrl": "--- GCS URL to the uploaded profile pic ---",
      "address": "123 Main Street",
      "province": "California",
      "city": "Los Angeles",
      "kecamatan": "Downtown",
      "kodePos": "90001"
    },
    [... more users]
  ]
}
```

### Get user by ID

URL: `api/v1/user/get/:id`

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
  "user": {
    "id": "--- uuid ---",
    "fullName": "John Doe",
    "email": "johndoe@example.com",
    "password": "--- bcrypt salted password ---",
    "phone": "1234567890",
    "isSeller": 0,
    "profilePicUrl": "--- GCS URL to the uploaded profile pic ---",
    "address": "123 Main Street",
    "province": "California",
    "city": "Los Angeles",
    "kecamatan": "Downtown",
    "kodePos": "90001"
  }
}
```

### Get user by email

URL: `api/v1/user/get/email/:email`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :email
```

**Output:**

```json
{
  "user": {
    "id": "--- uuid ---",
    "fullName": "John Doe",
    "email": "johndoe@example.com",
    "password": "--- bcrypt salted password ---",
    "phone": "1234567890",
    "isSeller": 0,
    "profilePicUrl": "--- GCS URL to the uploaded profile pic ---",
    "address": "123 Main Street",
    "province": "California",
    "city": "Los Angeles",
    "kecamatan": "Downtown",
    "kodePos": "90001"
  }
}
```

### Get user by phone number

URL: `api/v1/user/get/phone/:phone`

**Input Details:**

```yaml
Method: GET
Authenticated: False

Params:
- :phone
```

**Output:**

```json
{
  "user": {
    "id": "--- uuid ---",
    "fullName": "John Doe",
    "email": "johndoe@example.com",
    "password": "--- bcrypt salted password ---",
    "phone": "1234567890",
    "isSeller": 0,
    "profilePicUrl": "--- GCS URL to the uploaded profile pic ---",
    "address": "123 Main Street",
    "province": "California",
    "city": "Los Angeles",
    "kecamatan": "Downtown",
    "kodePos": "90001"
  }
}
```
