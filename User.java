/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffieHellman;

import java.math.BigInteger;
import java.util.Random;

/**
 * User class to use for demonstrating DHKE
 *
 * @author Sebastian Brann-Singer
 */
public class User {

    private BigInteger privateKey,
            publicKey, publicBase,
            givenKeyPair, receivedKeyPair,
            sharedSecretKey;

    //Constructor which generates a private key
    User(BigInteger publicKey, BigInteger publicBase) {
        this.publicKey = publicKey;
        this.publicBase = publicBase;
        generatePrivateKey();
    }

    //creates random private key between 0 - publicKey
    private void generatePrivateKey() {
        Random rand = new Random(); //get random seed
        //System.out.println(publicKey.bitCount());
        //random BigInteger with max number of bits equal to number of bits of public key
        privateKey = new BigInteger(publicKey.bitCount(), rand);
    }

    //creates shared secret key
    private void setSharedSecretKey() {
        //raise public key pair from other user by private key
        //use modulo public key to get shared sercret key
        //receivedKeyPair^privateKey % publicKey
        sharedSecretKey = receivedKeyPair.modPow(privateKey, publicKey);
        //both users should have the same shared key
    }

    //creates public key pair to send to other user
    public BigInteger createPublicKeyPair() {
        //raise public prime base by private key
        //then does modulo with public key to get a public key pair
        //publicBase^privateKey % publicKey
        givenKeyPair = publicBase.modPow(privateKey, publicKey);
        return givenKeyPair;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public void setPublicBase(BigInteger publicBase) {
        this.publicBase = publicBase;
    }

    public BigInteger getPublicBase() {
        return publicBase;
    }

    public void setReceivedKeyPair(BigInteger receivedKeyPair) {
        this.receivedKeyPair = receivedKeyPair;
        setSharedSecretKey();
    }

    public BigInteger getGivenKeyPair() {
        return givenKeyPair;
    }

//    public void sendKeyPair(User matchedUser) {
//        if (!publicKey.equals(matchedUser.getPublicKey())) { //if users have differnet public keys (dont match)
//            return;
//        }
//
//    }
    public String encryptMessage(String unencryptedMessage) {
        return AESEncryption.encrypt(unencryptedMessage, sharedSecretKey.toString());
    }

    public String decryptMessage(String encryptedMessage) {
        return AESEncryption.decrypt(encryptedMessage, sharedSecretKey.toString());
    }

}
