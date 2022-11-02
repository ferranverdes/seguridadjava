package com.luis.intro;

import java.util.ArrayList;
import java.util.Random;


public class Product {
    private String name;
    private float price;
    private int stock;
    
    public Product(String name, float price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ID Producto: " + name + ", Precio: " + price +", Stock: "+ stock; 
    }
    
    public static ArrayList<Product> randomProducts(int total) {
        Random rnd = new Random();
        ArrayList<Product> products = new ArrayList();
        for(int i = 0; i < total; i++) {
            Product p = new Product("Prod_"+i, rnd.nextFloat(), rnd.nextInt() & Integer.MAX_VALUE/10);
            products.add(p);
        }
        return products;
    }
    
}
