package com.sparta.msa_exam.order.client;

import java.util.List;

// 응용 계층 DIP 적용을 위한 상품 서비스 인터페이스
public interface ProductService {
    ProductResponseDto getProductById(Long id);
}
