package com.test.sku.network;

import java.io.Serializable;

public class ChatMsg implements Serializable{

	String uid;
	String pwd;
	String from;
	String to;			//받는 사람의 아이디
	String msg;
	boolean login;
	boolean isSecret;
	byte[] fdata;
	
	public ChatMsg() {}
	public ChatMsg(boolean login,String uid,String pwd)
	{
		this.login = login;
		this.uid = uid;
		this.pwd = pwd;
	}
	public ChatMsg(String form, String to,String msg)
	{
		this.from = from;
		this.to = to;
		this.msg = msg;
	}
	
}
