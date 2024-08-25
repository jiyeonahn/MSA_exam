package com.sparta.msa_exam.order.application;

import com.sparta.msa_exam.order.infrastructure.ProductRequestDto;
import com.sparta.msa_exam.order.infrastructure.ProductService;
import com.sparta.msa_exam.order.application.dto.OrderRequestDto;
import com.sparta.msa_exam.order.application.dto.OrderResponseDto;
import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.domain.OrderProduct;
import com.sparta.msa_exam.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        //해당 상품 조회
        // 예외처리 -> FeignErrorDecoder
        for(Long productId : requestDto.getOrderItemIds()){
            productService.getProductById(productId);
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
        productService.getProductById(productId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."));
        List<OrderProduct> productList = order.getProduct_ids();

        productList.add(OrderProduct.createOrderProduct(productId, order));

        return OrderResponseDto.fromEntity(orderRepository.save(order));

    }

}
