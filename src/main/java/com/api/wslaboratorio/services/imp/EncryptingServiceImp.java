package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EncriptadoDto;
import com.api.wslaboratorio.services.IEncryptingService;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptingServiceImp implements IEncryptingService {
    @Override
    public EncriptadoDto encriptarTexto(String texto) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String transformacion = "AES/ECB/PKCS5Padding";
        SecretKey claveSecreta;

        claveSecreta = generadorClaveSecreta();
        Cipher cipher = Cipher.getInstance(transformacion);
        cipher.init(Cipher.ENCRYPT_MODE, claveSecreta);

        byte[] encryptedBytes = cipher.doFinal(texto.getBytes(StandardCharsets.UTF_8));
        String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);
        String claveSecretaEncriptada = Base64.getEncoder().encodeToString(claveSecreta.getEncoded());

        return new EncriptadoDto(encryptedBase64, claveSecretaEncriptada);
    }

    @Override
    public String dencriptarTexto(String texto, String claveSecreta) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String algoritmo = "AES";
        String transformacion = "AES/ECB/PKCS5Padding";

        byte[] decodedKey = Base64.getDecoder().decode(claveSecreta);
        SecretKey claveSecretaOriginal = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        Cipher cipher = Cipher.getInstance(transformacion);
        cipher.init(Cipher.DECRYPT_MODE, claveSecretaOriginal);

        byte[] encriptarBytes = Base64.getDecoder().decode(texto);
        byte[] desencriptarBytes = cipher.doFinal(encriptarBytes);
        String textoDesencriptado = new String(desencriptarBytes, StandardCharsets.UTF_8);

        return textoDesencriptado;
    }

    private SecretKey generadorClaveSecreta() throws NoSuchAlgorithmException {
        String algoritmo = "AES";
        KeyGenerator generadorClave = KeyGenerator.getInstance(algoritmo);
        SecretKey claveSecreta = generadorClave.generateKey();

        return claveSecreta;
    }
}
