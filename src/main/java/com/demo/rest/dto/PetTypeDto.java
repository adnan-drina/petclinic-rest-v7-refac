package com.demo.rest.dto;

/**
 * HARVEST DTO shape from legacy OwnerDto/PetDto JSON (referent GET bodies).
 * Locus: /projects/.derived/legacy-at-3/.../rest/OwnerRestController.java + observed G-4 body.
 */
public class PetTypeDto {
    public Integer id;
    public String name;

    public PetTypeDto() {}

    public PetTypeDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
