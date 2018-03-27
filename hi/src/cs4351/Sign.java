package cs4351;
import java.io.*;
import java.security.*;
import java.util.Base64;

class Sign {

    public static void main(String[] args) {
        // Written by Luc Longpre for Computer Security, Spring 2018
        
        File file;
        PrivateKey privKey;
        Signature sig;
        String messageToSign = "Alexis Reyes 03/22/2018";
        byte[] signature;
        
        System.out.println("Signing the message: \""+messageToSign+"\"");

        // Read private key from file
        privKey = PemUtils.readPrivateKey("AlexisServerSignPrivateKey.pem");
        
        signature = sign(privKey, messageToSign.getBytes());

        file = new File("signature.txt");
        try (PrintWriter output = new PrintWriter(file)) {
            output.print(Base64.getEncoder().encodeToString(signature));
        } catch (Exception e) {
            System.out.println("Could not create signature file");
        }
    }
    
    public static byte[] sign(PrivateKey privKey, byte[] bytes) {
        // Written by Luc Longpre for Computer Security, Spring 2018       
        Signature sig;
        byte[] signature;
        
        try {
            sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(privKey);
            sig.update(bytes);
            signature = sig.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            System.out.println("Error attempting to sign");
            return null;
        }
        return signature;
    }
}
