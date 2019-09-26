package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.OrderHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderHistory and its DTO OrderHistoryDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, OrderOkMapper.class})
public interface OrderHistoryMapper extends EntityMapper<OrderHistoryDTO, OrderHistory> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "order.id", target = "orderId")
    OrderHistoryDTO toDto(OrderHistory orderHistory);

    @Mapping(source = "clientId", target = "client")
    @Mapping(source = "orderId", target = "order")
    OrderHistory toEntity(OrderHistoryDTO orderHistoryDTO);

    default OrderHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(id);
        return orderHistory;
    }
}
