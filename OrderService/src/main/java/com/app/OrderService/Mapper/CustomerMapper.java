package com.app.OrderService.Mapper;

import com.app.OrderService.Model.ItemModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Named("mapToList")
    default List<ItemModel> mapToList(Map<Long, ItemModel> menu) {
        return new ArrayList<>(menu.values());
    }

    @Named("mapToMap")
    default Map<Long, ItemModel> toMap(List<ItemModel> menu){
        return menu.stream().collect(Collectors.toMap(ItemModel::getItemID,itemModel -> itemModel));
    }

}
