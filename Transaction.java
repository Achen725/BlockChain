import java.util.*;
import java.security.*;

public class Transaction {
	
	public String transHash;
	public double amount;
	public byte[] signature;
	public PublicKey sender;
	public PublicKey receiver;
	public ArrayList<TransInput> inputs = new ArrayList<TransInput>();
	public ArrayList<TransOutput> outputs = new ArrayList<TransOutput>();
	private static int seq;
	
	public Transaction(PublicKey sender, PublicKey to, ArrayList<TransInput> inputs, double val) {
		this.sender = sender;
		this.receiver = to;
		this.amount = val;
		this.inputs = inputs;
		this.transHash = makeHash();
	}
	public void genSig(PrivateKey key) {
		String s = HelperMethods.getKeyString(sender) + HelperMethods.getKeyString(receiver)+Double.toString(amount);
		signature = HelperMethods.applySig(key,s);
	}
	public boolean verify() {
		String s = HelperMethods.getKeyString(sender) + HelperMethods.getKeyString(receiver)+Double.toString(amount);
		return HelperMethods.verifySig(sender, s, signature);
	}
	public boolean checkTrans(){
		
		if(verify() == false) {
			return false;
		}
		for(TransInput t : inputs) {
			t.UTXO = Chain.UTXOs.get(t.outputID);
		}
		if(getInputsValue() < Chain.min) {
			return false;
		}
		double result = getInputsValue() - amount;
		transHash = makeHash();
		outputs.add(new TransOutput(this.receiver, amount, transHash));
		outputs.add(new TransOutput(this.sender, result, transHash));		
		for(TransOutput t : outputs) {
			Chain.UTXOs.put(t.ID, t);
		}
		for(TransInput i : inputs) {
			if(i.UTXO == null) continue;
			Chain.UTXOs.remove(i.UTXO.ID);
		}
		return true;
	}
	public String makeHash(){
		seq++;
	    String calculatedHash = HashGenerator.encrypt(Double.toString(amount)+ seq+ HelperMethods.getKeyString(sender)+HelperMethods.getKeyString(receiver));
	    return calculatedHash;
	}
	public double getInputsValue() {
		double num = 0;
		for(TransInput t : inputs) {
			if(t.UTXO == null) continue; 
			num += t.UTXO.amount;
		}
		return num;
		
		//  net flow coming in
	}
	public double getOutputsValue() {
		double num = 0;
		for(TransOutput t : outputs) {
			num += t.amount;
		}
		return num;
	}
	// net flow going out
}