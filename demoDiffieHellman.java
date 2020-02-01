/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffieHellman;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Demo program for Diffie-Hellman Key Exchange Presentation in CIT-242-01
 *
 * @author Sebastian Brann-Singer 11/21/19
 */
public class demoDiffieHellman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("This program demonstrates the Diffie-Hellman Key Exchange to "
                + "\npass secret messages from one user to another\n");

        Scanner input = new Scanner(System.in);
        int userChoice;

        BigInteger publicGenerator = null; //public generator used for modulo (prime)
        BigInteger publicBase = null; //base to raise by private key (primitive root of generator)

        BigInteger secondOakleyGenerator = new BigInteger(
                "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD1"
                + "29024E088A67CC74020BBEA63B139B22514A08798E3404DD"
                + "EF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245"
                + "E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7ED"
                + "EE386BFB5A899FA5AE9F24117C4B1FE649286651ECE65381"
                + "FFFFFFFFFFFFFFFF", 16); //radix 16 denotes the number format is hexadecimal
        //defaut radix is base 10
        BigInteger secondOakleyBase = new BigInteger("2");

        BigInteger exGenerator1 = new BigInteger("7");
        BigInteger exBase1 = new BigInteger("3");

        BigInteger exGenerator2 = new BigInteger("23");
        BigInteger exBase2 = new BigInteger("5");

        //menu for choosing which generator to use
        System.out.println("Which public generator and base would you like to use?");
        System.out.println("1. Extremely Low Numbers"
                + "\n2. Low Numbers"
                + "\n3. Second Oakley Numbers (1024 bit generator)");
        userChoice = Integer.parseInt(input.nextLine());
        switch (userChoice) {
            case (1):
                publicGenerator = exGenerator1;
                publicBase = exBase1;
                break;
            case (2):
                publicGenerator = exGenerator2;
                publicBase = exBase2;
                break;
            case (3):
                publicGenerator = secondOakleyGenerator;
                publicBase = secondOakleyBase;
                break;
        }

        //creates two users with same public generator and base
        System.out.println("This is the public key generator...");
        System.out.println(publicGenerator);
        System.out.println("This is liam's public base...");
        System.out.println(publicBase);
        User liam = new User(publicGenerator, publicBase);
        User charlotte = new User(publicGenerator, publicBase);

        //creates public key pair to send to one another
        System.out.println("This is liam's public key pair...");
        BigInteger liamsKeyPair = liam.createPublicKeyPair();
        System.out.println(liamsKeyPair);
        System.out.println("This is charlotte's public key pair...");
        BigInteger charlottesKeyPair = charlotte.createPublicKeyPair();
        System.out.println(charlottesKeyPair);

        System.out.println("Now transferring public keys to each other...");
        liam.setReceivedKeyPair(charlottesKeyPair);
        charlotte.setReceivedKeyPair(liamsKeyPair);

        System.out.println("Now enter a secret message to encrypt for Liam to send: ");
        String encryptedMessage = liam.encryptMessage(input.nextLine());
        String decryptedMessage;

        System.out.println("This is the message, now encrypted:");
        System.out.println(encryptedMessage);

        System.out.println("Charlotte now decrypting...");
        decryptedMessage = charlotte.decryptMessage(encryptedMessage);
        System.out.println("This is Liam's message, now decrypted:");
        System.out.println(decryptedMessage);

    }

}
