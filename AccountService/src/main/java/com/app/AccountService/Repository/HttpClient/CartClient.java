package com.app.AccountService.Repository.HttpClient;

import com.app.AccountService.DTO.ApiResponse;
import com.app.AccountService.DTO.Request.CartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cart", url = "${app.services.cart}")
public interface CartClient {
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> save(@RequestBody CartRequest request);
    @DeleteMapping(value = "/{cartID}" , produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Boolean> delete(@PathVariable String cartID);
}
