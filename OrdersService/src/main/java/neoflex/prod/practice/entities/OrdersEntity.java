package neoflex.prod.practice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "orders")
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
    private Instant dateCreate = Instant.now();

    public Instant getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Instant dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
