package no.kristianped.service;

import no.kristianped.api.mapper.CategoryMapper;
import no.kristianped.domain.Category;
import no.kristianped.model.CategoryDTO;
import no.kristianped.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    static final Long ID = 2L;
    static final String NAME = "Jimmy";

    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @BeforeEach
    void setup() {
        categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
    }

    @Test
    void getAllCategories() {
        // given
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // when
        List<CategoryDTO> list = categoryService.getAllCategories();

        // then
        assertEquals(categories.size(), list.size());
    }

    @Test
    void getCategoryByName() {
        // given
        Category category = new Category();
        category.setId(ID);
        category.setName(NAME);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        // when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

        // then
        assertEquals(ID, categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());
    }
}