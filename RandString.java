
import java.util.Random;

public class RandString {
	
	public static String getRandString(int x) {
        String list = "abcdefghijklmonpqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*(){}[]?";
        StringBuilder newS = new StringBuilder();
        Random rand = new Random();
        while (newS.length() < x) 
        {
            int index = (int) (rand.nextFloat() * list.length());
            newS.append(list.charAt(index));
        }
        String saltStr = newS.toString();
        return saltStr;

    }
	

}
