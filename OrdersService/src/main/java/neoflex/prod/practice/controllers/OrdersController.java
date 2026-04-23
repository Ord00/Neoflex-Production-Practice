package neoflex.prod.practice.controllers;

import neoflex.prod.practice.dto.OrdersRequest;
import neoflex.prod.practice.dto.OrdersResponse;
import neoflex.prod.practice.services.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/order")
    public ResponseEntity<OrdersResponse> order(@RequestBody OrdersRequest ordersRequest) {
        OrdersResponse ordersResponse = ordersService.order(ordersRequest);
        return ordersResponse.getStatus().equals(OrdersResponse.StatusEnum.SUCCESS) ?
                ResponseEntity.ok(ordersResponse) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ordersResponse);
    }
}
