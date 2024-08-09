package com.sparta.msa_exam.order;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getProductById")){
                    return new ResponseStatusException(HttpStatus.valueOf(response.status())
                            , "해당 상품을 찾을 수 없습니다.");
                }
        }
        return new Exception(response.reason());
    }
}
