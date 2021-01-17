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
	         //int i_index; //��� �ε���
	         
	         command[0] = "python";
	         command[1] = "C:\\Users\\Hong\\Documents\\FACEGATE\\FaceGate\\new_preson_cam.py";
	         	
	         //CNN model ����
	         try {
	              execCapture(command);
	          } catch (Exception e) {
	              e.printStackTrace();
	         }
	         


	          //��� �ε��� �������� DB�� ��� �ð� ���
	          
	          //em_index = 1;
	          
	   }

	   //CNN �� ����
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
