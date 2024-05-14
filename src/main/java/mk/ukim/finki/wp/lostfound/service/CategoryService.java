package mk.ukim.finki.wp.lostfound.service;

import mk.ukim.finki.wp.lostfound.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> listCategories();

    Optional<Category> findById(Long id);

    Category create(String name);

    Category update(Long id, String name);

    void delete(Long id);
}
