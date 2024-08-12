package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.infrastructure.ProductRequestDto;
import com.sparta.msa_exam.order.application.dto.OrderRequestDto;
import com.sparta.msa_exam.order.application.dto.OrderResponseDto;
import com.sparta.msa_exam.order.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}")
    public OrderResponseDto updateOrder(@PathVariable("orderId") Long orderId, @RequestBody ProductRequestDto requestDto) {
        return orderService.updateOrder(orderId, requestDto);
    }
}
