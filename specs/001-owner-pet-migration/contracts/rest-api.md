# REST API Contracts: Owner/Pet Migration

**Date**: August 8, 2026

## Owner Resource Contract

### GET /api/owners
**Purpose**: Retrieve all pet owners

**Response**:
```json
{
  "owners": [
    {
      "id": 1,
      "firstName": "George",
      "lastName": "Franklin",
      "address": "110 W. Liberty St.",
      "city": "Madison",
      "telephone": "6085551023",
      "pets": [
        {
          "id": 1,
          "name": "Max",
          "birthDate": "2012-09-04",
          "type": {"id": 1, "name": "cat"}
        }
      ]
    }
  ]
}
```

**Status Codes**: 200 OK

### GET /api/owners/{ownerId}
**Purpose**: Retrieve specific owner by ID

**Response**:
```json
{
  "id": 1,
  "firstName": "George",
  "lastName": "Franklin", 
  "address": "110 W. Liberty St.",
  "city": "Madison",
  "telephone": "6085551023",
  "pets": [
    {
      "id": 1,
      "name": "Max",
      "birthDate": "2012-09-04",
      "type": {"id": 1, "name": "cat"}
    }
  ]
}
```

**Status Codes**: 200 OK, 404 Not Found

### POST /api/owners
**Purpose**: Create new owner

**Request**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "address": "123 Main St",
  "city": "Springfield", 
  "telephone": "5551234567"
}
```

**Response**: 201 Created with created owner JSON

**Status Codes**: 201 Created, 400 Bad Request (validation errors)

### PUT /api/owners/{ownerId}
**Purpose**: Update existing owner

**Request**: Same as POST

**Response**: 200 OK with updated owner JSON

**Status Codes**: 200 OK, 400 Bad Request, 404 Not Found

### DELETE /api/owners/{ownerId}
**Purpose**: Delete owner

**Response**: 204 No Content

**Status Codes**: 204 No Content, 404 Not Found

## Pet Resource Contract

### GET /api/owners/{ownerId}/pets
**Purpose**: Retrieve all pets for specific owner

**Response**:
```json
{
  "pets": [
    {
      "id": 1,
      "name": "Max",
      "birthDate": "2012-09-04",
      "type": {"id": 1, "name": "cat"},
      "owner": {"id": 1}
    }
  ]
}
```

**Status Codes**: 200 OK, 404 Not Found

### GET /api/owners/{ownerId}/pets/{petId}
**Purpose**: Retrieve specific pet

**Response**:
```json
{
  "id": 1,
  "name": "Max",
  "birthDate": "2012-09-04",
  "type": {"id": 1, "name": "cat"},
  "owner": {"id": 1}
}
```

**Status Codes**: 200 OK, 404 Not Found

### POST /api/owners/{ownerId}/pets
**Purpose**: Create new pet for owner

**Request**:
```json
{
  "name": "Buddy",
  "birthDate": "2020-01-15",
  "type": {"id": 2, "name": "dog"}
}
```

**Response**: 201 Created with created pet JSON

**Status Codes**: 201 Created, 400 Bad Request, 404 Not Found

### PUT /api/owners/{ownerId}/pets/{petId}
**Purpose**: Update existing pet

**Request**: Same as POST

**Response**: 200 OK with updated pet JSON

**Status Codes**: 200 OK, 400 Bad Request, 404 Not Found

### DELETE /api/owners/{ownerId}/pets/{petId}
**Purpose**: Delete pet

**Response**: 204 No Content

**Status Codes**: 204 No Content, 404 Not Found

## Error Contract

All endpoints may return validation errors:

**400 Bad Request**:
```json
{
  "timestamp": "2026-08-08T10:30:00.000Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": {
    "firstName": "First name is required",
    "telephone": "Phone number must be 10 digits"
  }
}
```

**404 Not Found**:
```json
{
  "timestamp": "2026-08-08T10:30:00.000Z", 
  "status": 404,
  "error": "Not Found",
  "message": "Owner with id 999 not found"
}
```
