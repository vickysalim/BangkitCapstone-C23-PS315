---
title: "User Authentication"
description: "POST them up"
---

### Authenticating a user

URL: `/api/v1/user/post/authenticate`

**Input Details:**

```yaml
Method: POST
Content-Type: multipart/form-data, application/x-www-form-urlencoded
Authenticated: False

Input Fields:
- email
- password
```

**Output:**

```json
{
  "cred": {
    "id": "--- uuid ---",
    "fullName": "Irvan Malik A",
    "email": "irvanmalik69@gmail.com",
    "password": "--- password ---",
    "phone": "089628090166",
    "isSeller": 1,
    "profilePicUrl": "--- url ---"
  },
  "jwt": "--- jwt bearer string ---"
}
```

### Verifying token

URL: `/api/v1/user/post/verify-token`

**Input Details:**

```yaml
Method: POST
Authenticated: True

Input Fields: none
```

**Output:**

```json
{
  "code": "TOKEN_VERIFIED",
  "id": "--- user id ---",
  "message": "Token verified"
}
```
