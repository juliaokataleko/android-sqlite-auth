package com.kataleko.androidSQLiteAuth.models;

public class User {
    private int id, status, user_type;
    private String login, name, photo, email, phone, address, pass, mother_name,
            father_name, birth_place, birthday, gender, created_at,
            updated_at;

    public User(int id, String login,
                String name, String photo, String email,
                String pass, String mother_name,
                String father_name, String birth_place,
                String birthday, String gender,
                String created_at, String updated_at,
                int status, int user_type) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.photo = photo;
        this.email = email;
        this.pass = pass;
        this.mother_name = mother_name;
        this.father_name = father_name;
        this.birth_place = birth_place;
        this.birthday = birthday;
        this.gender = gender;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.user_type = user_type;
    }

    public User(String login, String pass) {
        this.id = 0;
        this.login = login;
        this.name = "";
        this.photo = "";
        this.email = "";
        this.pass = pass;
        this.mother_name = "";
        this.father_name = "";
        this.birth_place = "";
        this.birthday = "";
        this.gender = "";
        this.created_at = "";
        this.updated_at = "";
        this.status = 1;
        this.user_type = 4;
    }

    public User(int id, String login, String pass) {
        this.id = id;
        this.login = login;
        this.name = "";
        this.photo = "";
        this.email = "";
        this.pass = pass;
        this.mother_name = "";
        this.father_name = "";
        this.birth_place = "";
        this.birthday = "";
        this.gender = "";
        this.created_at = "";
        this.updated_at = "";
        this.status = 1;
        this.user_type = 4;
    }

    public User(int id) {
        this.id = id;
        this.login = "";
        this.name = "";
        this.photo = "";
        this.email = "";
        this.pass = "";
        this.mother_name = "";
        this.father_name = "";
        this.birth_place = "";
        this.birthday = "";
        this.gender = "";
        this.created_at = "";
        this.updated_at = "";
        this.status = 1;
        this.user_type = 4;
    }

    public User() {
        this.id = 0;
        this.login = "";
        this.name = "";
        this.photo = "";
        this.email = "";
        this.pass = "";
        this.mother_name = "";
        this.father_name = "";
        this.birth_place = "";
        this.birthday = "";
        this.gender = "";
        this.created_at = "";
        this.updated_at = "";
        this.status = 1;
        this.user_type = 4;
    }

    public User(User u) {
        this.id = u.id;
        this.login = u.login;
        this.name = u.name;
        this.photo = u.photo;
        this.email = u.email;
        this.pass = u.pass;
        this.mother_name = u.mother_name;
        this.father_name = u.father_name;
        this.birth_place = u.birth_place;
        this.birthday = u.birthday;
        this.gender = u.gender;
        this.created_at = u.created_at;
        this.updated_at = u.updated_at;
        this.status = u.status;
        this.user_type = u.user_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
