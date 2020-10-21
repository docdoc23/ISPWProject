package main.java.entity;

import java.util.List;

public class User {
    private String nickname;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Gender gender;
    private List<Research> favorite;
    private RoomAnnounce roomRented; // Observed state

    public User(String nickname, String name, String surname, String email, String password) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roomRented= null;
    }

    public User() {

    }

    public RoomAnnounce getRoomRented() {
        return roomRented;
    }

    public void setRoomRented(RoomAnnounce roomRented) {
        this.roomRented = roomRented;
        if (roomRented == null) {

        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        if (gender == 'M' || gender == 'm') {
            this.gender = Gender.male;
        }
        if (gender == 'F' || gender == 'f') {
            this.gender = Gender.female;
        }
    }

    public List<Research> getFavorite() {
        return favorite;
    }

    public void setFavorite(List<Research> favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "----- User -----\nname: " + this.name + "\nsurname: " + this.surname + "\n";
    }

    @Override
    public boolean equals(Object o) {
        return this.nickname.equals(((User) o).getNickname()) &&
                this.name.equals(((User) o).getName()) &&
                this.surname.equals(((User) o).getSurname()) &&
                this.email.equals(((User) o).getEmail()) &&
                this.gender.equals(((User) o).getGender());
    }
}





