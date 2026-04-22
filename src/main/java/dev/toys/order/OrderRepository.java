package dev.toys.order;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = "customer")
    List<Order> findAllByStatusNotInOrderById(List<OrderStatus> orderstatus);

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"customer", "customer.country"
            , "details", "details.product"})
    Optional<Order> findById(Long id);

    //    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @EntityGraph(attributePaths = {"details", "details.product"})
    @Query("select o from Order o where o.id = :id")
    Optional<Order> findAndLockById(Long id);
}