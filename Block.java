import java.util.*;

public class Block {
  public String hash;
  public String preHash;
  private long timeStamp;
  private String data;
  private int nonce;
  private boolean isMined;
  //private long bits;
  //private long version;
  
  /*
  	Referenced from: https://en.bitcoin.it/wiki/Block_hashing_algorithm
  */

  public Block(String data, String preHash){
    this.data = data;
    this.preHash = preHash;
    this.timeStamp = new Date().getTime();
    this.hash = makeHash();
    isMined = false;
  }
  //for genesis
  public Block(String hash, String data, String preHash) {
	   
	  this.timeStamp = new Date().getTime();
	  this.hash = hash;
	  this.data = data;
	  this.preHash = preHash;
	  isMined = false;
  }
  public String makeHash(){
    String calculatedHash = HashGenerator.encrypt(preHash + Long.toString(timeStamp) + Integer.toString(nonce) + data);
    return calculatedHash;
  }
  public void mineBlock(int diff) {
	  String target = Block.repeat("0", diff);
	  while(!hash.substring(0,diff).equals(target)) {
		  System.out.println(hash);
		  hash = makeHash();
		  nonce++;
	  }
	  isMined = true;
	  System.out.println("Mined: "+ hash);
  }
  public boolean isValid(Block b, int diff){
	  if(b.getHash().substring(0,diff).equals(Block.repeat("0",diff))) {
		  System.out.println(b.getHash());
		  return true;
	  }
	  return false;
  }
  public static String repeat(String s, int i){
	  
	  String newS = s;
	  for(int x = 0; x < i-1; x++) {
		  newS+=s;
	  }
	  return newS;
  }
  public boolean getMined() {
	  return isMined;
  }
  public String getHash(){
    return hash;
  }
  public String getPreHash(){
    return preHash;
  }
  public long getTime(){
    return timeStamp;
  }
}
