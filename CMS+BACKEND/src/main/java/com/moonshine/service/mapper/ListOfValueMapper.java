package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ListOfValueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ListOfValue and its DTO ListOfValueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ListOfValueMapper extends EntityMapper<ListOfValueDTO, ListOfValue> {


    @Mapping(target = "discounts", ignore = true)
    ListOfValue toEntity(ListOfValueDTO listOfValueDTO);

    default ListOfValue fromId(Long id) {
        if (id == null) {
            return null;
        }
        ListOfValue listOfValue = new ListOfValue();
        listOfValue.setId(id);
        return listOfValue;
    }
}
