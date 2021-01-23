package no.kristianped.controller;

import no.kristianped.api.v1.model.CategoryDTO;
import no.kristianped.service.CategoryService;
import org.hamcrest.Matchers;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories() throws Exception {
        CategoryDTO cat1 = new CategoryDTO();
        cat1.setId(1L);
        cat1.setName("Kristian");

        CategoryDTO cat2 = new CategoryDTO();
        cat2.setId(2L);
        cat2.setName("Horse");

        List<CategoryDTO> categories = List.of(cat1, cat2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", Matchers.hasSize(2)));

    }

    @Test
    void getCategoryByName() throws Exception {
        CategoryDTO cat1 = new CategoryDTO();
        cat1.setId(1L);
        cat1.setName("Kristian");

        when(categoryService.getCategoryByName(anyString())).thenReturn(cat1);

        mockMvc.perform(get("/api/v1/categories/Kristian")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Kristian")));
    }
}