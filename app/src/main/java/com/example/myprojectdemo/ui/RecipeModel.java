package com.example.myprojectdemo.ui;

public class RecipeModel {
    public String name;
    public String description;
    public String ingredint;
     public int id;
     public byte[] image;

    public String getIngredint() {
        return ingredint;
    }

    public void setIngredint(String ingredinet) {
        this.ingredint = ingredinet;
    }


    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }
    public RecipeModel(){}

    public RecipeModel(int id,String name, String description,byte[] image,String ingredint) {
        this.id=id;
        this.name = name;
        this.description = description;
        this.image=image;
        this.ingredint=ingredint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
