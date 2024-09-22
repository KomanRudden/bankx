package com.komanrudden.api;

import com.komanrudden.model.data.Customer;
import com.komanrudden.model.entities.CustomerEntity;
import com.komanrudden.service.CustomerService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomerResourceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerResource customerResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("return all customers")
    void should_return_all_customers() {
        CustomerEntity customer1 = CustomerEntity.builder()
                .name("Test1 Name1")
                .email("example1@email.com")
                .phoneNumber("000000000")
                .build();
        CustomerEntity customer2 = CustomerEntity.builder()
                .name("Test2 Name2")
                .email("example2@email.com")
                .phoneNumber("111111111")
                .build();
        List<CustomerEntity> expectedCustomers = List.of(customer1, customer2);

        when(customerService.getAll()).thenReturn(expectedCustomers);

        Response response = customerResource.getAllCustomers();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Object responseEntity = response.getEntity();
        assertNotNull(responseEntity);
        if (responseEntity instanceof List<?> list) {
            // Further check if the list contains elements of type CustomerEntity
            boolean allMatchCustomerEntity = list.stream().allMatch(e -> e instanceof CustomerEntity);
            if (allMatchCustomerEntity) {
                assertEquals(expectedCustomers.size(), list.size());
                @SuppressWarnings("unchecked") // Types were checked above manually
                List<CustomerEntity> typedList = (List<CustomerEntity>) list;
                assertTrue(typedList.containsAll(expectedCustomers));
            } else {
                fail("List does not contain elements of type CustomerEntity");
            }
        } else {
            fail("Response entity is not of type List");
        }
    }

    @Test
    @DisplayName("return customer with id")
    void should_get_customer_with_valid_id() {
        CustomerEntity customer = CustomerEntity.builder()
                .id(1L)
                .name("Test1 Name1")
                .email("example1@email.com")
                .phoneNumber("000000000")
                .build();
        when(customerService.getById(1L)).thenReturn(customer);
        Response response = customerResource.getCustomer(1L);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        CustomerEntity customerEntity = (CustomerEntity) response.getEntity();
        assertNotNull(customerEntity);
        assertEquals(customer, customerEntity);
    }

    @Test
    @DisplayName("do not return customer with id")
    void should_not_find_customer_with_id() {
        when(customerService.getById(1L)).thenReturn(null);
        Response response = customerResource.getCustomer(1L);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    @DisplayName("do not return customer with id")
    void should_create_customer() {
        CustomerEntity customer = CustomerEntity.builder()
                .id(1L)
                .name("Test1 Name1")
                .email("example1@email.com")
                .phoneNumber("000000000")
                .build();
        Customer customerData = Customer.builder()
                .name("Test1 Name1")
                .email("example1@email.com")
                .phoneNumber("000000000")
                .build();
        when(customerService.create(any(CustomerEntity.class))).thenReturn(customer);
        try (Response response = customerResource.createCustomer(customerData)) {
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            CustomerEntity customerEntity = (CustomerEntity) response.getEntity();
            assertNotNull(customerEntity);
            assertEquals(customer, customerEntity);
        }
    }

//    @Test
//    void createCustomer_withInvalidData_returnsBadRequest() {
//        // Simulate validation failure or other cause of bad request
//        Response response = customerResource.createCustomer(new Customer("", "", ""));
//        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void updateCustomer_withValidIdAndData_returnsOk() {
//        when(customerService.update(anyLong(), any(CustomerEntity.class))).thenReturn(Response.ok().build());
//        Customer customer = new Customer("Updated Name", "update@example.com", "0987654321");
//        Response response = customerResource.updateCustomer(1L, customer);
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void updateCustomer_withInvalidId_returnsNotFound() {
//        when(customerService.update(anyLong(), any(CustomerEntity.class))).thenReturn(Response.status(Response.Status.NOT_FOUND).build());
//        Customer customer = new Customer("Name", "email@example.com", "1234567890");
//        Response response = customerResource.updateCustomer(999L, customer);
//        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void deleteCustomer_withValidId_returnsNoContent() {
//        when(customerService.delete(anyLong())).thenReturn(Response.noContent().build());
//        Response response = customerResource.deleteCustomer(1L);
//        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    void deleteCustomer_withInvalidId_returnsNotFound() {
//        when(customerService.delete(anyLong())).thenReturn(Response.status(Response.Status.NOT_FOUND).build());
//        Response response = customerResource.deleteCustomer(999L);
//        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
//    }
}