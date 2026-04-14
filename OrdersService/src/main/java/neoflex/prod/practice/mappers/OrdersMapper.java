package neoflex.prod.practice.mappers;

import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.entities.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdersMapper {
    OrdersEntity toEntity(OrdersRequest ordersRequest);
}
