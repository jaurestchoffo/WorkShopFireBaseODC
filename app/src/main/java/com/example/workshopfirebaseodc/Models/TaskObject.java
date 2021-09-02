package com.example.workshopfirebaseodc.Models;

import com.google.firebase.firestore.Exclude;

public class TaskObject {

    /* privae attibut*/

    private String name;
    private String description;
    private Boolean status;
    @Exclude private String  id;


    public TaskObject(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = false;
    }

    public TaskObject(String Id, String name, String description, Boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = Id;
    }


    public TaskObject(){

    }

    @Exclude public String getId() { return id;}

    public void setId(String id) {this.id = id;}
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

