package com.komanrudden.api;

import com.komanrudden.api.common.HttpStatus;
import com.komanrudden.model.data.Customer;
import com.komanrudden.model.entities.CustomerEntity;
import com.komanrudden.model.repositories.CustomerRepository;
import com.komanrudden.service.CustomerService;
import io.quarkus.security.Authenticated;
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

/**
 * Resource class for handling customer-related operations.
 * <p>
 * This class provides endpoints for CRUD operations on customers, including listing all customers,
 * retrieving a single customer by ID, creating a new customer, updating an existing customer, and deleting a customer.
 * Access to these operations is secured and requires authentication using oidc and keycloak.
 * </p>
 */
@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
@Tag(name = "Customers", description = "Operations related to customers")
public class CustomerResource extends AbstractResource<CustomerEntity, CustomerRepository> {

    public CustomerResource(CustomerService customerService) {
        service = customerService;
    }

    /**
     * Retrieves a list of all customers.
     * <p>This method returns a list of all customers in the system.</p>
     *
     * @return a {@link Response} object containing the list of all customers or an error message in case of failure.
     */
    @GET
    @Operation(summary = "Get all customers", description = "Returns a list of all customers")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the list of all customers",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CustomerEntity.class)))
    @APIResponse(responseCode = HttpStatus.INTERNAL_SERVER_ERROR, description = "Internal server error")
    public Response getAllCustomers() {
        return getAll();
    }

    /**
     * Retrieves a customer by its ID.
     * <p>This method returns the details of a specific customer identified by its ID.</p>
     *
     * @param id the ID of the customer to retrieve.
     * @return a {@link Response} object containing the customer details or an error message if the customer is not found.
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Get a customer by ID", description = "Returns a single customer by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully retrieved the customer",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Customer.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Customer not found")
    public Response getCustomer(@Parameter(description = "ID of the customer to be retrieved", required = true) @PathParam("id") long id) {
        return get(id);
    }

    /**
     * Creates a new customer.
     * <p>This method allows for the creation of a new customer with the provided details.</p>
     *
     * @param customer the {@link Customer} object containing the details of the new customer.
     * @return a {@link Response} object indicating the result of the creation operation.
     */
    @POST
    @Operation(summary = "Create a new customer", description = "Creates a new customer and returns the created customer")
    @APIResponse(responseCode = HttpStatus.CREATED,
            description = "Successfully created the customer",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CustomerEntity.class)))
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response createCustomer(@Parameter(description = "Customer object to be created", required = true) @Valid Customer customer) {
        CustomerEntity entity = buildCustomerEntity(customer);
        return create(entity);
    }

    /**
     * Updates an existing customer.
     * <p>This method updates the details of an existing customer identified by its ID.</p>
     *
     * @param id       the ID of the customer to update.
     * @param customer the {@link Customer} object containing the updated details of the customer.
     * @return a {@link Response} object indicating the result of the update operation.
     */
    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a customer", description = "Updates an existing customer by ID")
    @APIResponse(responseCode = HttpStatus.OK,
            description = "Successfully updated the customer",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = CustomerEntity.class)))
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Customer not found")
    @APIResponse(responseCode = HttpStatus.BAD_REQUEST, description = "Invalid input")
    public Response updateCustomer(@Parameter(description = "ID of the customer to be updated", required = true) @PathParam("id") long id,
                                   @Parameter(description = "Updated customer object", required = true) @Valid Customer customer) {
        CustomerEntity customerEntity = buildCustomerEntity(customer);
        return update(id, customerEntity);
    }

    /**
     * Deletes a customer by its ID.
     * <p>This method deletes the customer identified by its ID from the system.</p>
     *
     * @param id the ID of the customer to delete.
     * @return a {@link Response} object indicating the result of the deletion operation.
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a customer", description = "Deletes an existing customer by ID")
    @APIResponse(responseCode = HttpStatus.NO_CONTENT, description = "Successfully deleted the customer")
    @APIResponse(responseCode = HttpStatus.NOT_FOUND, description = "Customer not found")
    public Response deleteCustomer(@Parameter(description = "ID of the customer to be deleted", required = true) @PathParam("id") long id) {
        return delete(id);
    }

    /**
     * Builds a {@link CustomerEntity} from a {@link Customer} DTO.
     * <p>This utility method converts a customer DTO into a customer entity for persistence.</p>
     *
     * @param customer the {@link Customer} DTO to convert.
     * @return a {@link CustomerEntity} representing the customer.
     */
    private static CustomerEntity buildCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}