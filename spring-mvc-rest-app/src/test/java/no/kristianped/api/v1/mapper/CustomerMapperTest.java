package no.kristianped.api.v1.mapper;

import no.kristianped.domain.Customer;
import no.kristianped.model.CustomerDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTO() {
        // given
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstname("Kristian");
        customer.setLastname("Pedersen");

        // when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // then
        assertNotNull(customerDTO);
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    void CustomerDTOToCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Kristian");
        customerDTO.setLastname("Pedersen");

        // when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);

        // then
        assertNotNull(customer);
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }
}