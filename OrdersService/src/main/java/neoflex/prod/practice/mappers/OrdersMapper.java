package neoflex.prod.practice.mappers;

import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.ReserveProductDto;
import neoflex.prod.practice.entities.OrdersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrdersMapper {
    @Mapping(source = "idOrder", target = "id")
    OrdersEntity toEntity(OrdersRequest ordersRequest);
    ReserveProductDto toReserveProductDto(OrdersRequest ordersRequest);
}
