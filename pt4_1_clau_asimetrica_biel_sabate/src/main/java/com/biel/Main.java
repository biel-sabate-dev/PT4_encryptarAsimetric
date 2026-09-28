package com.biel;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

import javax.crypto.Cipher;

public class Main {
    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {
            int keyLenght = 0;
            String message = null;

            while (keyLenght < 2048 || keyLenght > 16385) {
                System.out.println("Introdueix la longitud de clau que vols: ");
                keyLenght = scanner.nextInt();
                scanner.nextLine();
                if (keyLenght < 2048 || keyLenght > 16384) {
                    System.out.println("La longitud tiene que ser igual o superior a 2048 pero igual o menor a 16384...");
                    System.out.println("");
                }
            }
            System.out.println("Introdueix el missatge a xifrar: ");
            message = scanner.nextLine();

            System.out.println("Generant claus...");
            KeyPair keys = keyGenerator(keyLenght);

            System.out.println("Xifrant el missatge...");
            byte[] encryptedData = encryptData(message.getBytes(), keys.getPublic());
            System.out.println("Dades xifrades: " + new String(encryptedData));
            
            System.out.println("Desxifrant el missatge...");
            byte[] desencryptedData = desencryptData(encryptedData, keys.getPrivate());
            System.out.println("Dades desxifrades: " + new String(desencryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static KeyPair keyGenerator(int lenght) {
        KeyPair keys = null;

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(lenght);
            keys = keyGen.genKeyPair();
        } catch (Exception e) {
            System.err.println("Error al generar la clave...");
        }

        return keys;
    }

    public static byte[] encryptData(byte[] data, PublicKey inKey) {
        byte[] encryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.ENCRYPT_MODE, inKey);
            encryptedData = cipher.doFinal(data);
        } catch (Exception e) {
            System.err.println("Error al encriptar les dades...");
        }

        return encryptedData;
    }

    public static byte[] desencryptData(byte[] dataEncrypted, PrivateKey outKey) {
        byte[] desencryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, outKey);
            desencryptedData = cipher.doFinal(dataEncrypted);
        } catch (Exception e) {
            System.err.println("Error al desencriptar les dades...");
        }

        return desencryptedData;
    }
}