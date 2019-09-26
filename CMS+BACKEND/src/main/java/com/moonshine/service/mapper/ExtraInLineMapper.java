package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ExtraInLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExtraInLine and its DTO ExtraInLineDTO.
 */
@Mapper(componentModel = "spring", uses = {ExtraMapper.class, ProductByOrderMapper.class})
public interface ExtraInLineMapper extends EntityMapper<ExtraInLineDTO, ExtraInLine> {

    @Mapping(source = "extra.id", target = "extraId")
    @Mapping(source = "productByOrder.id", target = "productByOrderId")
    ExtraInLineDTO toDto(ExtraInLine extraInLine);

    @Mapping(source = "extraId", target = "extra")
    @Mapping(source = "productByOrderId", target = "productByOrder")
    ExtraInLine toEntity(ExtraInLineDTO extraInLineDTO);

    default ExtraInLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExtraInLine extraInLine = new ExtraInLine();
        extraInLine.setId(id);
        return extraInLine;
    }
}
