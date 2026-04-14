package neoflex.prod.practice.services;

import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.OrdersResponse;
import neoflex.prod.practice.entities.OrdersEntity;
import neoflex.prod.practice.mappers.OrdersMapper;
import neoflex.prod.practice.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;

    private final KafkaTemplate<String, OrdersRequest> kafkaTemplate;
    @Value("${kafka.topics.reserve.product}")
    private String reserveProductTopic;

    public OrdersService(OrdersRepository ordersRepository,
                         OrdersMapper ordersMapper,
                         KafkaTemplate<String, OrdersRequest> kafkaTemplate) {
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void saveOrder(OrdersRequest request) {
        OrdersEntity ordersEntity = ordersMapper.toEntity(request);
        ordersRepository.save(ordersEntity);
    }

    @Transactional
    public OrdersResponse order(OrdersRequest request) {
        try {
            kafkaTemplate.send(reserveProductTopic, request);
            saveOrder(request);
            return new OrdersResponse(request, OrdersResponse.StatusEnum.SUCCESS);
        } catch (Exception e) {
            return new OrdersResponse(request, OrdersResponse.StatusEnum.FAIL);
        }
    }
}
