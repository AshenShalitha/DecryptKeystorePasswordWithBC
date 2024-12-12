package org.example;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import org.json.JSONObject;

public class DecryptWithKeystoreBC {

    public static void main(String[] args) throws Exception {
        // Base64-encoded password and keystore info
        String password = "eyJjIjoiWVRsQ1F2YlNHZUdmN0VuYnF0RTFkTGJTSjk1VzhDdE11OTI2ajZTcWFKQURWbWJuMzBodEhtQUhEeGhWQi8yL3RycFpvVlBsQXR4YXgrbmdGRkgzQTRjUUhBUnowRkhNTm4vVHRxU3JEQkQ4YkxoS2F5U0x2QlhKRm5xdEY1K0lJKzUyczAva3c4bTE4b1AreW1VRlQwNG5OSGF3Tk1ua3ozSWo4cVpnQ2VNT1FKd09BckFpZ0NqQ21KUzd2eEY5akNpNjY3ZWExWjZwTFZoWTNKZ2VPMzl2U0Z4T1lrODA0NVFGMU1ZUncrWDdKRVF6QUFpUGNOWlBIRktsVTJxL2k2Q3cwdHpUMlVsd3RRcG1GUGNzcjZSd0ZUMGoxZ01qek9welpMamp0RUdJL0VEeFhCTWpFRGpNc3haUmNBTndaSUkwOGZzdVVXUjA0VGFiMlRaVkdnXHUwMDNkXHUwMDNkIiwidCI6IlJTQS9FQ0IvT0FFUHdpdGhTSEExYW5kTUdGMVBhZGRpbmciLCJ0cCI6IjU3RkYzOEQ5NzY2NEM3OTJGRjg4MDExNzFGMDQxOTFERUQ4ODc3OEQiLCJ0cGQiOiJTSEEtMSJ9";
        String keystorePath = "/Users/ashens/Work/Products/U2/wso2am-4.0.0.298/repository/resources/security/wso2carbon.jks";
        String keystorePassword = "wso2carbon";
        String keyAlias = "wso2carbon";
        String keyPassword = "wso2carbon";

        // Decode the Base64 string
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String jsonString = new String(decodedBytes);

        // Parse the JSON string
        JSONObject jsonObject = new JSONObject(jsonString);

        // Extract values
        String Ciphertext = jsonObject.getString("c");
        String t = jsonObject.getString("t");

        // Add Bouncy Castle as a security provider
        Security.addProvider(new BouncyCastleProvider());

        // Load the keystore
        FileInputStream keystoreInput = new FileInputStream(keystorePath);
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(keystoreInput, keystorePassword.toCharArray());

        // Retrieve the private key
        PrivateKey privateKey = (PrivateKey) keystore.getKey(keyAlias, keyPassword.toCharArray());
        if (privateKey == null) {
            throw new Exception("Private key not found for alias: " + keyAlias);
        }

        // Decode the Base64 ciphertext
        byte[] ciphertextBytes = Base64.getDecoder().decode(Ciphertext);

        // Initialize the RSA cipher with OAEP padding using Bouncy Castle
        Cipher cipher = Cipher.getInstance(t, "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Decrypt the ciphertext
        byte[] plaintextBytes = cipher.doFinal(ciphertextBytes);

        // Convert plaintext bytes to a string
        String plaintext = new String(plaintextBytes, "UTF-8");

        // Print the decrypted plaintext
        System.out.println("Decrypted plaintext: " + plaintext);
    }
}