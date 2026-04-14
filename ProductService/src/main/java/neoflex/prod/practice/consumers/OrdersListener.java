package neoflex.prod.practice.consumers;


import neoflex.prod.practice.services.ProductsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrdersListener {
    private final ProductsService productsService;

    public OrdersListener(ProductsService productsService) {
        this.productsService = productsService;
    }

    @KafkaListener(topics = "${kafka.topics.reserve.product}")
    public void reserveProduct(@Payload UUID idProduct, @Payload int count) {
        productsService.reserveProduct(idProduct, count);
    }
}
