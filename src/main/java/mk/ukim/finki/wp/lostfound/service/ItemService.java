package mk.ukim.finki.wp.lostfound.service;

import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Item;
import mk.ukim.finki.wp.lostfound.model.Location;
import mk.ukim.finki.wp.lostfound.model.enums.Status;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> listItems(String name, String isLost, Long categoryId);

    Optional<Item> findById(Long id);

    Item create(String name, String description, String isLost, Long categoryId, MultipartFile file, String location);

    Item update(Long id, String name, String description, String isLost, Long categoryId, MultipartFile file, String location);

    void delete(Long id);

}
