package mk.ukim.finki.wp.lostfound.repository;

import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Item;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaSpecificationRepository<Item, Long>{
    List<Item> findByName(String name);
    List<Item> findByCategory(Category category);
    @Query("select i from Item i WHERE i.isLost = ?1")
    List<Item> findLost(Boolean lost);
    List<Item> findByNameAndCategory(String name, Category category);
    @Query("select i from Item i WHERE i.name = ?1 and i.isLost = ?2")
    List<Item> findByNameAndByLost(String name, Boolean lost);
    @Query("select i from Item i WHERE i.category = ?1 and i.isLost = ?2")
    List<Item> findByCategoryAndByLost(Category category, Boolean lost);
    @Query("select i from Item i WHERE i.name=?1 and i.category = ?2 and i.isLost = ?3")
    List<Item> findByNameAndByCategoryAndByLost(String name, Category category, Boolean lost);
}
