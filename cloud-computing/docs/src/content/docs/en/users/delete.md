---
title: "Delete User"
description: "POST them up"
---

### Delete a user by ID

> Deleting a user also deletes the address associated with the user

URL: `/api/v1/user/del/`

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

```plaintext
Yeah, there's no output. Just make use of the status code, alright?
```
