package mk.ukim.finki.wp.lostfound.repository;

import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaSpecificationRepository<Item, Long>{
    Page<Item> findByName(String name, Pageable pageable);
    Page<Item> findByCategory(Category category, Pageable pageable);
    @Query("select i from Item i WHERE i.isLost = ?1")
    Page<Item> findLost(Boolean lost, Pageable pageable);
    Page<Item> findByNameAndCategory(String name, Category category, Pageable pageable);
    @Query("select i from Item i WHERE i.name = ?1 and i.isLost = ?2")
    Page<Item> findByNameAndByLost(String name, Boolean lost, Pageable pageable);
    @Query("select i from Item i WHERE i.category = ?1 and i.isLost = ?2")
    Page<Item> findByCategoryAndByLost(Category category, Boolean lost, Pageable pageable);
    @Query("select i from Item i WHERE i.name=?1 and i.category = ?2 and i.isLost = ?3")
    Page<Item> findByNameAndByCategoryAndByLost(String name, Category category, Boolean lost, Pageable pageable);
}
