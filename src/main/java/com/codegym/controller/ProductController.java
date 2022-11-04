package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ModelAndView listProducts() {
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }

//    @GetMapping("")
//    public String showList(Model model) {
//        Iterable<Product> productList = productService.findAll();
//        model.addAttribute("products", productList);
//        return "/product/list";
//    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveCustomer(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("success", "New product created successfully");
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product", product.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error.404");
        }
    }

    @PostMapping("/update")
    public ModelAndView updateCustomer(@ModelAttribute("product") Product product) {
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", product);
        modelAndView.addObject("success", "Product updated successfully");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product", product.get());
            return modelAndView;

        } else {
            return new ModelAndView("/error.404");
        }
    }


    @PostMapping("/delete")
    public String deleteCustomer(@ModelAttribute("product") Product product) {
        productService.remove(product.getId());
//        redirect.addFlashAttribute("success", "Removed product successfully!");
        return "redirect:/products";
    }

    @GetMapping("/{id}/view")
    public ModelAndView view(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/product/view");
        modelAndView.addObject("product", product.get());
        return modelAndView;
    }

//    @GetMapping("/search")
//    public String searchProduct(@RequestParam String name, Model model) {
//        List<Product> productList = productService.findByName(name);
//        model.addAttribute("products", productList);
//        return "/list";
//    }
}
