package model;

import java.time.LocalDateTime;

public class RememberMeToken {
    private int TokenId;
    private int UserId;
    private String Username;
    private String Token;
    private LocalDateTime Expires_At;

    public RememberMeToken(int TokenId, int UserId, String Username, String Token, LocalDateTime Expires_At) {
        this.TokenId = TokenId;
        this.UserId = UserId;
        this.Username = Username;
        this.Token = Token;
        this.Expires_At = Expires_At;
    }

    public int getTokenId() {
        return TokenId;
    }

    public void setTokenId(int tokenId) {
        TokenId = tokenId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public LocalDateTime getExpires_At() {
        return Expires_At;
    }

    public void setExpires_At(LocalDateTime expires_At) {
        Expires_At = expires_At;
    }
}
