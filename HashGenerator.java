//import javax.crypto.spec.*;
import java.security.*;
//import javax.crypto.*;
//import java.lang.Object.*;

  public class HashGenerator{
    public static String encrypt(String input){
  		try {
  			MessageDigest digest = MessageDigest.getInstance("SHA-256");
  			byte[] hash = digest.digest(input.getBytes("UTF-8"));
  			StringBuffer hexString = new StringBuffer(); 
  			for (int i = 0; i < hash.length; i++) {
  				String hex = Integer.toHexString(0xff & hash[i]);
  				if(hex.length() == 1) hexString.append('0');
  				hexString.append(hex);
  			}
  			return hexString.toString();
  		}
  		catch(Exception e) {
  			throw new RuntimeException(e);
  		}
  	}
  }
// Adapted from https://stackoverflow.com/questions/5531455/how-to-hash-some-string-with-sha256-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa