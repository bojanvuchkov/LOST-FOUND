package mk.ukim.finki.wp.lostfound.web;

import mk.ukim.finki.wp.lostfound.model.Category;
import mk.ukim.finki.wp.lostfound.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategoryPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Category> categories = this.categoryService.listCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        this.categoryService.delete(id);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryPage(@PathVariable Long id, Model model) {
        if (this.categoryService.findById(id).isPresent()) {
            Category category = this.categoryService.findById(id).get();
            model.addAttribute("category", category);
            return "categories/add";
        }
        return "redirect:/categories?error=CategoryNotFound";
    }

    @GetMapping("/add")
    public String addCategoryPage() {
        return "categories/add";
    }

    @PostMapping("/add")
    public String saveCategory(@RequestParam String name) {
        categoryService.create(name);
        return "redirect:/categories";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id,
                               @RequestParam String name) {
        categoryService.update(id, name);
        return "redirect:/categories";
    }
}
