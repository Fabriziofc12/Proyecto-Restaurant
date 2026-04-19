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
    private String rol;

    public Roles(String trol) {
        this.rol = trol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrol() {
        return rol;
    }

    public void setTrol(String trol) {
        this.rol = trol;
    }
    
    
}
