package Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

public class NewFaceCapture {

	   public void getImage() {

	         String [] command = new String[2];
	         String index = new String();
	         int len;
	         //int i_index; //사원 인덱스
	         
	         command[0] = "python";
	         command[1] = "C:\\Users\\Hong\\Documents\\FACEGATE\\FaceGate\\new_preson_cam.py";
	         	
	         //CNN model 실행
	         try {
	              execCapture(command);
	          } catch (Exception e) {
	              e.printStackTrace();
	         }
	         


	          //사원 인덱스 기준으로 DB에 출근 시간 기록
	          
	          //em_index = 1;
	          
	   }

	   //CNN 모델 실행
	   public static void execCapture(String[] command) throws IOException, InterruptedException{
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

	         
	      }

}
