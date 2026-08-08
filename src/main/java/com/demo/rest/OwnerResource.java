package com.demo.rest;

import com.demo.model.Owner;
import com.demo.repository.OwnerRepository;
import com.demo.rest.dto.OwnerDto;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.List;

/**
 * HARVEST of OwnerRestController GET surface for G-4 both-modes request set.
 *
 * §17.2: task M3-T019-REST · brief B-OWNER-PET-1 · locus
 * /projects/.derived/legacy-at-3/src/main/java/org/springframework/samples/petclinic/rest/OwnerRestController.java:66-84
 *
 * Paths/status from referent (empty collection → 404). Visit mutators deferred.
 */
@Path("/api/owners")
@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @Inject
    OwnerRepository owners;

    @Inject
    OwnerPetDtoMapper mapper;

    @GET
    public Response getOwners() {
        Collection<Owner> found = owners.findAll();
        if (found == null || found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<OwnerDto> body = mapper.toOwnerDtoCollection(found);
        return Response.ok(body).build();
    }

    @GET
    @Path("/{ownerId}")
    public Response getOwner(@PathParam("ownerId") int ownerId) {
        Owner owner;
        try {
            owner = owners.findById(ownerId);
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (owner == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mapper.toOwnerDto(owner)).build();
    }
}
