package com.app.OrderService.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    Long itemID;
    String name;
    Long quantity;
    Long price;
}
