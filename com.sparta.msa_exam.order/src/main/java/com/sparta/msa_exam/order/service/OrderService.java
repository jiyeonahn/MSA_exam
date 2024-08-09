package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductRequestDto;
import com.sparta.msa_exam.order.client.ProductResponseDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        //해당 상품 조회
        // 예외처리 -> FeignErrorDecoder
        for(Long productId : requestDto.getOrderItemIds()){
            productClient.getProductById(productId);
        }

        Order order = OrderRequestDto.toEntity(requestDto);

        for (Long productId : requestDto.getOrderItemIds()) {
            OrderProduct orderProduct = OrderProduct.createOrderProduct(productId, order);
            order.addProduct(orderProduct);
        }

        return OrderResponseDto.fromEntity(orderRepository.save(order));
    }

    @Cacheable(cacheNames = "orderCache", key = "args[0]")
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(OrderResponseDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."));
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, ProductRequestDto requestDto) {

        Long productId = requestDto.getProduct_id();
        // 해당 상품 조회
        // 예외처리 -> FeignErrorDecoder
        productClient.getProductById(productId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."));
        List<OrderProduct> productList = order.getProduct_ids();

        productList.add(OrderProduct.createOrderProduct(productId, order));

        return OrderResponseDto.fromEntity(orderRepository.save(order));

    }

}
