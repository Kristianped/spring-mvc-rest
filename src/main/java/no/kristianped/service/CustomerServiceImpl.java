package no.kristianped.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import no.kristianped.api.v1.mapper.CustomerMapper;
import no.kristianped.api.v1.model.CustomerDTO;
import no.kristianped.domain.Customer;
import no.kristianped.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerMapper customerMapper;
    CustomerRepository customerRepository;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
            customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());

            return customerDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        if (!customerOptional.isPresent())
            throw new RuntimeException("Could not find customer with ID: " + id);

        return customerMapper.customerToCustomerDTO(customerOptional.get());
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customer) {
        Customer mappedCustomer = customerMapper.customerDTOToCustomer(customer);
        return saveAndReturn(mappedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        Customer mappedCustomer = customerMapper.customerDTOToCustomer(customer);
        mappedCustomer.setId(id);
        return saveAndReturn(mappedCustomer);
    }

    private CustomerDTO saveAndReturn(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        returnDTO.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId());

        return returnDTO;
    }

    @Override
    public CustomerDTO patchCustomers(Long id, CustomerDTO customer) {
        return customerRepository.findById(id).map(customer1 -> {
            if (customer.getFirstname() != null)
                customer1.setFirstname(customer.getFirstname());

            if (customer.getLastname() != null)
                customer1.setLastname(customer.getLastname());

            return customerMapper.customerToCustomerDTO(customerRepository.save(customer1));
        }).orElseThrow(RuntimeException::new);
    }
}
