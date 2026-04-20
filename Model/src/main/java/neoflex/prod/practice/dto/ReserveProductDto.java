package neoflex.prod.practice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
public class ReserveProductDto {
    private UUID idProduct;
    private int count;
}
