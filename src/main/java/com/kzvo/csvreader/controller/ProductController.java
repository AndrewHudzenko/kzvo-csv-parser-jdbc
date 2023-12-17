package com.kzvo.csvreader.controller;

import com.kzvo.csvreader.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(@RequestParam MultipartFile multipartFile) {
        productService.processCsvFile(multipartFile);
        return ResponseEntity.ok("Products successfully uploaded!");
    }
}
