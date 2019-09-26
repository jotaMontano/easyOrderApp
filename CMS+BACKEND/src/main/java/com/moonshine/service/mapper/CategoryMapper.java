package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "client.id", target = "clientId")
    CategoryDTO toDto(Category category);

    @Mapping(target = "products", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
