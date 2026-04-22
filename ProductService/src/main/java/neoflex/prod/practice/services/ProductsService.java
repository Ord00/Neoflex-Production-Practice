package neoflex.prod.practice.services;

import lombok.extern.slf4j.Slf4j;
import neoflex.prod.practice.entities.ProductsEntity;
import neoflex.prod.practice.repositories.ProductsRepository;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional
    public void reserveProduct(UUID idProduct, int count) {
        log.atLevel(Level.INFO).log("Поиск продукта для резервирования c idProduct = {}", idProduct);

        Optional<ProductsEntity> productsEntity = productsRepository.findById(idProduct);
        if (productsEntity.isPresent()) {
            ProductsEntity product = productsEntity.get();
            countUpdate(product, count);
            productsRepository.save(product);
        } else {
            log.atLevel(Level.ERROR).log("Продукт с idProduct = {} не найден", idProduct);
        }
    }

    private void countUpdate(ProductsEntity product, int reserveCount) {
        int oldCount = product.getCount();
        int newCount = oldCount - reserveCount;
        log.atLevel(Level.INFO).log("Изменение count продукта (idProduct = {}) c {}, на {}",
                product.getId(), oldCount, newCount);
        product.setCount(newCount);
    }
}

