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

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ItemService itemService;

    public UserController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }


    @GetMapping("/{id}")
    public String getCategoryPage(@PathVariable String id) {
        //TO DO
        return "users/details";
    }

    @GetMapping("/contact/{id}")
    public String sendMailFrom(@PathVariable String id, @RequestParam Long itemId, Model model) {
        User receiver = userService.findById(id).orElseThrow(UserNotFoundException::new);
        Item item = itemService.findById(itemId).orElseThrow(ItemNotFoundException::new);
        model.addAttribute("receiver", receiver);
        model.addAttribute("item",item);
        return "users/mail";
    }

    @PostMapping("/send")
    public String sendMail(HttpServletRequest request,
                           @RequestParam String receiver,
                           @RequestParam String subject,
                           @RequestParam String message) {
        userService.sendMail(request, receiver, subject, message);
        return "redirect:/items";
    }
}
