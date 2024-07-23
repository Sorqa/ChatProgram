package com.test.sku.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InputTread extends Thread {
	private ObjectInputStream oin;
	String uid;
	//private ObjectOutputStream oos;
	
	
	public InputTread(String uid,ObjectInputStream oin) {
		this.uid=uid;
		this.oin = oin;		
	}


	@Override
	public void run() {
		  
		ChatMsg cm;
		try {
			while(true) {
			cm = (ChatMsg)oin.readObject();
			if(!(this.uid.equals(cm.uid))) {
			System.out.println("[" + cm.uid+ "]" +cm.msg);
			}else continue;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}

}
