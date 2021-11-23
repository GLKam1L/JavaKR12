package ru.mirea.tourismapp.domain;


import lombok.Data;

@Data
public class SubsesForm {
    private String subses;

    public User toSubses(User user){
        String buffer = user.getSubses() + subses + ",";
        user.setSubses(buffer);
        return user;
    }
}
