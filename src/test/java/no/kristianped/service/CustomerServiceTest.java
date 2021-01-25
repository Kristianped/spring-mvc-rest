package no.kristianped.service;

import no.kristianped.api.v1.mapper.CustomerMapper;
import no.kristianped.api.v1.model.CustomerDTO;
import no.kristianped.controller.v1.CustomerController;
import no.kristianped.domain.Customer;
import no.kristianped.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    static final Long ID = 2L;
    static final String FIRSTNAME = "Jimmy";
    static final String LASTNAME = "Olsen";

    @Mock
    CustomerRepository customerRepository;

    CustomerServiceImpl customerService;

    static final String BASE_URL = CustomerController.BASE_URL + "/";

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // when
        List<CustomerDTO> customers = customerService.getAllCustomers();

        // then
        assertEquals(1, customers.size());
        assertEquals(BASE_URL + ID, customers.get(0).getCustomerUrl());
    }

    @Test
    void getCustomerById() {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        // then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }

    @Test
    void createNewCustomer() {
        // given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // when
        CustomerDTO customerDTO = customerService.createNewCustomer(new CustomerDTO());

        // then
        assertNotNull(customerDTO);
        assertEquals(customerDTO.getFirstname(), FIRSTNAME);
        assertEquals(customerDTO.getLastname(), LASTNAME);
        assertEquals(BASE_URL + ID, customerDTO.getCustomerUrl());
    }

    @Test
    void updateCustomer() {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(FIRSTNAME);
        savedCustomer.setLastname(LASTNAME);
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDto = customerService.updateCustomer(ID, customerDTO);

        // then
        assertNotNull(savedDto);
        assertEquals(savedDto.getFirstname(), FIRSTNAME);
        assertEquals(savedDto.getLastname(), LASTNAME);
        assertEquals(BASE_URL + ID, savedDto.getCustomerUrl());
    }

    @Test
    void deleteCustomerById() {

        customerRepository.deleteById(1L);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}