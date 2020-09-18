package com.github.vika.store;

import com.github.vika.store.domain.Manufacturer;
import com.github.vika.store.domain.Product;
import com.github.vika.store.domain.ProductType;
import com.github.vika.store.repository.ManufacturerRepository;
import com.github.vika.store.repository.ProductRepository;
import com.github.vika.store.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitDatabase implements ApplicationRunner {

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    private List<String[]> readCsv(String fileName) throws IOException {
        File file = new ClassPathResource(fileName, this.getClass().getClassLoader()).getFile();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(file))) {
            List<String[]> rows = new ArrayList<>();

            String row = csvReader.readLine();
            // Skipping first row - should always be header
            row = csvReader.readLine();
            while (row != null) {
                String[] data = row.split(",");
                rows.add(data);
                row = csvReader.readLine();
            }
            return rows;
        }
    }

    private Map<String, Manufacturer> loadManufacturers() throws IOException {
        List<String[]> rows = readCsv("init/manufacturer.csv");
        Map<String, Manufacturer> virtualMap = new HashMap<>();

        rows.forEach(row -> {
            String virtualId = row[0];
            String manufacturerName = row[1];

            Manufacturer model = manufacturerRepository.save(new Manufacturer(manufacturerName));

            virtualMap.put(virtualId, model);
        });
        return virtualMap;
    }

    private Map<String, ProductType> loadProductTypes() throws IOException {
        List<String[]> rows = readCsv("init/productType.csv");

        Map<String, ProductType> virtualMap = new HashMap<>();
        rows.forEach(row -> {
            String virtualId = row[0];
            String productType = row[1];

            ProductType model = productTypeRepository.save(new ProductType(productType));

            virtualMap.put(virtualId, model);
        });
        return virtualMap;
    }

    private Map<String, Product> loadProducts(Map<String, ProductType> productTypes, Map<String,
            Manufacturer> manufacturers) throws IOException {
        List<String[]> rows = readCsv("init/product.csv");

        Map<String, Product> virtualMap = new HashMap<>();
        rows.forEach(row -> {
            String virtualId = row[0];
            String serialNumber = row[1];
            ProductType productType = productTypes.get(row[2]);
            String info = row[3];
            Manufacturer manufacturer = manufacturers.get(row[4]);
            int price = Integer.parseInt(row[5]);
            int count = Integer.parseInt(row[6]);

            Product model = productRepository.save(new Product(serialNumber, productType, info,
                    manufacturer, price, count));

            virtualMap.put(virtualId, model);
        });
        return virtualMap;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        manufacturerRepository.deleteAll();
        productRepository.deleteAll();
        productTypeRepository.deleteAll();

        Map<String, Manufacturer> manufacturers = loadManufacturers();
        Map<String, ProductType> productTypes = loadProductTypes();
        Map<String, Product> products = loadProducts(productTypes, manufacturers);
    }
}
