package com.demo.rest;

import com.demo.model.Pet;
import com.demo.model.PetType;
import com.demo.repository.PetRepository;
import com.demo.rest.dto.PetDto;
import com.demo.rest.dto.PetTypeDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;

/**
 * HARVEST of PetRestController GET surface for G-4 request set.
 *
 * §17.2: task M3-T031-REST · brief B-OWNER-PET-1 · locus
 * /projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/rest/PetRestController.java:55-78
 *
 * Referent class mapping is "api/pets" (no leading slash) → effective /api/pets.
 * Do not invent /api/owners/{id}/pets (spec contract drift vs referent).
 */
@Path("/api/pets")
@Produces(MediaType.APPLICATION_JSON)
public class PetResource {

    @Inject
    PetRepository pets;

    @Inject
    OwnerPetDtoMapper mapper;

    @GET
    @Path("/{petId}")
    public Response getPet(@PathParam("petId") int petId) {
        Pet pet = pets.findById(petId);
        if (pet == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.toPetDto(pet)).build();
    }

    @GET
    public Response getPets() {
        Collection<Pet> found = pets.findAll();
        if (found == null || found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<PetDto> body = mapper.toPetDtoCollection(found);
        return Response.ok(body).build();
    }

    @GET
    @Path("/pettypes")
    public Response getPetTypes() {
        List<PetType> types = pets.findPetTypes();
        if (types == null || types.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<PetTypeDto> body = mapper.toPetTypeDtoCollection(types);
        return Response.ok(body).build();
    }
}
