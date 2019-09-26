package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.TopDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Top and its DTO TopDTO.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface TopMapper extends EntityMapper<TopDTO, Top> {

    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    TopDTO toDto(Top top);

    @Mapping(source = "productsId", target = "products")
    Top toEntity(TopDTO topDTO);

    default Top fromId(Long id) {
        if (id == null) {
            return null;
        }
        Top top = new Top();
        top.setId(id);
        return top;
    }
}
