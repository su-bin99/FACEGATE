package Model;

import java.sql.*;
import java.util.*;

public class Controller {
   public static int check;
   public static int select;
   public static Scanner sc = new Scanner(System.in);

   // 로그인
   public static int LogIn(String id, String pw) {
      check = 0;
      DbManager db = new DbManager();
      // while(check==0) {
      return db.LogIn(id, pw);
      // }
   }

   // 관리자 등록
   public static int ManagerSignUp(String id, String pw, int index) {
      check = 0;
      DbManager db = new DbManager();
//      while(check!=1) {
      return db.managerSignUp(id, pw, index);
//      }
   }

   public static int EmployeeSignUp(int Employee_IDX, String Employee_NM, String Employee_DP, String Employee_CP) {
      // return 0 : 입력 실패
      // return 1 : 입력 성공
      // return 2 : 이미 사운으로 등록됨 -> 입력안함
      
      DbManager db = new DbManager();
      return db.employeeSignUp(Employee_IDX, Employee_NM, Employee_DP, Employee_CP );
   }

   // 사원삭제
   public static void Delete(int Employee_IDX) {
      DbManager db = new DbManager();
      db.deleteEmployee(Employee_IDX);
   }

   // 사원추가
   public static void AddEmployee(int Employee_IDX, String Employee_NM, String Employee_DP, String Employee_CP) {
      check = 0;
      while (check != 1) {
         DbManager db = new DbManager();
         check = db.employeeSignUp(Employee_IDX, Employee_NM, Employee_DP, Employee_CP);
      }
   }

}