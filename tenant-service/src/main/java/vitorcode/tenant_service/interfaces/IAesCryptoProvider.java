package vitorcode.tenant_service.interfaces;

public interface IAesCryptoProvider {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}

