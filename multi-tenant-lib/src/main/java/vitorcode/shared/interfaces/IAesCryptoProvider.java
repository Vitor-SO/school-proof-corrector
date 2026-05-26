package vitorcode.shared.interfaces;

public interface IAesCryptoProvider {
    String encrypt(String plainText);
    String decrypt(String cipherText);
}

