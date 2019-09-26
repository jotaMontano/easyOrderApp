package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.OrderOkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderOk and its DTO OrderOkDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface OrderOkMapper extends EntityMapper<OrderOkDTO, OrderOk> {

    @Mapping(source = "client.id", target = "clientId")
    OrderOkDTO toDto(OrderOk orderOk);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "orders", ignore = true)
    OrderOk toEntity(OrderOkDTO orderOkDTO);

    default OrderOk fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderOk orderOk = new OrderOk();
        orderOk.setId(id);
        return orderOk;
    }
}
