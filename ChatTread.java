package com.test.sku.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatTread extends Thread {
	private String userid;
	private Socket s;
	private ObjectInputStream oin;
	private ObjectOutputStream oos;
	
    static Map<String,ObjectOutputStream> user = new HashMap<>();	//선언
	public ChatTread(String userid,Socket s,ObjectInputStream oin, ObjectOutputStream oos) {
		this.userid =userid;
		this.s = s;
		this.oin = oin;
		this.oos = oos;
		
		ChatMsg cm = new ChatMsg("서버","클라이언트","로그인 성공");
		try {
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void run() {
		
		System.out.println("채팅을 시작하겠습니다");
 
		try {
			while(true) {
				ChatMsg cm = (ChatMsg)oin.readObject();	//클라이언트가 종료하면 SocketException 발생
				if(cm.isSecret) {
					
					user.get(cm.to).writeObject(cm);
					user.get(cm.to).flush();
					continue;
				}
				
				
				//접속한 모든 이용자에게 메시지를 전달한다
				Set<String> idSet = ChatTread.user.keySet();
				Iterator <String> idIter = idSet.iterator();
				ObjectOutputStream userOut = null;
				String userid =null;
				while(idIter.hasNext()) {
					userid = idIter.next();		//userid = key					
					userOut = user.get(userid);
					userOut.writeObject(cm);
					userOut.flush();
					}
				
	
			}
		}catch(Exception e) {
			InetAddress ia = s.getInetAddress();				//user 주소 
			System.err.println(ia + "이용자 퇴장");
			//map의 이용자user에서 퇴장한 이용자의 id와 oos삭제한다
			//ObjectOutputStream oos = user.get(ia);
			// InetAddress 객체를 String으로 변환
			
			user.remove(userid);
			System.out.println(user);

		}
	
		
		System.err.println("Chat Thread dead");
	
	}

}
