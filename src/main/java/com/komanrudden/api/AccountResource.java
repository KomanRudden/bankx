package com.komanrudden.api;

import com.komanrudden.api.common.HttpStatus;
import com.komanrudden.model.entities.AccountEntity;
import com.komanrudden.model.repositories.AccountRepository;
import com.komanrudden.service.AccountService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Accounts", description = "Operations related to bank accounts")
public class AccountResource extends AbstractResource<AccountEntity, AccountRepository> {

    public AccountResource(AccountService accountService) {
        service = accountService;
    }

    @GET
    @Operation(summary = "Get all accounts", description = "Returns a list of all accounts")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the list of all accounts",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AccountEntity.class)))
    @APIResponse(responseCode = HttpStatus.INTERNAL_SERVER_ERROR, description = "Internal server error")
    public Response getAllAccounts() {
        return getAll();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get an account by ID", description = "Returns a single account by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the account",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AccountEntity.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Account not found")
    public Response getAccount(@Parameter(description = "ID of the account to be retrieved", required = true) @PathParam("id") Long id) {
        return get(id);
    }

    @POST
    @Operation(summary = "Create a new account", description = "Creates a new account and returns the created account")
    @APIResponse(responseCode = HttpStatus.CREATED,
            description = "Successfully created the account",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AccountEntity.class)))
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response createAccount(@Parameter(description = "Account object to be created", required = true) @Valid AccountEntity account) {
        return create(account);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an account", description = "Updates an existing account by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully updated the account",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = AccountEntity.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Account not found")
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response updateAccount(@Parameter(description = "ID of the account to be updated", required = true) @PathParam("id") Long id,
                                  @Parameter(description = "Updated account object", required = true) @Valid AccountEntity account) {
        return update(id, account);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an account", description = "Deletes an existing account by ID")
    @APIResponse(responseCode = HttpStatus.NO_CONTENT, description = "Successfully deleted the account")
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Account not found")
    public Response deleteAccount(@Parameter(description = "ID of the account to be deleted", required = true) @PathParam("id") Long id) {
        return delete(id);
    }
}