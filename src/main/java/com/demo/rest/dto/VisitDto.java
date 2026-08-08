package com.demo.rest.dto;

/**
 * Visit DTO for slice-two (#9) GET parity. date is ISO-8601 string from entity.
 * Clock residual documented on G-4 normalization list (LocalDate.now() ctor).
 */
public class VisitDto {
    public Integer id;
    public String date;
    public String description;
}
