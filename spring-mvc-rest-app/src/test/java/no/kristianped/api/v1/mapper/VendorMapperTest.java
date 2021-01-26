package no.kristianped.api.v1.mapper;

import no.kristianped.api.mapper.VendorMapper;
import no.kristianped.domain.Vendor;
import no.kristianped.model.VendorDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VendorMapperTest {

    static final String NAME = "Joe Biden";

    VendorMapper mapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTO() {
        // given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        // when
        VendorDTO vendorDTO = mapper.vendorToVendorDTO(vendor);

        // when
        assertNotNull(vendorDTO);
        assertEquals(vendor.getName(), vendorDTO.getName());

    }

    @Test
    void vendorDTOToVendor() {
        // given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        // when
        Vendor vendor = mapper.vendorDTOToVendor(vendorDTO);

        // then
        assertNotNull(vendor);
        assertEquals(vendor.getName(), vendorDTO.getName());
    }
}