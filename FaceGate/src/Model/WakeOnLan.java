package Model;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WakeOnLan {
   public static final int PORT = 9;
   //db 이름이 employee인것 연결 num(소프트맥스 번호??) 과 컴퓨터 주소를 저장한 db
  // private static final String DR_URL = "jdbc:mysql://localhost:3306/employee?serverTimezone=Asia/Seoul&useSSL=false";
   //user id
  // private static final String USRE_NAME ="root";
   //user pw
  // private static final  String PASSWORD = "DbScKs!3@4";
   
   
   public void power_on(int num) {
      
      
	   // String JDBD_DRIVER = "com.mysql.jdbc.Driver";
	   Statement state =null;
	   Connection conn=null;
	   String address2 =null;
	   try {
	      Class.forName("org.sqlite.JDBC");
	      //db 연결
	      conn = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
	      state = conn.createStatement();
	      String a = Integer.toString(num);
	         
	      // softmax인거 같아서 employee 중 num과 같은 사원의 주소를 뽑는 sql
	         String sql = "SELECT Employee_CP FROM EMPLOYEE where Employee_IDX = " + a;
	      ResultSet rs = state.executeQuery(sql);

	      address2 = rs.getString("Employee_CP");
	      rs.close();
            
         
         String macStr = address2; /*"e4-b3-18-ba-15-da"*/;
         String ipStr = "255.255.255.255";

         try {
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];

            int i;
            for (i = 0; i < 6; ++i) {
               bytes[i] = -1;
            }

            for (i = 6; i < bytes.length; i += macBytes.length) {
               System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 9);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            System.out.println("Wake-on-LAN packet sent.");
         } catch (Exception var8) {
            System.out.println("Failed to send Wake-on-LAN packet: + e");
            //System.exit(1);
         }
         
         
      } catch (Exception e) {
         // TODO: handle exception
         System.out.println("DB생성 실패");
      }finally {
         try {
            if(state != null) {
               //System.out.println("state Close");
               state.close();
            }
         } catch (SQLException e2) {
            // TODO: handle exception
         }
         
         try {
            if(conn != null)
               //System.out.println("conn Close");
               conn.close();
            
         } catch (SQLException e2) {
            // TODO: handle exception
         }
         
      }
      
   

   }

   private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
      byte[] bytes = new byte[6];
      String[] hex = macStr.split("(\\:|\\-)");
      if (hex.length != 6) {
         throw new IllegalArgumentException("Invalid MAC address.");
      } else {
         try {
            for (int i = 0; i < 6; ++i) {
               bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }

            return bytes;
         } catch (NumberFormatException var4) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
         }
      }
   }
}