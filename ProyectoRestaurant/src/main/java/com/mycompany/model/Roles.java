/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author Fabrizio
 */
public class Roles {
    private int id;
    private String trol;

    public Roles(String trol) {
        this.trol = trol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrol() {
        return trol;
    }

    public void setTrol(String trol) {
        this.trol = trol;
    }
    
    
}
