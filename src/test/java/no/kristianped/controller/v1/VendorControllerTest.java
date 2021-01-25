package no.kristianped.controller.v1;

import no.kristianped.api.v1.model.VendorDTO;
import no.kristianped.controller.RestResponseEntityExceptionHandler;
import no.kristianped.exceptions.ResourceNotFoundException;
import no.kristianped.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class VendorControllerTest {

    static final String NAME1 = "Vendor 1";
    static final String NAME2 = "Vendor 2";
    static final String ID1 = "1";
    static final String ID2 = "2";
    static final String BASE_URL = VendorController.BASE_URL + "/";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME1);
        vendor1.setVendorUrl(BASE_URL + ID1);

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setName(NAME2);
        vendor2.setVendorUrl(BASE_URL + ID2);

        // when
        when(vendorService.getAllVendors()).thenReturn(List.of(vendor1, vendor2));

        // then
        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getVendorById() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME1);
        vendor1.setVendorUrl(BASE_URL + ID1);

        // when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        // then
        mockMvc.perform(get(BASE_URL + ID1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME1)));
    }

    @Test
    void createNewVendor() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME1);
        returnDTO.setVendorUrl(BASE_URL + ID1);

        // when
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnDTO);

        // then
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendor1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(BASE_URL + ID1)));
    }

    @Test
    void updateVendor() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME1);
        returnDTO.setVendorUrl(BASE_URL + ID1);

        // when
        when(vendorService.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // then
        mockMvc.perform(put(BASE_URL + ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(BASE_URL + ID1)));
    }

    @Test
    void patchVendor() throws Exception {
        // given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setName(NAME1);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME1);
        returnDTO.setVendorUrl(BASE_URL + ID1);

        // whem
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);

        // then
        mockMvc.perform(patch(BASE_URL + ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(AbstractRestControllerTest.asJsonString(vendor1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(BASE_URL + ID1)));
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService, times(1)).deleteVendorById(anyLong());
    }

    @Test
    void getVendorNotFound() throws Exception {
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL + ID1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}