package neoflex.prod.practice.services;

import neoflex.prod.practice.entities.ProductsEntity;
import neoflex.prod.practice.repositories.ProductsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductsService {
    private static final Logger logger = LoggerFactory.getLogger(ProductsService.class);
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional
    public void reserveProduct(UUID productId, int count) {
        logger.atLevel(Level.INFO).log("Поиск продукта для резервирования");

        Optional<ProductsEntity> productsEntity = productsRepository.findById(productId);
        if (productsEntity.isPresent()) {
            ProductsEntity product = productsEntity.get();
            int oldCount = product.getCount();
            int newCount = product.getCount() - count;
            product.setCount(newCount);
            logger.atLevel(Level.INFO).log("Изменение count продукта c {}, на {}", oldCount, newCount);
            productsRepository.save(product);
        } else {
            logger.atLevel(Level.ERROR).log("Продукт с idProduct = {} не найден", productId);
        }
    }
}

