package neoflex.prod.practice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.prod.practice.entities.ProductsEntity;
import neoflex.prod.practice.repositories.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    @Transactional
    public void reserveProduct(UUID idProduct, int count) {
        log.info("Поиск продукта для резервирования c idProduct = {}", idProduct);
        Optional<ProductsEntity> productsEntity = productsRepository.findById(idProduct);
        if (productsEntity.isPresent()) {
            ProductsEntity product = productsEntity.get();
            product.setCount(countUpdate(product.getId(), product.getCount(), count));
            productsRepository.save(product);
        } else {
            log.error("Продукт с idProduct = {} не найден", idProduct);
        }
    }

    private int countUpdate(UUID idProduct, int oldCount, int reserveCount) {
        int newCount = oldCount - reserveCount;
        log.info("Изменение count продукта (idProduct = {}) c {}, на {}", idProduct, oldCount, newCount);
        return newCount;
    }
}

