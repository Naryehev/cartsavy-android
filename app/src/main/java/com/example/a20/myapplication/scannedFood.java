package com.example.a20.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by 20 on 7/5/2017.
 */


public class scannedFood implements Serializable {
    public String product_name_en;
    public String image_url;
    public String ingredients_text;

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String user;



    public String getProduct_name_en() {
        return product_name_en;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public scannedFood(String product_name_en, String image_url, String ingredients_text, String id, String user) {
        this.product_name_en = product_name_en;
        this.image_url = image_url;
        this.ingredients_text = ingredients_text;
        this.id = id;
        this.user = user;
    }

    public scannedFood (){

    }
}
