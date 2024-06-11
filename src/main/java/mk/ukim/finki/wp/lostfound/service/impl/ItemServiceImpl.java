package mk.ukim.finki.wp.lostfound.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Item;
import mk.ukim.finki.wp.lostfound.model.User;
import mk.ukim.finki.wp.lostfound.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.wp.lostfound.model.exceptions.ItemNotFoundException;
import mk.ukim.finki.wp.lostfound.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.lostfound.repository.CategoryRepository;
import mk.ukim.finki.wp.lostfound.repository.ItemRepository;
import mk.ukim.finki.wp.lostfound.repository.UserRepository;
import mk.ukim.finki.wp.lostfound.service.ItemService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Item> listItems() {
        return itemRepository.findAll();

    }

    @Override
    public List<Item> filter(String name, String isLost, Long categoryId) {
        if(name == null && isLost == null && categoryId == null)
            return itemRepository.findAll();
        else if(name.isEmpty() && isLost.equals("All") && categoryId == -1)
            return itemRepository.findAll();
        else if(!name.isEmpty() && !Objects.equals(isLost, "All") && categoryId != -1) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            return itemRepository.findByNameAndByCategoryAndByLost(name, category, Objects.equals(isLost, "Lost"));
        }
        else if(!isLost.equals("All") && categoryId != -1){
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            return itemRepository.findByCategoryAndByLost(category, Objects.equals(isLost, "Lost"));
        }
        else if(!name.isEmpty() && categoryId != -1){
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            return itemRepository.findByNameAndCategory(name, category);
        }
        else if(!name.isEmpty() && !isLost.equals("All")){
            return itemRepository.findByNameAndByLost(name, Objects.equals(isLost, "Lost"));
        }
        else if(!name.isEmpty())
            return itemRepository.findByName(name);
        else if(!isLost.equals("All")) {
            return itemRepository.findLost(Objects.equals(isLost, "Lost"));
        }
        else {
            Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
            return itemRepository.findByCategory(category);
        }
    }


    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item create(HttpServletRequest request, String name, String description, String isLost, Long categoryId, MultipartFile file, String location) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        boolean lost = Objects.equals(isLost, "Lost");
        String username = request.getUserPrincipal().getName();
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        Item item = null;
        try {
            byte[] imageBytes = IOUtils.toByteArray(new URL("https://clipground.com/images/no-image-png-5.jpg"));
            item = new Item(name, description, lost, category,  !file.isEmpty() ? file.getBytes() : imageBytes, location, user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return itemRepository.save(item);
    }

    @Override
    public Item update(Long id, String name, String description, String isLost, Long categoryId, MultipartFile file, String location) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        boolean lost = Objects.equals(isLost, "Lost");
        Item item = this.findById(id).orElseThrow(ItemNotFoundException::new);
        item.setName(name);
        item.setDescription(description);
        item.setLost(lost);
        item.setCategory(category);
        if(!file.isEmpty()) {
            try {
                item.setImage(file.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        item.setLocation(location);
        return itemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }
}
