package com.sparta.msa_exam.order.client;

import com.sparta.msa_exam.order.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service", configuration = FeignConfiguration.class)
public interface ProductClient {
    @GetMapping("/products/{id}")
    ProductResponseDto getProduct(@PathVariable("id") Long id);
}
