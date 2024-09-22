package com.komanrudden.api;

import com.komanrudden.api.common.HttpStatus;
import com.komanrudden.model.entities.Bank;
import com.komanrudden.model.repositories.BankRepository;
import com.komanrudden.service.AbstractService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Resource class for handling bank-related operations.
 * <p>
 * This class provides endpoints for CRUD operations on banks, including listing all banks,
 * retrieving a single bank by ID, creating a new bank, updating an existing bank, and deleting a bank.
 * Access to these operations is controlled through role-based authentication using token propagation.
 * </p>
 */
@Path("/api/v1/banks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Banks", description = "Operations related to banks")
public class BankResource extends AbstractResource<Bank, BankRepository> {

    @Inject
    JsonWebToken principal;

    public BankResource(AbstractService<Bank, BankRepository> service) {
        super.service = service;
    }

    /**
     * Retrieves a list of all banks.
     * <p>This method is accessible to users with the "user" role. It returns a list of all banks in the system.</p>
     *
     * @return a {@link Response} object containing the list of all banks or an error message in case of failure.
     */
    @GET
    @RolesAllowed("user")
    @Operation(summary = "Get all banks", description = "Returns a list of all banks")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the list of all banks",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Bank.class)))
    @APIResponse(responseCode = HttpStatus.INTERNAL_SERVER_ERROR, description = "Internal server error")
    public Response getAllBanks() {
        return getAll();
    }

    /**
     * Retrieves a bank by its ID.
     * <p>This method is accessible to users with the "user" role. It returns the details of a specific bank identified by its ID.</p>
     *
     * @param id the ID of the bank to retrieve.
     * @return a {@link Response} object containing the bank details or an error message if the bank is not found.
     */
    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    @Operation(summary = "Get a bank by ID", description = "Returns a single bank by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the bank",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Bank.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Bank not found")
    public Response getBank(@Parameter(description = "ID of the bank to be retrieved", required = true) @PathParam("id") Long id) {
        return get(id);
    }

    /**
     * Creates a new bank.
     * <p>This method is accessible to users with the "admin" role. It allows for the creation of a new bank with the provided details.</p>
     *
     * @param bank the {@link Bank} object containing the details of the new bank.
     * @return a {@link Response} object indicating the result of the creation operation.
     */
    @POST
    @RolesAllowed("admin")
    @Operation(summary = "Create a new bank", description = "Creates a new bank and returns the created bank")
    @APIResponse(responseCode = HttpStatus.CREATED,
            description = "Successfully created the bank",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Bank.class)))
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response createBank(@Parameter(description = "Bank object to be created", required = true) @Valid Bank bank) {
        return create(bank);
    }

    /**
     * Updates an existing bank.
     * <p>This method is accessible to users with the "admin" role. It updates the details of an existing bank identified by its ID.</p>
     *
     * @param id   the ID of the bank to update.
     * @param bank the {@link Bank} object containing the updated details of the bank.
     * @return a {@link Response} object indicating the result of the update operation.
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Update a bank", description = "Updates an existing bank by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully updated the bank",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Bank.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Bank not found")
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response updateBank(@Parameter(description = "ID of the bank to be updated", required = true) @PathParam("id") Long id,
                               @Parameter(description = "Updated bank object", required = true) @Valid Bank bank) {
        return update(id, bank);
    }

    /**
     * Deletes a bank by its ID.
     * <p>This method is accessible to users with the "admin" role. It deletes the bank identified by its ID from the system.</p>
     *
     * @param id the ID of the bank to delete.
     * @return a {@link Response} object indicating the result of the deletion operation.
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete a bank", description = "Deletes an existing bank by ID")
    @APIResponse(responseCode = HttpStatus.NO_CONTENT, description = "Successfully deleted the bank")
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Bank not found")
    public Response deleteBank(@Parameter(description = "ID of the bank to be deleted", required = true) @PathParam("id") Long id) {
        return super.delete(id);
    }
}

