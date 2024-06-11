package mk.ukim.finki.wp.lostfound.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Item;
import mk.ukim.finki.wp.lostfound.model.User;
import mk.ukim.finki.wp.lostfound.model.exceptions.ItemNotFoundException;
import mk.ukim.finki.wp.lostfound.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.lostfound.service.CategoryService;
import mk.ukim.finki.wp.lostfound.service.ItemService;
import mk.ukim.finki.wp.lostfound.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final UserService userService;

    public ItemController(ItemService itemService, CategoryService categoryService, UserService userService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping
    public String getItemsPage(Model model) {
        List<Item> items = this.itemService.listItems();
        HashMap<Item, String> dateTimes = new HashMap<>();
        HashMap<Item, String> images = new HashMap<>();
        items.forEach(item -> {
            dateTimes.put(item, item.getDateRegistered().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            images.put(item, Base64.getEncoder().encodeToString(item.getImage()));
        });
        List<Category> categories = categoryService.listCategories();
        model.addAttribute("items", items);
        model.addAttribute("images", images);
        model.addAttribute("dateTimes", dateTimes);
        model.addAttribute("categories", categories);
        return "items/list";
    }
    @PostMapping("/search")
    public String search(@RequestParam String name,
                         @RequestParam String isLost,
                         @RequestParam Long categoryId,
                         Model model) {
        List<Item> items = this.itemService.filter(name, isLost, categoryId);
        HashMap<Item, String> dateTimes = new HashMap<>();
        items.forEach(item -> dateTimes.put(item, item.getDateRegistered().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))));
        List<Category> categories = categoryService.listCategories();
        model.addAttribute("items", items);
        model.addAttribute("dateTimes", dateTimes);
        model.addAttribute("categories", categories);
        return "/items/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        this.itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String editItemPage(@PathVariable Long id, Model model) {
        Item item = this.itemService.findById(id).orElseThrow(ItemNotFoundException::new);
        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("image", Base64.getEncoder().encodeToString(item.getImage()));
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        return "items/add";
    }

    @GetMapping("/add")
    public String addItemPage(Model model) {
        List<Category> categories = this.categoryService.listCategories();

        model.addAttribute("categories", categories);
        return "items/add";
    }

    @PostMapping("/add")
    public String saveItem(HttpServletRequest request,
                           @RequestParam String name,
                           @RequestParam String description,
                           @RequestParam String isLost,
                           @RequestParam Long categoryId,
                           @RequestParam MultipartFile file,
                           @RequestParam String location) {
        this.itemService.create(request, name, description, isLost, categoryId, file, location);
        return "redirect:/items";
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable("id") Long id,
                           @RequestParam String name,
                           @RequestParam String description,
                           @RequestParam String isLost,
                           @RequestParam Long categoryId,
                           @RequestParam MultipartFile file,
                           @RequestParam String location) {

        itemService.update(id, name, description, isLost, categoryId, file, location);
        return "redirect:/items";
    }
}


