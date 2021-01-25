package no.kristianped.bootstrap;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import no.kristianped.domain.Category;
import no.kristianped.domain.Customer;
import no.kristianped.domain.Vendor;
import no.kristianped.repositories.CategoryRepository;
import no.kristianped.repositories.CustomerRepository;
import no.kristianped.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    CategoryRepository categoryRepository;
    CustomerRepository customerRepository;
    VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        saveFruits();
        saveCustomers();
        saveVendors();
    }

    private void saveFruits() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.saveAll(List.of(fruits, dried, fresh, exotic, nuts));
    }

    private void saveCustomers() {
        Customer alice = new Customer();
        alice.setId(342L);
        alice.setFirstname("Alice");
        alice.setLastname("Eastman");

        Customer max = new Customer();
        max.setId(343L);
        max.setFirstname("Max");
        max.setLastname("Mustermann");

        Customer manni = new Customer();
        manni.setId(344L);
        manni.setFirstname("Manni");
        manni.setLastname("Meistermann");

        Customer klaus = new Customer();
        klaus.setId(345L);
        klaus.setFirstname("Klaus");
        klaus.setLastname("Müller");

        Customer klaus2 = new Customer();
        klaus2.setId(346L);
        klaus2.setFirstname("Klaus");
        klaus2.setLastname("Müller");

        Customer hans = new Customer();
        hans.setId(347L);
        hans.setFirstname("Hans");
        hans.setLastname("Müller");

        customerRepository.saveAll(List.of(alice, max, manni, klaus, klaus2, hans));
    }

    private void saveVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Vendor 2");

        vendorRepository.saveAll(List.of(vendor1, vendor2));
    }
}
