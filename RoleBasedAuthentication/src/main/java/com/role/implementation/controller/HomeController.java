package com.role.implementation.controller;

import com.role.implementation.model.Products;
import com.role.implementation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/products")
    public String home(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                       @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                       @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir, Model m) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo, 3, sort);

        Page<Products> page = productRepo.findAll(pageable);

        List<Products> list = page.getContent();

        m.addAttribute("pageNo", pageNo);
        m.addAttribute("totalElements", page.getTotalElements());
        m.addAttribute("totalPage", page.getTotalPages());
        m.addAttribute("all_products", list);

        m.addAttribute("sortField", sortField);
        m.addAttribute("sortDir", sortDir);
        m.addAttribute("revSortDir", sortDir.equals("asc") ? "desc" : "asc");

        return "products";
    }
    

    @GetMapping("/load_form")
    public String loadForm() {
        return "add";
    }

    @GetMapping("/edit_form/{id}")
    public String editForm(@PathVariable(value = "id") long id, Model m) {

        Optional<Products> product = productRepo.findById(id);

        Products pro = product.get();
        m.addAttribute("product", pro);

        return "edit";
    }

    @PostMapping("/save_products")
    public String saveProducts(@ModelAttribute Products products, HttpSession session) {

        productRepo.save(products);
        session.setAttribute("msg", "Product Added Successfully..");

        return "redirect:/load_form";
    }

    @PostMapping("/update_products")
    public String updateProducts(@ModelAttribute Products products, HttpSession session) {

        productRepo.save(products);
        session.setAttribute("msg", "Product Update Successfully..");

        return "redirect:/products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProducts(@PathVariable(value = "id") long id, HttpSession session) {
        productRepo.deleteById(id);
        session.setAttribute("msg", "Product Delete Successfully..");
        return "redirect:/products";
    }


}
