package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "user.id", target = "userId")
    ClientDTO toDto(Client client);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "extras", ignore = true)
    @Mapping(target = "discounts", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "orderHistories", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
