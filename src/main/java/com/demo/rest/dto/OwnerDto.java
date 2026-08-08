package com.demo.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * HARVEST from referent GET /api/owners body shape (plan #5 probe).
 * Collection endpoint returns OwnerDto[] (not wrapped) — match referent, not invented contract wrap.
 */
public class OwnerDto {
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String telephone;
    public Integer id;
    public List<PetDto> pets = new ArrayList<>();
}
