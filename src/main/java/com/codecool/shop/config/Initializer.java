//package com.codecool.shop.config;
//
//import com.codecool.shop.dao.ProductCategoryDao;
//import com.codecool.shop.dao.ProductDao;
//import com.codecool.shop.dao.SupplierDao;
//import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
//import com.codecool.shop.dao.implementation.ProductDaoMem;
//import com.codecool.shop.dao.implementation.SupplierDaoMem;
//import com.codecool.shop.model.Product;
//import com.codecool.shop.model.ProductCategory;
//import com.codecool.shop.model.Supplier;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//public class Initializer implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        ProductDao productDataStore = ProductDaoMem.getInstance();
//        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
//        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
//
//        //setting up a new supplier
//        Supplier martin = new Supplier("George R.R. Martin", "Lazy author");
//        Supplier shaefer = new Supplier("Craig Shaefer", "Great writer");
//        Supplier steel = new Supplier("Danielle Steel", "Very rich robot writer");
//        Supplier sparks = new Supplier("Nicolas Sparks", "Has cultural charity foundation");
//        Supplier rowling = new Supplier("J. K. Rowling", "Loved by fans around the world");
//        Supplier amazon = new Supplier("Amazon", "Digital content and services");
//        Supplier asus = new Supplier("ASUS", "Wiser together");
//        Supplier lenovo = new Supplier("Lenovo", "Computers");
//
//        supplierDataStore.addLineItem(amazon);
//        supplierDataStore.addLineItem(lenovo);
//        supplierDataStore.addLineItem(martin);
//        supplierDataStore.addLineItem(shaefer);
//        supplierDataStore.addLineItem(steel);
//        supplierDataStore.addLineItem(sparks);
//        supplierDataStore.addLineItem(rowling);
//        supplierDataStore.addLineItem(asus);
//
//
//        //setting up a new product category
//        ProductCategory tablet = new ProductCategory("Tablet", "Electronics", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
//        ProductCategory laptop = new ProductCategory("Laptop", "Electronics", "Portable PC.");
//        ProductCategory router = new ProductCategory("Router", "Electronics", "Makes possible watching Netflix in toilet.");
//        ProductCategory sf = new ProductCategory("S-F", "Books", "Books about not-reality");
//        ProductCategory romance = new ProductCategory("Romance", "Books", "Books about love you will never have.");
//        ProductCategory teens = new ProductCategory("Teens", "Books", "Books for children and teens.");
//
//        productCategoryDataStore.addLineItem(tablet);
//        productCategoryDataStore.addLineItem(laptop);
//        productCategoryDataStore.addLineItem(router);
//        productCategoryDataStore.addLineItem(sf);
//        productCategoryDataStore.addLineItem(romance);
//        productCategoryDataStore.addLineItem(teens);
//
//        //setting up products and printing it
//        productDataStore.addLineItem(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
//        productDataStore.addLineItem(new Product("Lenovo IdeaPad Mix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
//        productDataStore.addLineItem(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
//        productDataStore.addLineItem(new Product("A Game of Thrones", 6.62f, "USD", "Dragons, love and incest", sf, martin));
//        productDataStore.addLineItem(new Product("Fire and Blood", 16.73f, "USD", "A Targaryen History", sf, martin));
//        productDataStore.addLineItem(new Product("Ghosts of Gotham", 5.99f, "USD", "Mystery and magic", sf, shaefer));
//        productDataStore.addLineItem(new Product("Lost and Found", 2.66f, "USD", "Love novel", romance, steel));
//        productDataStore.addLineItem(new Product("Fairytale", 2.99f, "USD", "Love novel again", romance, steel));
//        productDataStore.addLineItem(new Product("The Notebook", 7.88f, "USD", "La La Land was based on this", romance, sparks));
//        productDataStore.addLineItem(new Product("See me", 8.16f, "USD", "Rich in emotion and fueled with suspense", romance, sparks));
//        productDataStore.addLineItem(new Product("Harry Potter", 69.15f, "USD", "The Illustrated Collection", teens, rowling));
//        productDataStore.addLineItem(new Product("ASUS Vivobook K570ZD", 493.37f, "USD", "Full Hd", laptop, asus));
//        productDataStore.addLineItem(new Product("ASUS Whole Home Dual-Band AiMesh Router", 134.99f, "USD", "Mesh Wifi System", router, asus));
//        productDataStore.addLineItem(new Product("ASUS C302CA-DHM4 Chromebook Flip", 465.00f, "USD", "12.5-inch Touchscreen", laptop, asus));
//
//    }
//}
