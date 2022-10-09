package com.gasull.carlos.DiceGame.entity;

public class Login {

    private Long idPlayer;
    private String password;

    public Login() {
    }

    public Login(Long idPlayer, String password) {
        this.idPlayer = idPlayer;
        this.password = password;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public String getPassword() {
        return password;
    }
}
