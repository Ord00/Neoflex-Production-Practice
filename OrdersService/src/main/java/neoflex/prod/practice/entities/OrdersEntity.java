package neoflex.prod.practice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrdersEntity {
    @Id
    @Column(name = "id_order", nullable = false)
    private UUID id;

    @Column(name = "id_product", nullable = false, length = 36)
    private String idProduct;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "id_user", nullable = false, length = 36)
    private String idUser;

    @Column(name = "date_create", nullable = false)
    private Instant dateCreate;
}
