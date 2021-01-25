package no.kristianped.service;

import no.kristianped.api.v1.mapper.VendorMapper;
import no.kristianped.api.v1.model.VendorDTO;
import no.kristianped.controller.v1.VendorController;
import no.kristianped.domain.Vendor;
import no.kristianped.repositories.VendorRepository;
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
class VendorServiceTest {

    static final Long ID = 2L;
    static final String NAME = "Fresh Fruits Ltd";
    static final String BASE_URL = VendorController.BASE_URL + "/";

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    void getAllVendors() {
        // given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findAll()).thenReturn(List.of(vendor));

        // when
        List<VendorDTO> vendors = vendorService.getAllVendors();

        // then
        assertEquals(1, vendors.size());
        assertEquals(BASE_URL + ID, vendors.get(0).getVendorUrl());
    }

    @Test
    void getVendorById() {
        // given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        // when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        // then
        assertEquals(NAME, vendorDTO.getName());
        assertEquals(BASE_URL + ID, vendorDTO.getVendorUrl());
    }

    @Test
    void createNewVendor() {
        // given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        // when
        VendorDTO vendorDTO = vendorService.createNewVendor(new VendorDTO());

        // then
        assertNotNull(vendorDTO);
        assertEquals(vendorDTO.getName(), NAME);
        assertEquals(vendorDTO.getVendorUrl(), BASE_URL + ID);
    }

    @Test
    void updateCustomer() {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(ID);
        savedVendor.setName(NAME);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        // when
        VendorDTO savedDto = vendorService.updateVendor(ID, vendorDTO);

        // then
        assertNotNull(vendorDTO);
        assertEquals(savedDto.getName(), NAME);
        assertEquals(savedDto.getVendorUrl(), BASE_URL + ID);
    }

    @Test
    void deleteVendorById() {
        vendorRepository.deleteById(ID);
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}