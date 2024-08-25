package com.sparta.msa_exam.order.infrastructure;

import com.sparta.msa_exam.order.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 상품 서비스 호출 클라이언트가 응용 계층의 인터페이스인 ProductService를 상속받아 DIP를 적용
@FeignClient(name = "product-service", configuration = FeignConfiguration.class)
public interface ProductClient extends ProductService {
    @GetMapping("/products/{id}")
    ProductResponseDto getProductById(@PathVariable("id") Long id);
}
