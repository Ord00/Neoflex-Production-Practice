package neoflex.prod.practice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class ProductsEntity {
    @Id
    @Column(name = "id_product", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count", nullable = false)
    private Integer count;

    @ColumnDefault("now()")
    @Column(name = "date_time_last_change", nullable = false)
    private Instant dateTimeLastChange;
}
