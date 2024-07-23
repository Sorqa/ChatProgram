package com.test.sku.network;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	public static void main(String[] args) {
		// 다중 이용자 채팅 서버
		//client가 오는 포트 번호 1024 이후의 것을 해야함
		//ServerSocket은 24시간 대기
		
		try {
			ServerSocket ss = new  ServerSocket(1234);
			while(true) {
			System.out.println("서버 대기중...");
			Socket s = ss.accept();	//accept 대기 , 클라이언트가 접속하면 통신용 소켓을 리턴한다
			//Listens for a connection to be made to this socket and acceptsit. 
			//The method blocks until a connection is made. 
			//blocks은 막혀서 안돌아가 접속이 올 때 까지
			System.out.println("클라이언트 접속");
			
			
			new LogingThread(s).start();	//별도의 cpu 작동
			
			   
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("서버 종료");
	}

}

/*
OutputStreamWriter osw = new OutputStreamWriter(out);
PrintWriter pout = new PrintWriter(osw);
pout.println("아이디 암호:");	//서버에 글이 적힘
pout.flush();
*/
