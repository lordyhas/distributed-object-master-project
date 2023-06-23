/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import domain.User;
import java.util.Collection;

/**
 *
 * @author lordyhas
 */
public interface UserService {
    
    public void addUser(User user);
    public Collection<User> getUsers();
    public User getUser(int id);
    public void deleteUser(int id);
    public boolean userExist(int id);
    public User editUser(User user) throws UserException;

    
}
