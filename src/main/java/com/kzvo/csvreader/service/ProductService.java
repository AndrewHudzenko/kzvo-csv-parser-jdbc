package com.kzvo.csvreader.service;

import com.kzvo.csvreader.dao.ProductDao;
import com.kzvo.csvreader.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public void processCsvFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");

                String priceString = fields[2].replace(",", ".");
                double price = Double.parseDouble(priceString);

                Product product = Product.builder()
                        .id(Long.parseLong(fields[0]))
                        .name(fields[1])
                        .price(BigDecimal.valueOf(price))
                        .type(Integer.parseInt(fields[3]))
                        .build();

                productDao.saveProduct(product);
            }

        } catch (IOException e) {
            throw new RuntimeException("Can't parse csv file", e);
        }
    }
}