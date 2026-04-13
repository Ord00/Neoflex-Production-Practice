package neoflex.prod.practice.repositories;

import neoflex.prod.practice.entities.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, UUID> {
    @Modifying
    @Query("update ProductsEntity p set p.count = p.count - :count where p.id = :productId")
    void decreaseCount(@Param("productId") UUID productId, @Param("count") int count);
}
