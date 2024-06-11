package mk.ukim.finki.wp.lostfound.service;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Item;
import mk.ukim.finki.wp.lostfound.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> listItems();

    List<Item> filter(String name, String isLost, Long categoryId);

    Optional<Item> findById(Long id);

    Item create(HttpServletRequest request, String name, String description, String isLost, Long categoryId, MultipartFile file, String location);

    Item update(Long id, String name, String description, String isLost, Long categoryId, MultipartFile file, String location);

    void delete(Long id);

}
