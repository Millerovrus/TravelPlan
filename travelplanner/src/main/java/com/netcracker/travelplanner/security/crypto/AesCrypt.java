package com.netcracker.travelplanner.security.crypto;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCrypt {

    private static final Logger logger = LoggerFactory.getLogger(AesCrypt.class);

    //128 bit key
    private static String key = "ItIsOurBigSecret";

    //16 bytes init vector
    private static String initVector = "RandomInitVector";

    public static String encrypt(String plainText){
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            //Получение экземпляра алгоритма шифрования
            //CBC - режим сцепления блоков шифротекста
            //PKCS5Padding - алгоритм дополнения последних блоков до нужного количества бит (128 бит)
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            //logger.debug("{} was encrypted to: {}", plainText,  Base64.encodeBase64String(encrypted));
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            //Получение экземпляра алгоритма шифрования
            //CBC - режим сцепления блоков шифротекста
            //PKCS5Padding - алгоритм дополнения последних блоков до нужного количества бит (128 бит)
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            //logger.debug("{} was decrypted to: {}", encrypted, new String(original));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
