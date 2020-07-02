package com.malyshev2202.store.model;
//todo: add security to login page and client's controller  
public class Client {
    private Long client_id;
    private String nickname;
    private String email;
    private String password;
    private ClientRole role;

    public Client() {
    }

    public Client(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
}
