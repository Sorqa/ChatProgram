package com.test.sku.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LogingThread extends Thread {	//extends Thread해야 가상의 cpu가 된다.implement는 start가 없다
	  private Socket s;
	  private ObjectInputStream oin;
	  private ObjectOutputStream oos;

	    public LogingThread(Socket s) {
	        this.s = s;
	    }
	    
	    @Override
	    public void run(){
		//서버측에서 클라이언트에게 "서버접속 성공" 메시지 전달 -> 출력
		try {
			OutputStream out = s.getOutputStream();
			this.oos = new ObjectOutputStream(out);
			ChatMsg cm = new ChatMsg("서버", "클라이언트", "아이디 암호");
			oos.writeObject(cm);
			oos.flush();
			
			//클라이언트로부터 아이디,암호를 받는다
			InputStream in = s.getInputStream();			
			this.oin = new ObjectInputStream(in);
			
			ChatMsg cm2 = (ChatMsg)oin.readObject();
			System.out.printf("%s / %s %n", cm2.uid, cm2.pwd);
			//아이디 smith 암호 1234면 챗팅 스레드를 돌린다 스레드 종료하는 방법(죽게dead)는 로직을 다 소모시킨다 시험임
			//채팅 스레드에는 내가 보낸 메세지는 서버로 갔다가 나에게 온다
			// object input stearamd ouput stream 보낼 때는 writeobjdect
			//String sm = "smith";
			//String p = "1234";
			if(cm2.uid.length()>3&&cm2.pwd.length()>3) {	//이용자 인증
				System.out.println("로그인 성공");				
			   ChatTread.user.put(cm2.uid,oos);				//map에 id,object output Stream을 추가한다
			   new ChatTread(cm2.uid,this.s,this.oin,this.oos).start();	//채팅 시작
			}else {
				ChatMsg cm3 = new ChatMsg("서버","클라이언트","로그인 실패");
				oos.writeObject(cm3);
				oos.flush();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		System.err.println("LoginThread dead");
		
		
	
	}

}
