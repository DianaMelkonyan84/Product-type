package org.example.product;

// ProductController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            byte[] imageBytes = imageFile.getBytes();
            product.setImages(imageBytes);
        }
        productService.saveProduct(product);
        return "redirect:/save-page";
    }


    @GetMapping("/save-page")
    public String savePage(Model model) {
        model.addAttribute("product", new Product());
        return "success";
    }

    @GetMapping("/products")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/products/{id}")
    @ResponseBody
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
        Optional<Product> products = productService.getProductsById(id);
        if (products.isPresent()) {
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Endpoint to get products by name
    @GetMapping("/products/search/{name}")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name) {
        List<Product> productList = productService.getProductsByName(name);
        return ResponseEntity.ok().body(productList);
    }
  @GetMapping("/products/search/model/{modelName}")
    @ResponseBody
    public ResponseEntity<List <Product>> getProductsByModelName(@PathVariable String modelName){
        List<Product> productList=productService.getProductsByModelName(modelName);
      return ResponseEntity.ok().body(productList);
  }


    @GetMapping("/products/tags/{tag}")
    @ResponseBody
    public ResponseEntity<List<Product>> getProductsByTag(@PathVariable String tag) {
        List<Product> productList = productService.getProductsByTag(tag);
        return ResponseEntity.ok().body(productList);
    }
    @GetMapping("/category")
    public String showCategory(Model model) {
        model.addAttribute("product", new Product());
        return "category";
    }
}

