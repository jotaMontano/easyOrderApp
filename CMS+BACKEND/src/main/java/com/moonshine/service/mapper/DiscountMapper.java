package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.DiscountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Discount and its DTO DiscountDTO.
 */
@Mapper(componentModel = "spring", uses = {ListOfValueMapper.class, ClientMapper.class})
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {

    @Mapping(source = "client.id", target = "clientId")
    DiscountDTO toDto(Discount discount);

    @Mapping(target = "products", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Discount toEntity(DiscountDTO discountDTO);

    default Discount fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discount discount = new Discount();
        discount.setId(id);
        return discount;
    }
}
