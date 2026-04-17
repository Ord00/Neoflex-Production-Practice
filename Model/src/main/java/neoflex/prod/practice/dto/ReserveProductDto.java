package neoflex.prod.practice.dto;

import java.util.UUID;

public class ReserveProductDto {
    private UUID idProduct;
    private int count;

    public ReserveProductDto() {}

    public UUID getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
