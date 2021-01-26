package no.kristianped.service;

import no.kristianped.api.mapper.VendorMapper;
import no.kristianped.bootstrap.Bootstrap;
import no.kristianped.domain.Vendor;
import no.kristianped.model.VendorDTO;
import no.kristianped.repositories.CategoryRepository;
import no.kristianped.repositories.CustomerRepository;
import no.kristianped.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VendorServceIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setup() throws Exception {
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void patchVendorName() {
        // given
        String updatedName = "UpdatedName";
        Long id = getVendorIdValue();

        Vendor originalVendor = vendorRepository.getOne(id);
        assertNotNull(originalVendor);
        String originalName = originalVendor.getName();

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(updatedName);

        vendorService.patchVendor(id, vendorDTO);

        // when
        Vendor updatedVendor = vendorRepository.findById(id).get();

        // then
        assertNotNull(updatedVendor);
        assertEquals(updatedVendor.getName(), updatedName);
        assertNotEquals(updatedVendor.getName(), originalName);
    }

    Long getVendorIdValue() {
        List<Vendor> vendors = vendorRepository.findAll();
        System.out.println("Vendors found: " + vendors.size());

        return vendors.get(0).getId();
    }
}
