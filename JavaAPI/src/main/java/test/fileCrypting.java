package test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class fileCrypting {
	static String encryptedBase64;
	static byte[] secretKeyBytes;
	
	@GET 
	@Produces(MediaType.TEXT_HTML)
	@Path("encrypt")
	public static void printEncrypt(@QueryParam("dir") String dirName, @QueryParam("file") String fileName) throws Exception {
        fileCrypting mainApp = new fileCrypting();
        java.nio.file.Path sourceDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
		List<String> message = Files.readAllLines(sourceDir);
        
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Algorithm \t: AES with ECB (Electronic Codebook) block mode");
        secretKeyBytes = mainApp.generateSecretKeyBytes();
        System.out.println("Key Length \t: " + (secretKeyBytes.length * 8) + " bits");
        System.out.println("-----------------------------------------------------------------");
        
        String plainText = String.join(" ", message);
        
        System.out.println("Secret Key Bytes: " +secretKeyBytes);
        System.out.println("Plain Text \t: " + plainText);
        
        encryptedBase64 = mainApp.encrypt(plainText, secretKeyBytes);
        System.out.println("Encrypted Text \t: " + encryptedBase64);
        

        Writer output = null;
        File file = new File(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
        output = new BufferedWriter(new FileWriter(file));
        output.write(encryptedBase64);
        output.close();
    }
	
	private String encrypt(String plainText, byte[] secretKeyBytes) throws Exception{
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
        
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        
        String encryptedMesssageBase64 =  Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedMesssageBase64;
    }
	
	@GET 
	@Produces(MediaType.TEXT_HTML)
	@Path("decrypt")
	public static void printDecrypt(@QueryParam("dir") String dirName, @QueryParam("file") String fileName) throws Exception {
		fileCrypting mainApp = new fileCrypting();
		java.nio.file.Path sourceDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
		List<String> message = Files.readAllLines(sourceDir);
        
        System.out.println("-----------------------------------------------------------------");
        System.out.println("Algorithm \t: AES with ECB (Electronic Codebook) block mode");
        System.out.println("Key Length \t: " + (secretKeyBytes.length * 8) + " bits");
        System.out.println("-----------------------------------------------------------------");
        
        String plainText = String.join(" ", message);
        
        System.out.println("Secret Key Bytes: " +secretKeyBytes);
        System.out.println("Encrypted Text \t: " + plainText);
            
        String decryptedText = mainApp.decrypt(encryptedBase64, secretKeyBytes);
        System.out.println("Decrypted Text \t: " + decryptedText);    
        
        Writer output = null;
        File file = new File(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
        output = new BufferedWriter(new FileWriter(file));
        output.write(decryptedText);
        output.close();
	}
	
    private String decrypt(String encryptedBase64, byte[] secretKeyBytes) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedText = new String(decryptedBytes, "UTF-8");
        return decryptedText;
    }
    
    private byte[] generateSecretKeyBytes() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }
}