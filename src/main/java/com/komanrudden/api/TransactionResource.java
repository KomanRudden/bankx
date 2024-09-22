package com.komanrudden.api;

import com.komanrudden.api.common.HttpStatus;
import com.komanrudden.model.entities.Transaction;
import com.komanrudden.model.repositories.TransactionRepository;
import com.komanrudden.service.TransactionService;
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

@Path("/api/v1/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Transactions", description = "Operations related to transactions")
public class TransactionResource extends AbstractResource<Transaction, TransactionRepository> {

    public TransactionResource(TransactionService transactionService) {
        super.service = transactionService;
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a transaction by ID", description = "Returns a single transaction by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the transaction",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Transaction.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Transaction not found")
    public Response getTransaction(@Parameter(description = "ID of the transaction to be retrieved", required = true) @PathParam("id") Long id) {
        return get(id);
    }

    @POST
    @Operation(summary = "Create a new transaction", description = "Creates a new transaction and returns the created transaction")
    @APIResponse(responseCode = HttpStatus.CREATED,
            description = "Successfully created the transaction",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Transaction.class)))
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response createTransaction(@Parameter(description = "Transaction object to be created", required = true) @Valid Transaction transaction) {
        return create(transaction);
    }
}
