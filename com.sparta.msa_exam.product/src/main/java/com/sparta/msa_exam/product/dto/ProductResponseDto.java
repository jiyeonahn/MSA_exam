package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long product_id;
    private String name;
    private int supply_price;

    public ProductResponseDto(Product product) {
        this.product_id = product.getProduct_id();
        this.name = product.getName();
        this.supply_price = product.getSupply_price();
    }
}
