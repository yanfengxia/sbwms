package com.beta.ms.user.bridge;

import com.beta.ms.user.notion.Token;
import org.springframework.stereotype.Component;

@Component
public class TokenBridge {
    public Token convertTokenToTokenRO(com.beta.ms.user.model.Token token) {
        return Token.builder()
                .token(token.getToken())
                .expiretime(token.getExpiryTime())
                .expiryin(token.getExpiryIn())
                .build();
    }
}
