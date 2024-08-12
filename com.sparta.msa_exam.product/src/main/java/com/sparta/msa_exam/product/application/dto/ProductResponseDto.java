package com.sparta.msa_exam.product.application.dto;

import com.sparta.msa_exam.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long product_id;
    private String name;
    private int supply_price;

    public static ProductResponseDto fromEntity(Product product) {
        return ProductResponseDto.builder()
                .product_id(product.getProduct_id())
                .name(product.getName())
                .supply_price(product.getSupply_price())
                .build();
    }
}
