package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto implements Serializable {
    private Long orderId;
    private List<Long> product_ids = new ArrayList<>();

    public static OrderResponseDto fromEntity(Order order){
        return OrderResponseDto.builder()
                .orderId(order.getOrder_id())
                .product_ids(order.getProduct_ids().stream()
                        .map(OrderProduct::getProduct_id)
                        .collect(Collectors.toList()))
                .build();
    }

}
