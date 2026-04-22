package neoflex.prod.practice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.OrdersResponse;
import neoflex.prod.practice.entities.OrdersEntity;
import neoflex.prod.practice.mappers.OrdersMapper;
import neoflex.prod.practice.avro.ReserveProductDto;
import neoflex.prod.practice.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final KafkaTemplate<String, ReserveProductDto> kafkaTemplate;
    @Value("${kafka.topics.reserve.product}")
    private String reserveProductTopic;

    public void saveOrder(OrdersRequest request) {
        UUID orderId = UUID.randomUUID();
        log.info("Сохранение заявки c idOrder = {}", orderId);
        OrdersEntity ordersEntity = ordersMapper.toEntity(request);
        ordersEntity.setId(orderId);
        ordersRepository.save(ordersEntity);
        log.info("Заявка сохранена c idOrder = {}", orderId);
    }

    @Transactional
    public OrdersResponse order(OrdersRequest request) {
        try {
            ReserveProductDto dto = ordersMapper.toReserveProductDto(request);
            log.info("Отправка сообщения с idProduct = {} и count = {}",
                    request.getIdProduct(), request.getCount());
            kafkaTemplate.send(reserveProductTopic, dto);
            saveOrder(request);
            return new OrdersResponse(request, OrdersResponse.StatusEnum.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new OrdersResponse(request, OrdersResponse.StatusEnum.FAIL);
        }
    }
}
