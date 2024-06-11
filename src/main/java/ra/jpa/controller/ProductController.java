package ra.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ra.jpa.dto.request.ProductCreateRequest;
import ra.jpa.entity.Product;

import ra.jpa.service.IProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping("/list")
    // danh sách
    public String productList(@PageableDefault(page = 0,size = 3) Pageable pageable, Model model){
        model.addAttribute("page",productService.findAll(pageable));
        model.addAttribute("product",new ProductCreateRequest());
        return "product";
    }

//    @GetMapping("/add")
//    public  String add (){
//
//    }
    @GetMapping("/edit")
    public  String edit (@RequestParam Integer id , Model model){

        Product product  = productService.findById(id);
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stock(product.getStock())
                .build();
        model.addAttribute("product",request);
        model.addAttribute("id",product.getId());
        model.addAttribute("imageUrl",product.getImageUrl());
        return "product-edit";
    }
    @GetMapping("/delete")
    public  String delete (@RequestParam Integer id){
        productService.deleteById(id);
        return "redirect:/product/list";
    }
    @PostMapping("/add")
    public  String doAdd (@ModelAttribute("product") ProductCreateRequest request){
        productService.save(request);

        ArrayList<String> list = new ArrayList<>();
        list.isEmpty();
        return "redirect:/product/list";
    }
    @PostMapping("/update")
    public  String doUpdate (@ModelAttribute("product") ProductCreateRequest request, @RequestParam Integer id ){
        productService.save(request,id);
        return "redirect:/product/list";
    }
}
