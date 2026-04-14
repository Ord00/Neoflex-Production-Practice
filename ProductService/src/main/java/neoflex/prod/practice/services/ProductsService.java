package neoflex.prod.practice.services;

import neoflex.prod.practice.repositories.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional
    public void reserveProduct(UUID productId, int count) {
        productsRepository.findById(productId)
                .ifPresent(product -> {
                    product.setCount(product.getCount() - count);
                    productsRepository.save(product);
                });
    }
}

