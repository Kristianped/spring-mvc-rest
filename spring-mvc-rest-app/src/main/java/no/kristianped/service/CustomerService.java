package no.kristianped.service;

import no.kristianped.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createNewCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(Long id, CustomerDTO customer);

    CustomerDTO patchCustomer(Long id, CustomerDTO customer);

    void deleteCustomerById(Long id);
}
