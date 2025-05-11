    package com.app.OrderService.Controller;

    import com.app.OrderService.DTO.Request.Restaurant.ItemEditRequest;
    import com.app.OrderService.DTO.Request.Restaurant.RestaurantSaveRequest;
    import com.app.OrderService.DTO.Response.Restaurant.ItemRestaurantResponse;
    import com.app.OrderService.DTO.Response.Restaurant.RestaurantResponse;
    import com.app.OrderService.DTOMock.RequestMock;
    import com.app.OrderService.DTOMock.ResponseMock;
    import com.app.OrderService.Service.RestaurantService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import lombok.AccessLevel;
    import lombok.experimental.FieldDefaults;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.http.MediaType;
    import org.springframework.test.context.TestPropertySource;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;
    import org.springframework.test.web.servlet.MockMvc;

    import java.util.ArrayList;
    import java.util.List;

    import static org.mockito.Mockito.when;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @SpringBootTest
    @AutoConfigureMockMvc
    @TestPropertySource(properties = "KEY_JWT=1234")
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class RestaurantControllerTest {
        @Autowired
        MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @MockitoBean
        RestaurantService restaurantService;

        RestaurantSaveRequest restaurantSaveRequest;
        ItemEditRequest itemEditRequest;

        ItemRestaurantResponse itemRestaurantResponse;
        RestaurantResponse restaurantResponse;

        @BeforeEach
        void initData() {
            restaurantSaveRequest = RequestMock.restaurantSaveRequest();
            itemEditRequest = RequestMock.itemEditRequest();
            itemRestaurantResponse = ResponseMock.itemRestaurantResponse("name");
            restaurantResponse = ResponseMock.restaurantResponse();
        }

        @Test
        void findAll_success() throws Exception {
            when(restaurantService.findAll()).thenReturn(new ArrayList<>(List.of(restaurantResponse, restaurantResponse)));
            mockMvc.perform(get("/restaurant")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value(1000))
                    .andExpect(jsonPath("result.length()").value(2));

        }

        @Test
        void findById_success() throws Exception {
            when(restaurantService.findById("restaurantID")).thenReturn(restaurantResponse);
            mockMvc.perform(get("/restaurant/restaurantID")
                            .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value(1000))
                    .andExpect(jsonPath("result.restaurantID").value(restaurantResponse.getRestaurantID()));

        }

        @Test
        void save_success() throws Exception {
            when(restaurantService.save(restaurantSaveRequest)).thenReturn(restaurantResponse);
            var content = objectMapper.writeValueAsString(restaurantSaveRequest);

            mockMvc.perform(post("/restaurant")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(content))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value(1000))
                    .andExpect(jsonPath("result.restaurantID").value(restaurantResponse.getRestaurantID()));
        }

        @Test
        void addItem_success() throws Exception {
            when(restaurantService.addItem("restaurantID",itemEditRequest)).thenReturn(itemRestaurantResponse);
            var content = objectMapper.writeValueAsString(itemEditRequest);

            mockMvc.perform(put("/restaurant/id=restaurantID/item/add")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(content))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("code").value(1000))
                    .andExpect(jsonPath("result.itemID").value(itemRestaurantResponse.getItemID()));
        }




    }
