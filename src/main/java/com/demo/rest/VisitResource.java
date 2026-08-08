package com.demo.rest;

import com.demo.model.Visit;
import com.demo.repository.VisitRepository;
import com.demo.rest.dto.VisitDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * HARVEST of VisitRestController GET surface for slice-two (#9).
 *
 * §17.2: locus legacy-at-3 .../rest/VisitRestController.java:55-75
 * Paths/status from referent (empty collection → 404). Mutators deferred.
 */
@Path("/api/visits")
@Produces(MediaType.APPLICATION_JSON)
public class VisitResource {

    @Inject
    VisitRepository visits;

    @GET
    public Response getAllVisits() {
        Collection<Visit> found = visits.findAll();
        if (found == null || found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(toDtoCollection(found)).build();
    }

    @GET
    @Path("/{visitId}")
    public Response getVisit(@PathParam("visitId") int visitId) {
        Visit visit = visits.findById(visitId);
        if (visit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(toDto(visit)).build();
    }

    private static VisitDto toDto(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.id = visit.getId();
        dto.date = visit.getDate() == null ? null : visit.getDate().toString();
        dto.description = visit.getDescription();
        return dto;
    }

    private static List<VisitDto> toDtoCollection(Collection<Visit> in) {
        List<VisitDto> out = new ArrayList<>();
        in.stream()
            .sorted(Comparator.comparing(v -> v.getId() == null ? Integer.MAX_VALUE : v.getId()))
            .forEach(v -> out.add(toDto(v)));
        return out;
    }
}
