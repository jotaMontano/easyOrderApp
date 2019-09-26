package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ProductByOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductByOrder and its DTO ProductByOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderOkMapper.class, ProductMapper.class})
public interface ProductByOrderMapper extends EntityMapper<ProductByOrderDTO, ProductByOrder> {

    @Mapping(source = "orderOk.id", target = "orderOkId")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    ProductByOrderDTO toDto(ProductByOrder productByOrder);

    @Mapping(source = "orderOkId", target = "orderOk")
    @Mapping(target = "extraInLines", ignore = true)
    @Mapping(source = "productsId", target = "products")
    ProductByOrder toEntity(ProductByOrderDTO productByOrderDTO);

    default ProductByOrder fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductByOrder productByOrder = new ProductByOrder();
        productByOrder.setId(id);
        return productByOrder;
    }
}
