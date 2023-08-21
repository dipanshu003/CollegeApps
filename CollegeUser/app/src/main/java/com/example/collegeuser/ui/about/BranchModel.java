package com.example.collegeuser.ui.about;

public class BranchModel {
   // private int img;
    private String title,description;

    public BranchModel( String title, String description) {
       // this.img = img;
        this.title = title;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
