package com.example.mailServer.mail.services.AuthoriticationProxy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

public class Encryption {
    private static String bytesToHex(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32)
            hexString.insert(0, '0');
        return hexString.toString();
    }

    public static String getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
    }

}
