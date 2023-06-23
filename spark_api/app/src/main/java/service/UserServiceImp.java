/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import domain.User;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author lordyhas
 */
public class UserServiceImp implements UserService {
    private HashMap<Integer, User> userData;

    public UserServiceImp() {
        this.userData = new HashMap<>();
    }
    
    @Override
    public void addUser(User user) {
         userData.put(user.getId(), user);
    }

    @Override
    public Collection<User> getUsers() {
        return userData.values();
    }

    @Override
    public User getUser(int id) {
        return userData.get(id);
    }

    @Override
    public void deleteUser(int id) {
        userData.remove(id);
    }

    @Override
    public boolean userExist(int id) {
        return userData.containsKey(id);
    }

    @Override
    public User editUser(User user) throws UserException {
        try{
            if(user.getId() == null) throw new UserException("ID can't be null");
            User toEdit = userData.get(user.getId());
            if(toEdit == null) throw new UserException("User not found");
            if(user.getEmail() != null){
                toEdit.setEmail(user.getEmail());
            }
            if(user.getName() != null){
                toEdit.setName(user.getName());
            }
            if(user.getId() != null){
                toEdit.setId(user.getId());
            }
            if(user.getHeight() != null){
                toEdit.setHeight(user.getHeight());
            }
            return toEdit;
        }catch(Exception e){
            throw new UserException(e.getMessage());
        }
        
    }
    
}
