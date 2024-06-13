package mk.ukim.finki.wp.lostfound.web;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.model.Email;
import mk.ukim.finki.wp.lostfound.model.User;
import mk.ukim.finki.wp.lostfound.model.exceptions.ItemNotFoundException;
import mk.ukim.finki.wp.lostfound.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wp.lostfound.service.CategoryService;
import mk.ukim.finki.wp.lostfound.service.ItemService;
import mk.ukim.finki.wp.lostfound.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
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
    public String getDetails(@PathVariable String id, Model model) {
        User loggedInUser = userService.findById(id).orElseThrow(UserNotFoundException::new);
        model.addAttribute("loggedInUser", loggedInUser);
        HashMap<Email, String> dateTimes = new HashMap<>();
        loggedInUser.getReceivedEmails().forEach(email -> {
            dateTimes.put(email, email.getDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        });
        model.addAttribute("dateTimes",dateTimes);
        return "users/details";
    }

    @PostMapping ("/{id}/deleteMessage/{messageId}")
    public String deleteMessage(HttpServletRequest request,
                                @PathVariable String id,
                                @PathVariable Long messageId,
                                Model model) {
        if(request.getUserPrincipal().getName().equals(id))
            this.userService.deleteMessage(id, messageId);
//        User loggedInUser = userService.findById(userId).orElseThrow(UserNotFoundException::new);
//        model.addAttribute("loggedInUser", loggedInUser);
        return "redirect:/users/{id}";
    }

    @GetMapping("/contact/{id}")
    public String sendMailFrom(HttpServletRequest request,
                               @PathVariable String id,
                               Model model) {
        User receiver = userService.findById(id).orElseThrow(UserNotFoundException::new);
        String username = request.getUserPrincipal().getName();
        model.addAttribute("username", username);
        model.addAttribute("receiver", receiver);
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
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request) {
//        request.getSession().invalidate();
//        return "redirect:/items";
//    }

}
