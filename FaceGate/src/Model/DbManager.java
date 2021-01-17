package Model;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
   public static void main(String[] args) {
      new DbManager();
   }

   Connection cnt = null;
   Statement stat = null;

   public DbManager() {
//      try {
//         // Class Load
//         Class.forName("org.sqlite.JDBC");
//         System.out.println("Find org.sqlite.JDBC");
//
//         try {
//            // create a database connection
//            cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
//            stat = cnt.createStatement();
//
//            ResultSet rs = stat.executeQuery("select *from EMPLOYEE");
//            while (rs.next()) {
//               System.out.println(rs.getInt("Employee_IDX"));
//            }
//            rs.close();
//            cnt.close();
//            stat.close();
//
//         } catch (SQLException e) {
//            // if the error message is "out of memory",
//            // it probably means no database file is found
//            System.err.println(e.getMessage());
//         }
//
//      } catch (ClassNotFoundException e) {
//         System.out.println("Can't find org.sqlite.JDBC");
//         System.err.println(e.getMessage());
//      }

      // 함수 실행 예시입니다.
//      Commute_On(201811259);
//      Commute_Off(201811259);
//      managerSignUp("baesubin18", "qotnqls", 201811259);
//      employeeSignUp(201811259, "배수빈", "DB/디자인팀", null);
//      LogIn("baesubin18", "qotnqls");
//      deleteEmployee(201811259);
//      getEmployeeData("배수빈");
   }

   public int Commute_On(int Employee_IDX) {
      // return 1 : 출근 입력 성공
      // return 2 : 사원이 아님 -> 입력안함

      try {
         // Employee_IDX가 Employee테이블에 존재할 경우,
         // 해당 Employee_IDX에 대해 현재시간 insert
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();

         ResultSet rs = stat.executeQuery(
               "select EXISTS (select * from EMPLOYEE where Employee_IDX = " + Employee_IDX + ") as success");
         int flag = rs.getInt(1);
         if (flag != 0) {// 사원O
            String query = "insert into COMMUTE ( Employee_IDX, Commute_ON_TM, Commute_OFF_TM ) " + "values ("
                  + Integer.toString(Employee_IDX) + ",datetime('now', 'localtime'), NULL)";
            if (stat.executeUpdate(query) >= 1) {
               System.out.println(Employee_IDX + "님 출근 완료");
               rs.close();
               cnt.close();
               stat.close();
               return 1;
            } else {
               rs.close();
               cnt.close();
               stat.close();
               return 0;
            }
         } else {// 사원X
            System.out.println("출근 - 사원이 아닙니다. ");
            rs.close();
            cnt.close();
            stat.close();
            return 2;
         }

      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }

      return 0;
   }

   public int Commute_Off(int Employee_IDX) {
      // return 1: 퇴근입력 성공
      try {
         // Commute테이블에 출근상태인 Employee에 대해서
         // Commute_Off컬럼에 현재시간 update
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();

         String query = "update Commute set Commute_OFF_TM = datetime('now', 'localtime') "
               + "where Commute_OFF_TM is NULL And COMMUTE.Employee_IDX =" + Employee_IDX;
         if (stat.executeUpdate(query) >= 1) {
            System.out.println(Employee_IDX + "님 퇴근 완료");
            cnt.close();
            stat.close();
            return 1;
         } else {
            System.out.println("존재하지 않은 사원이거나 출근상태가 아닙니다. ");
            cnt.close();
            stat.close();
            return 0;
         }

      } catch (SQLException e) {
         System.err.println(e.getMessage());
      }
      return 0;
   }

   public int managerSignUp(String Manager_Id, String Manager_Pwd, Integer Employee_IDX) {
      // return 0 : 입력 실패
      // return 1 : 입력 성공
      // return 2 : 사원이 아님 -> 입력안함
      // return 3 : 이미 관리자로 등록된 사원 -> 입력안함

      ResultSet rs;

      try {
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();

         rs = stat.executeQuery(
               "select EXISTS (select * from EMPLOYEE where Employee_IDX = " + Employee_IDX + " ) as success");

         int flag = rs.getInt(1);
         if (flag != 0) { // 사원O 확인
            rs = stat.executeQuery(
                  "select EXISTS (select * from Manager where Employee_IDX = " + Employee_IDX + " ) as success");
            if (rs.getInt(1) == 1) { // 사원O, 관리자O
               System.out.println("이미 관리자로 등록된 사원입니다. ");
               rs.close();
               cnt.close();
               stat.close();
               return 3;
            } else { // 사원O , 관리자X 상태 -> DB에 입력함
               rs.close();
               String query = "insert into MANAGER ( Employee_IDX, Manager_ID, Manager_PWD ) " + "values ("
                     + Integer.toString(Employee_IDX) + ", '" + Manager_Id + "' , '" + Manager_Pwd + "' )";
               if (stat.executeUpdate(query) >= 1) {
                  System.out.println("사원번호 : " + Employee_IDX + " 관리자 가입 성공 ");
                  cnt.close();
                  stat.close();
                  return 1;
               } else {
                  cnt.close();
                  stat.close();
                  return 0;
               }
            }
         } else {// 사원X
            rs.close();
            cnt.close();
            stat.close();
            System.out.println("관리자 - 사원이 아닙니다. ");
            return 2;
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }
      return 0;
   }

   public int employeeSignUp(int Employee_IDX, String Employee_NM, String Employee_DP, String Employee_CP) {
      // return 0 : 입력 실패
      // return 1 : 입력 성공
      // return 2 : 이미 사운으로 등록됨 -> 입력안함

      try {
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();
         ResultSet rs = stat.executeQuery(
               "select EXISTS (select * from EMPLOYEE where Employee_IDX = " + Employee_IDX + " ) as success");
         int flag = rs.getInt(1);
         rs.close();
         if (flag == 0) { // 사원X 확인
            String query = "insert into EMPLOYEE ( Employee_IDX, Employee_NM, Employee_DP, Employee_CP ) "
                  + "values (" + Integer.toString(Employee_IDX) + ", '" + Employee_NM + "' , '" + Employee_DP
                  + "' , '" + Employee_CP + "' )";
            int temp = stat.executeUpdate(query);
            if (temp >= 1) {
               System.out.println("사원번호 : " + Employee_IDX + " 가입성공 ");
               cnt.close();
               stat.close();
               return 1;
            } else {
               cnt.close();
               stat.close();
               return 0;
            }

         } else {// 사원O
            System.out.println("이미 사원으로 등록되어 있음");
            cnt.close();
            stat.close();
            return 2;
         }
      } catch (

      SQLException e) {
         e.printStackTrace();
      }

      return 0;
   }

   public int LogIn(String Manager_ID, String Manager_Pwd) {
      // return 0 : 로그인 실패
      // 성공시 사원번호 리턴
      int employee_IDX = 0;
      try {
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();

         ResultSet rs = stat.executeQuery("select * from MANAGER where Manager_ID = '" + Manager_ID
               + "' and Manager_Pwd = '" + Manager_Pwd + "'");
         if (rs.next()) {
            employee_IDX = rs.getInt("Employee_IDX");
            System.out.println("로그인성공 / 사원번호 : " + employee_IDX);
            cnt.close();
            stat.close();
            rs.close();
         } else {
            cnt.close();
            stat.close();
            rs.close();
            System.out.println("로그인 실패 ");
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }
      return employee_IDX;
   }

   public int deleteEmployee(int Employee_IDX) {
      // return 0 : 삭제 실패
      // return 1 : 삭제 성공
      // return 2 : 사원 X
      try {
         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
         stat = cnt.createStatement();

         ResultSet rs = stat.executeQuery(
               "select EXISTS (select * from EMPLOYEE where Employee_IDX = " + Employee_IDX + " ) as success");
         int flag = rs.getInt(1);
         rs.close();
         if (flag == 0) {// 사원X
            System.out.println("사원이 아님 ");
            cnt.close();
            stat.close();
            return 2;
         } else { // 사원O
            String query = "delete from employee where Employee_IDX = " + Employee_IDX;
            if (stat.executeUpdate(query) >= 1) {
               System.out.println("사원번호 : " + Employee_IDX + " 삭제성공 ");
               cnt.close();
               stat.close();
               return 1;
            } else {
               cnt.close();
               stat.close();
               return 0;
            }
         }

      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return 0;
   }

   public ArrayList<String> getEmployeeData(String Employee_NM) {
	      // return null : 사원이 아님
	      // ArrayList[0] : Employee_IDX를 String으로 변환한것
	      // ArrayList[1] : Employee_NM
	      // ArrayList[2] : Employee_DP
	      // ArrayList[3] : 가장 최근 commute_on시간 

	      ArrayList returnArray = new ArrayList<String>();

	      try {

	         cnt = DriverManager.getConnection("jdbc:sqlite:FaceGate.db");
	         stat = cnt.createStatement();

	         ResultSet rs = stat.executeQuery(
	               "select EXISTS (select * from EMPLOYEE where Employee_NM = '" + Employee_NM + "' ) as success");
	         int flag = rs.getInt(1);
	         rs.close();
	         if (flag == 0) {// 사원X
	            System.out.println("사원이 아님 ");
	            cnt.close();
	            stat.close();
	            return null;
	         } else { // 사원O
	            ResultSet rs2 = stat.executeQuery("select * from Employee where Employee_NM = '" + Employee_NM + "'");
	            int idx = rs2.getInt("Employee_IDX");
	            returnArray.add(Integer.toString(idx));
	            returnArray.add(rs2.getString("Employee_NM"));
	            returnArray.add(rs2.getString("Employee_DP"));
	            
	            ResultSet rs3 = stat.executeQuery("select * from COMMUTE where Employee_IDX = " + idx  
	                  + " and Commute_IDX = (SELECT max(Commute_IDX) FROM COMMUTE)");
	            returnArray.add(rs3.getString("Commute_ON_TM"));
	                  
	            rs2.close();
	            rs3.close();
	            cnt.close();
	            stat.close();
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      System.out.println(returnArray.get(0) + " " + returnArray.get(1) + " " + returnArray.get(2) + " " + returnArray.get(3) );
	      return returnArray;
	   }
   

}