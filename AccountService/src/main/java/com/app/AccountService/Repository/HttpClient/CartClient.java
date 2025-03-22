package com.app.AccountService.Repository.HttpClient;

import com.app.AccountService.DTO.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cart", url = "${app.services.cart}")
public interface CartClient {
    @PostMapping(value = "/{userID}",produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<?> save(@PathVariable String userID);
    @DeleteMapping(value = "/{cartID}" , produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<Boolean> delete(@PathVariable String cartID);
}
