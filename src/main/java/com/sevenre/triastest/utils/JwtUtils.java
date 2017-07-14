package com.sevenre.triastest.utils;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.sevenre.triastest.dto.Token;
import com.sevenre.triastest.dto.UserDto;
import com.sevenre.triastest.exceptions.UnauthorizedException;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Created by nikhilesh on 14/07/17.
 */
public class JwtUtils {

    public static String encode(String accessSecret, Map<String, Object> claims) {
        JWTSigner jwtSigner = new JWTSigner(accessSecret);
        return jwtSigner.sign(claims);
    }

    public static boolean verifyHeader(HttpHeaders headers) {
        List<String> auth = headers.get(Constants.authorizationHeaderName);
        if (auth.isEmpty()) {
            throw new UnauthorizedException("Empty Header");
        } else {
            String token = auth.get(0);
            decodeAccessToken(Constants.SECRET_KEY, token);
            return true;
        }
    }

    private static Token decodeAccessToken(String accessSecret, String token) {
        Token decodedAccessToken = decodeIdToken(token);
        verifySignature(accessSecret, token);

        return decodedAccessToken;
    }

    private static Token decodeIdToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new UnauthorizedException("Error, Null or empty token");
        }
        if (!token.contains(".")) {
            throw new UnauthorizedException("Invalid jwt token format");
        }

        String[] segments = token.split("\\.");
        if (segments.length != 3) {
            throw new UnauthorizedException("Invalid token format");
        }

        String payload = segments[1];
        Token decodedToken = new Token();
        decodedToken.setPayload(payload);
        decodedToken.setToken(token);
        ObjectMapper mapper = new ObjectMapper();
        try {
            UserDto authPayload = mapper.readValue(decodeFromBase64(payload), UserDto.class);
            if (authPayload == null) {
                throw new UnauthorizedException("Invalid token, Payload is null");
            }
            decodedToken.setAuthPayload(authPayload);
        } catch (IOException e) {
            throw new UnauthorizedException("Error in reading payload, contact admin");
        }
        return decodedToken;
    }

    private static void verifySignature(String accessSecret, String token) {
        try {
            final JWTVerifier verifier = new JWTVerifier(accessSecret);
            try {
                verifier.verify(token);
            } catch (NoSuchAlgorithmException e) {
                throw new UnauthorizedException(e.getLocalizedMessage());
            } catch (InvalidKeyException e) {
                throw new UnauthorizedException(e.getLocalizedMessage());
            } catch (IOException e) {
                throw new UnauthorizedException(e.getLocalizedMessage());
            } catch (SignatureException e) {
                throw new UnauthorizedException(e.getLocalizedMessage());
            }
        } catch (JWTVerifyException e) {
            throw new UnauthorizedException(e.getLocalizedMessage());
        }
    }

    private static String decodeFromBase64(String value) {
        byte[] valueDecoded = Base64.getDecoder().decode(value);
        System.out.println("Decoded value is " + new String(valueDecoded));
        return new String(valueDecoded);
    }
}
