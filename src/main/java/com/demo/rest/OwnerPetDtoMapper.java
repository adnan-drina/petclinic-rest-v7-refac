package com.demo.rest;

import com.demo.model.Owner;
import com.demo.model.Pet;
import com.demo.model.PetType;
import com.demo.rest.dto.OwnerDto;
import com.demo.rest.dto.PetDto;
import com.demo.rest.dto.PetTypeDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Manual HARVEST of OwnerMapper/PetMapper JSON projection (MapStruct not yet on dest).
 * Brief B-OWNER-PET-1; Visit body deferred (#9) — emit empty visits[].
 */
@ApplicationScoped
public class OwnerPetDtoMapper {

    public OwnerDto toOwnerDto(Owner owner) {
        if (owner == null) {
            return null;
        }
        OwnerDto dto = new OwnerDto();
        dto.id = owner.getId();
        dto.firstName = owner.getFirstName();
        dto.lastName = owner.getLastName();
        dto.address = owner.getAddress();
        dto.city = owner.getCity();
        dto.telephone = owner.getTelephone();
        List<PetDto> pets = new ArrayList<>();
        if (owner.getPets() != null) {
            owner.getPets().stream()
                .sorted(Comparator.comparing(p -> p.getId() == null ? Integer.MAX_VALUE : p.getId()))
                .forEach(p -> pets.add(toPetDto(p)));
        }
        dto.pets = pets;
        return dto;
    }

    public List<OwnerDto> toOwnerDtoCollection(Collection<Owner> owners) {
        List<OwnerDto> out = new ArrayList<>();
        if (owners == null) {
            return out;
        }
        owners.stream()
            .sorted(Comparator.comparing(o -> o.getId() == null ? Integer.MAX_VALUE : o.getId()))
            .forEach(o -> out.add(toOwnerDto(o)));
        return out;
    }

    public PetDto toPetDto(Pet pet) {
        if (pet == null) {
            return null;
        }
        PetDto dto = new PetDto();
        dto.id = pet.getId();
        dto.name = pet.getName();
        dto.birthDate = pet.getBirthDate() == null ? null : pet.getBirthDate().toString();
        dto.typeId = null; // referent GET emits typeId:null alongside type{}
        dto.type = toPetTypeDto(pet.getType());
        // Visit deferred — empty list matches referent seeded pets without visits in body_prefix
        dto.visits = new ArrayList<>();
        return dto;
    }

    public List<PetDto> toPetDtoCollection(Collection<Pet> pets) {
        List<PetDto> out = new ArrayList<>();
        if (pets == null) {
            return out;
        }
        pets.stream()
            .sorted(Comparator.comparing(p -> p.getId() == null ? Integer.MAX_VALUE : p.getId()))
            .forEach(p -> out.add(toPetDto(p)));
        return out;
    }

    public PetTypeDto toPetTypeDto(PetType type) {
        if (type == null) {
            return null;
        }
        return new PetTypeDto(type.getId(), type.getName());
    }

    public List<PetTypeDto> toPetTypeDtoCollection(Collection<PetType> types) {
        List<PetTypeDto> out = new ArrayList<>();
        if (types == null) {
            return out;
        }
        types.stream()
            .sorted(Comparator.comparing(t -> t.getId() == null ? Integer.MAX_VALUE : t.getId()))
            .forEach(t -> out.add(toPetTypeDto(t)));
        return out;
    }
}
