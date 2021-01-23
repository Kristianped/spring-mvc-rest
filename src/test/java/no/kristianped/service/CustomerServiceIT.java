package no.kristianped.service;

import no.kristianped.api.v1.mapper.CustomerMapper;
import no.kristianped.api.v1.model.CustomerDTO;
import no.kristianped.bootstrap.Bootstrap;
import no.kristianped.domain.Customer;
import no.kristianped.repositories.CategoryRepository;
import no.kristianped.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @BeforeEach
    void setup() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void patchCustomerFirstname() {
        // given
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        // when
        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedCustomer.getFirstname(), updatedName);
        assertNotEquals(updatedCustomer.getFirstname(), originalFirstname);
        assertEquals(updatedCustomer.getLastname(), originalLastname);
    }

    @Test
    void patchCustomerLastname() {
        // given
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstname = originalCustomer.getFirstname();
        String originalLastname = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        // when
        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertNotEquals(updatedCustomer.getFirstname(), updatedName);
        assertEquals(updatedCustomer.getFirstname(), originalFirstname);
        assertNotEquals(updatedCustomer.getLastname(), originalLastname);
    }

    Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Custmers found: " + customers.size());

        return customers.get(0).getId();
    }
}
