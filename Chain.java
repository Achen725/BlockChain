import java.util.*;
import java.security.*;
import com.google.gson.GsonBuilder;
//need to import library, but alternatively you can use a toString method to display data more neatly

public class Chain{

public static ArrayList<Block> blockchain = new ArrayList<Block>();
public static int diff = 3;
public static double min = 0.1;

public static Transaction gen;
	public static void main(String args[]){
	
	/* Demo: HashGen example
	 * A difference in even one character changes the return
	 */
		
    System.out.println(HashGenerator.encrypt("Hello World"));
	System.out.println(HashGenerator.encrypt("Hello World?"));
	
	/*
	 * G0 Block
	 */
		 blockchain.add(new Block("Andy is Awesome", "0"));
	     blockchain.get(0).mineBlock(diff);   
		  
	/* 
	 * Blockchain Example:
	 */
	
		Chain.repMine(20);	    
		String chain = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		 
		System.out.println(chain);

		
	/*
	 * Creating A Wallet

		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
		AndysWallet = new Wallet();
		wallet2 = new Wallet();
		bank = new Wallet();
		//System.out.println("Private Key and Public Key: "+ HelperMethods.getKeyString(wallet1.PrivAddress) + " and "+HelperMethods.getKeyString(wallet1.PubAddress));
    
		Transaction transaction = new Transaction(AndysWallet.PubAddress, wallet2.PubAddress, null, 5 );
		transaction.genSig(AndysWallet.PrivAddress);
				
		//System.out.println("Signature Verification: "+ transaction.verify());
	
	/*Demo: Cryptocurrency
	 * G0 Block

		
		gen = new Transaction(bank.PubAddress, AndysWallet.PubAddress, null,100);
		gen.genSig(bank.PrivAddress);
		gen.transHash = "0";
		gen.outputs.add(new TransOutput(gen.receiver, gen.amount, gen.transHash)); 
		UTXOs.put(gen.outputs.get(0).ID, gen.outputs.get(0));
		
		Coin genesis = new Coin("0");
		genesis.addTrans(gen);
		addBlock(genesis);
	*/	
		
	/*
	 * Test Transaction
	 */
		
	//	System.out.println(AndysWallet.getBalance());
		
	//	Coin next = new Coin(currency.get(currency.size()-1).hash);
	//	next.addTrans(AndysWallet.sendFunds( wallet2.PubAddress, 70));
		
	//	System.out.println(AndysWallet.getBalance());

	}
	
	public static void repMine(int rep) { 
		Random rand = new Random();
		int num = rand.nextInt(1000) + 1;
		int count = 0;
		for(int i=0; i<rep; i++){
			if(count == 10) {
				count = 0;
				diff+=1;
			}
			blockchain.add(new Block(RandString.getRandString(num),blockchain.get(blockchain.size()-1).getHash()));
			blockchain.get(blockchain.size()-1).mineBlock(diff);
			count++;
		}		  	  
	  }
   
	public static boolean check() {
				
		Block current;
		Block previous;
		
		for(int i=1; i<blockchain.size(); i++ ) {
			current = blockchain.get(i);
			previous = blockchain.get(i-1);
			if(!current.getHash().equals(current.makeHash())) {
				//System.out.println("False");
				return false;
			}
			if(!previous.getHash().equals(current.getPreHash()) ) {
				//System.out.println("False");
				return false;
			}
			if(!current.getMined()) {
				System.out.println("Block has not been mined yet");
				return false;
			}
		}
		if(!blockchain.get(0).getMined()) {
			System.out.println("Block has not been mined yet");
			return false;
		}
		return true;
	}
    /*
	public static Boolean checkC() {
		Coin currentBlock; 
		Coin previousBlock;
		String targetHash = new String(new char[diff]).replace('\0', '0');
		HashMap<String,TransOutput> temp = new HashMap<String,TransOutput>();
		temp.put(gen.outputs.get(0).ID, gen.outputs.get(0));
		
		for(int i=1; i < currency.size(); i++) {
			currentBlock = currency.get(i);
			previousBlock = currency.get(i-1);
			if(!currentBlock.hash.equals(currentBlock.makeHash()) ){
				return false;
			}
			if(!previousBlock.hash.equals(currentBlock.preHash) ) {
				return false;
			}
			if(!currentBlock.hash.substring( 0, diff).equals(targetHash)) {
				return false;
			}
			TransOutput tempOutput;
			for(int j = 0; j <currentBlock.transactions.size(); j++) {
				Transaction currentTransaction = currentBlock.transactions.get(j);
				if(!currentTransaction.verify()) {
					return false; 
				}
				if(currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					return false; 
				}
				
				for(TransInput input: currentTransaction.inputs) {	
					tempOutput = temp.get(input.outputID);
					
					if(tempOutput == null)return false;
					if(input.UTXO.amount != tempOutput.amount)return false;
	
					temp.remove(input.outputID);
				}
				
				for(TransOutput output: currentTransaction.outputs) {
					temp.put(output.ID, output);
				}
				
				if( currentTransaction.outputs.get(0).receiver != currentTransaction.receiver) {
					return false;
				}
				if( currentTransaction.outputs.get(1).receiver != currentTransaction.sender) {
					return false;
				}
			}
		}
		return true;
	}
	
	
    */
}

