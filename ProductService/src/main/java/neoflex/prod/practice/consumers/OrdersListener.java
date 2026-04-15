package neoflex.prod.practice.consumers;


import neoflex.prod.practice.services.ProductsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrdersListener {
    private final ProductsService productsService;

    public OrdersListener(ProductsService productsService) {
        this.productsService = productsService;
    }

    @KafkaListener(topics = "${kafka.topics.reserve.product}")
    public void reserveProduct(@Payload ReserveProductDto dto) {
        productsService.reserveProduct(dto.getIdProduct(), dto.getCount());
    }
}
