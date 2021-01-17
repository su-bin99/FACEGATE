package Model;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

//import java.sql.*;
//import java.util.*;


public class FaceRecognization extends Thread{
   WakeOnLan wol;
   boolean stop;
   //execCNN에서 해당 사원 index return
   int em_index;
   static String buff = new String();

   public void run() {
	   System.out.println("FaceRecognization run");
	   while(!stop) {
		   getImage(1);
	   }
   }
   
   //얼굴인식+WOL 실행
   public void getImage(int num) {

	   	 wol = new WakeOnLan();
         String [] command = new String[2];
         String index = new String();
         int len;
         //int i_index; //사원 인덱스
         
         command[0] = "python";
         command[1] = "C:\\Users\\Hong\\Documents\\FACEGATE\\FaceGate\\webcam+recog.py";
         	
         //CNN model 실행
         try {
              execCNN(command);
          } catch (Exception e) {
              e.printStackTrace();
         }
         
         
          len = buff.length();
          index =  buff.substring(len-3);
          index = index.substring(0,1);
          System.out.println(index);
          em_index = Integer.parseInt(index);
          
          System.out.println("em_index : "+em_index);

          //사원 인덱스 기준으로 DB에 출근 시간 기록
          
          //em_index = 1;
          
          DbManager db=new DbManager();
          if(num==1) {
        	  db.Commute_On(em_index);
        	  JOptionPane.showMessageDialog(null, "얼굴인식에 성공하였습니다.");
          }
          else if(num==2) {
        	  db.Commute_Off(em_index);
          }
          //WOL 실행
          wol.power_on(em_index);
   }

   //CNN 모델 실행
   public static void execCNN(String[] command) throws IOException, InterruptedException{
        CommandLine commandLine = CommandLine.parse(command[0]);
         for (int i = 1, n = command.length; i<n; i++) {
            commandLine.addArgument(command[i]);
         
         }
         
         
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
         DefaultExecutor executor = new DefaultExecutor();
         
         executor.setStreamHandler(pumpStreamHandler);
         
         //executor.setExitValue(1);
         int result = executor.execute(commandLine);
         //executor.setExitValue(1);
         
         
         
         //System.out.println("result:" + result);
         System.out.println("output:" + outputStream.toString());
         buff = outputStream.toString();
         
      }

   		public void setStop(boolean stop) { 
   			this.stop = stop; 
	   }


   

}