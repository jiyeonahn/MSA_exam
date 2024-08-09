package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private List<Long> orderItemIds;
    private String name;

    public static Order toEntity(OrderRequestDto orderRequestDto){
        return Order.builder()
                .name(orderRequestDto.getName())
                .product_ids(new ArrayList<>())
                .build();
    }
}
