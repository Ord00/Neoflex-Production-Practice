package neoflex.prod.practice.services;

import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.OrdersResponse;
import neoflex.prod.practice.entities.OrdersEntity;
import neoflex.prod.practice.mappers.OrdersMapper;
import neoflex.prod.practice.repositories.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersService {
    private final Logger logger = LoggerFactory.getLogger(OrdersService.class);
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
        logger.atLevel(Level.INFO).log("Сохранение заявки c idOrder = {}", request.getIdOrder());
        OrdersEntity ordersEntity = ordersMapper.toEntity(request);
        ordersRepository.save(ordersEntity);
        logger.atLevel(Level.INFO).log("Заявка сохранена c idOrder = {}", request.getIdOrder());
    }

    @Transactional
    public OrdersResponse order(OrdersRequest request) {
        try {
            logger.atLevel(Level.INFO).log("Отправка сообщения с idOrder = {}", request.getIdOrder());
            kafkaTemplate.send(reserveProductTopic,
                    new ReserveProductDto(request.getIdProduct(), request.getCount()));
            saveOrder(request);
            return new OrdersResponse(request, OrdersResponse.StatusEnum.SUCCESS);
        } catch (Exception e) {
            return new OrdersResponse(request, OrdersResponse.StatusEnum.FAIL);
        }
    }
}
