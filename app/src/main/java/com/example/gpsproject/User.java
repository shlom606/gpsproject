package com.example.gpsproject;


public class User {

    String firstName, lastName, age, userName;
    Boolean jedi,sith;


    public User(String firstName, String lastName, String age, String userName,Boolean sith,Boolean jedi) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userName = userName;
        this.sith=sith;
        this.jedi=jedi;

    }
    public Boolean getJedi(){
        return this.jedi;
    }
    public Boolean getSith(){
        return this.sith;
    }
    public void setJedi(){
        this.jedi=jedi;
    }
    public void setSith(){
        this.sith=sith;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}