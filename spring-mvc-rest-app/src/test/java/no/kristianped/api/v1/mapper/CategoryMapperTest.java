package no.kristianped.api.v1.mapper;

import no.kristianped.api.mapper.CategoryMapper;
import no.kristianped.domain.Category;
import no.kristianped.model.CategoryDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTO() {
        // given
        Category category = new Category();
        category.setName("Joe Exotic");
        category.setId(1L);

        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // then
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());
    }
}