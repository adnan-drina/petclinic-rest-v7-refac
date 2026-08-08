# Data Model: Owner/Pet Migration

**Date**: August 8, 2026

## Entity Model

The data model follows the legacy PetClinic structure with Jakarta Persistence annotations for Quarkus compatibility.

### Owner

**Purpose**: Represents pet owners in the clinic system

**Fields**:
- `id`: Unique identifier (Long, primary key)
- `firstName`: Owner's first name (String, not null, max length 30)
- `lastName`: Owner's last name (String, not null, max length 30)  
- `address`: Street address (String, max length 255)
- `city`: City name (String, max length 80)
- `telephone`: Phone number (String, max length 20)
- `pets`: Collection of associated pets (OneToMany relationship)

**Validation Rules**:
- firstName and lastName are required
- telephone format validation (numeric, appropriate length)
- City and address are required fields

**State Transitions**: None - Owner is a straightforward entity with CRUD operations

### Pet

**Purpose**: Represents pets owned by clinic customers

**Fields**:
- `id`: Unique identifier (Long, primary key)
- `name`: Pet's name (String, not null, max length 30)
- `birthDate`: Pet's birth date (LocalDate)
- `type`: Pet type entity (ManyToOne relationship to PetType)
- `owner`: Owning owner (ManyToOne relationship to Owner, not null)

**Validation Rules**:
- name is required
- birthDate cannot be in the future
- owner association is required
- type association is required

**State Transitions**: None - Pet follows standard entity lifecycle

### PetType

**Purpose**: Reference data for valid pet categories

**Fields**:
- `id`: Unique identifier (Long, primary key)
- `name`: Type name (String, not null, max length 80)

**Validation Rules**: name is required and unique

**State Transitions**: Static reference data, created during initialization

## Repository Interfaces

### OwnerRepository
- `save(Owner owner)`: Create or update owner
- `findById(Long id)`: Retrieve owner by ID
- `findAll()`: Retrieve all owners
- `findByLastName(String lastName)`: Find owners by last name
- `delete(Owner owner)`: Remove owner

### PetRepository  
- `save(Pet pet)`: Create or update pet
- `findById(Long id)`: Retrieve pet by ID
- `findAll()`: Retrieve all pets
- `findByOwner(Owner owner)`: Find pets by owner
- `delete(Pet pet)`: Remove pet

### PetTypeRepository
- `findAll()`: Retrieve all pet types
- `findById(Long id)`: Retrieve pet type by ID

## Business Rules

1. **Owner-Pet Relationship**: Each pet must have exactly one owner
2. **Pet Type Validation**: Only valid pet types from PetType table are allowed
3. **Cascade Rules**: Deleting an owner should not cascade to pets (pets can be reassigned)
4. **Unique Constraints**: Owner names should be unique per clinic (no strict validation as real clinics may have duplicate names)
5. **Data Integrity**: All validation rules from legacy system must be preserved
