package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductResponseDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = Order.createOrder(requestDto.getName());

        for (Long productId : requestDto.getOrderItemIds()) {
//            ProductResponseDto product = productClient.getProduct(productId);
//            log.info("############################ Product 수량 확인 : " + product.getQuantity());
//            if (product.getQuantity() < 1) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " is out of stock.");
//            }

            OrderProduct orderProduct = OrderProduct.createOrderProduct(productId, order);
            order.addProduct(orderProduct);
        }

//        // Reduce the quantity of each product by 1
//        for (Long productId : requestDto.getOrderItemIds()) {
//            productClient.reduceProductQuantity(productId, 1);
//        }

        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        return new OrderResponseDto(order);
    }


}
