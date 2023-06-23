/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author lordyhas
 */
public class User {
    
    private Integer id;
    private String name;
    private String email;
    private Float height;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public User(Integer id, String name, String email, float height) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.height = height;
    }
    
    @Override
    public String toString(){
        return new StringBuffer().append(this.getEmail()).toString();
    }
    
    
    
}
