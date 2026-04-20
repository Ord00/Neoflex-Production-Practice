package neoflex.prod.practice.services;

import lombok.extern.slf4j.Slf4j;
import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.OrdersResponse;
import neoflex.prod.practice.dto.ReserveProductDto;
import neoflex.prod.practice.entities.OrdersEntity;
import neoflex.prod.practice.mappers.OrdersMapper;
import neoflex.prod.practice.repositories.OrdersRepository;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    private final KafkaTemplate<String, ReserveProductDto> kafkaTemplate;
    @Value("${kafka.topics.reserve.product}")
    private String reserveProductTopic;

    public OrdersService(OrdersRepository ordersRepository,
                         OrdersMapper ordersMapper,
                         KafkaTemplate<String, ReserveProductDto> kafkaTemplate) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void saveOrder(OrdersRequest request) {
        UUID orderId = UUID.randomUUID();
        log.atLevel(Level.INFO).log("Сохранение заявки c idOrder = {}", orderId);
        OrdersEntity ordersEntity = ordersMapper.toEntity(request);
        ordersEntity.setId(orderId);
        ordersRepository.save(ordersEntity);
        log.atLevel(Level.INFO).log("Заявка сохранена c idOrder = {}", orderId);
    }

    @Transactional
    public OrdersResponse order(OrdersRequest request) {
        try {
            ReserveProductDto dto = ordersMapper.toReserveProductDto(request);
            log.atLevel(Level.INFO).log("Отправка сообщения с idProduct = {} и count = {}",
                    request.getIdProduct(), request.getCount());
            kafkaTemplate.send(reserveProductTopic, dto);
            saveOrder(request);
            return new OrdersResponse(request, OrdersResponse.StatusEnum.SUCCESS);
        } catch (Exception e) {
            log.atLevel(Level.ERROR).log(e.getMessage());
            return new OrdersResponse(request, OrdersResponse.StatusEnum.FAIL);
        }
    }
}
