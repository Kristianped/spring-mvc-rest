package no.kristianped.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import no.kristianped.api.v1.mapper.VendorMapper;
import no.kristianped.controller.v1.VendorController;
import no.kristianped.domain.Vendor;
import no.kristianped.exceptions.ResourceNotFoundException;
import no.kristianped.model.VendorDTO;
import no.kristianped.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {

    static String DEFAULT_URL = VendorController.BASE_URL + "/";
    VendorRepository vendorRepository;
    VendorMapper vendorMapper;

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl(DEFAULT_URL + vendor.getId());

            return vendorDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendor -> {
            VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setVendorUrl(DEFAULT_URL + vendor.getId());

            return vendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor mappedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        return saveAndReturn(mappedVendor);
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        Vendor mappedVendor = vendorMapper.vendorDTOToVendor(vendorDTO);
        mappedVendor.setId(id);

        return saveAndReturn(mappedVendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null)
                vendor.setName(vendorDTO.getName());

            VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnDTO.setVendorUrl(DEFAULT_URL + id);

            return returnDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturn(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        returnDTO.setVendorUrl(DEFAULT_URL + savedVendor.getId());

        return returnDTO;
    }
}
