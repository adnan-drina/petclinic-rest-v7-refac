package com.demo.rest.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * HARVEST from referent GET /api/pets/{id} body shape (plan #5 probe).
 * Fields: name, birthDate, typeId, id, type, visits — no owner (cycle closed by mapper).
 */
public class PetDto {
    public String name;
    public String birthDate;
    public Integer typeId;
    public Integer id;
    public PetTypeDto type;
    public List<VisitDto> visits = new ArrayList<>();
}
