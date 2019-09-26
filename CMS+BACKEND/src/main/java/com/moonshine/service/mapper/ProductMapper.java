package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {ExtraMapper.class, DiscountMapper.class, CategoryMapper.class, ClientMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "categories.id", target = "categoriesId")
    @Mapping(source = "categories.name", target = "categoriesName")
    @Mapping(source = "client.id", target = "clientId")
    ProductDTO toDto(Product product);

    @Mapping(target = "productByOrders", ignore = true)
    @Mapping(target = "tops", ignore = true)
    @Mapping(source = "categoriesId", target = "categories")
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
