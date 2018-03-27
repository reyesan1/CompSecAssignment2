package cs4351;
import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Verify {

    public static void main(String[] args) {
        // Written by Luc Longpre for Computer Security, Spring 2018        
        File file;
        PublicKey pubKey;
        String signature;
        String messageSigned = "Alexis Reyes 03/22/2018";
        
        System.out.println("Verifying the signature of: \""+messageSigned+"\"");

        // Read public key from file
        pubKey = PemUtils.readPublicKey("AlexisClientSignPublicKey.pem");

        // Read signature from file
        try {
            file = new File("signature.txt");
            Scanner input = new Scanner(file);
            signature = input.nextLine();
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not open signature file: " + ex);
            return;
        }
        
        if (verify(pubKey, messageSigned.getBytes(), Base64.getDecoder().decode(signature)))
            System.out.println("Signature verification succeeded");
        else
            System.out.println("Signature verification failed");                            
    }

    public static boolean verify(PublicKey pubKey, byte[] message, byte[] signature) {
        // Written by Luc Longpre for Computer Security, Spring 2018
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(pubKey);
            sig.update(message);
            return sig.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            System.out.println("problem verifying signature: " + e);
        }
        return false;
    }
}
