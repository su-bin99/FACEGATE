package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Model.Controller;
import Model.FaceRecognization;

public class NewWorkerInfoView extends JFrame  implements ActionListener {
   Image _ku_logoImg, homeImg, prevImg = null;
   ImageIcon homeIcon, prevIcon;
   
   FaceRecognization FR;
   Controller control;
   
   JButton home = new JButton();
   JButton prev = new JButton();
   JPanel ManagerInfo = new JPanel();
   JButton btn1 = new JButton("신규사원 사진 등록");
   
   JTextField IdxText = new JTextField(20);   //사원번호
   JTextField NmText = new JTextField(20);      //이름
   JTextField DpText = new JTextField(20);      //부서
   JTextField CpText = new JTextField(20);      //컴퓨터주소
   
   public NewWorkerInfoView() {
      super("FACEGATE"); // title
      this.setSize(800, 650);
      this.getContentPane().setBackground(Color.WHITE);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      init();
      this.setVisible(true);
   }
   
   public void init() {
      // TODO Auto-generated method stub
      this.setForeground(Color.WHITE);
      try {
         _ku_logoImg = ImageIO.read(new File("Img/KU_Logo.png"));
         homeImg = ImageIO.read(new File("Img/home.png"));
         prevImg = ImageIO.read(new File("Img/prev.png"));
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Fail to load image.");
         System.exit(0);
      }
      
      this.setIconImage(_ku_logoImg);
      this.setLayout(null);
      
      JLabel pn1 = new JLabel("201811259");
      JLabel pn2 = new JLabel("배수빈(관리자)");
      JLabel pn3 = new JLabel("DB/디자인팀");
      
      homeImg = homeImg.getScaledInstance(40   , 40, Image.SCALE_DEFAULT);
      homeIcon = new ImageIcon(homeImg);
      
      home.setIcon(homeIcon);
      home.setBorderPainted(false);
      home.setContentAreaFilled(false);
      home.setFocusPainted(false);
      home.setOpaque(false);
      
   
      prevImg = prevImg.getScaledInstance(40   , 40, Image.SCALE_DEFAULT);
      prevIcon = new ImageIcon(prevImg);
      
      prev.setIcon(prevIcon);
      prev.setBorderPainted(false);
      prev.setContentAreaFilled(false);
      prev.setFocusPainted(false);
      prev.setOpaque(false);
      
      ManagerInfo.setOpaque(true);
      ManagerInfo.setBackground(new Color(0xE7E6E6));
      
      
      ManagerInfo.setLayout(new BorderLayout());
      ManagerInfo.add(pn1, BorderLayout.NORTH);
      ManagerInfo.add(pn2, BorderLayout.CENTER);
      ManagerInfo.add(pn3, BorderLayout.SOUTH);
      
      
      btn1.setForeground(Color.white);
      btn1.setOpaque(true);
      btn1.setBackground(new Color(0x0E345B));
      btn1.setBorderPainted(false);
      btn1.setFocusPainted(false);
      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(4, 2));
      
      this.add(panel);
      panel.setBounds(200, 150, 400, 200);
      
      JLabel IdLabel = new JLabel("Worker No.");
      JLabel PwdLabel = new JLabel("Name");
      JLabel PwdCheckLabel = new JLabel("Department");
      JLabel EmployeeNumLabel = new JLabel("Computer address");


      panel.add(IdLabel);
      panel.add(IdxText);
      panel.add(PwdLabel);
      panel.add(NmText);
      panel.add(PwdCheckLabel);
      panel.add(DpText);
      panel.add(EmployeeNumLabel);
      panel.add(CpText);
      
      this.add(home);
      this.add(prev);
      this.add(ManagerInfo);
      this.add(btn1);
      
      home.setBounds(5, 5, 50, 40);
      ManagerInfo.setBounds(5,500 , 120, 60);
      prev.setBounds(60, 5, 40, 40);
      btn1.setBounds(300, 500 , 200, 50);
      
      initListener();
   }
   
   public void initListener() {
       // TODO Auto-generated method stub
      btn1.addActionListener(this);
      home.addActionListener(this);
      prev.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if(e.getSource() == home) {
         System.exit(0);
      }else if(e.getSource() == prev) {
         this.setVisible(false);
           new ManageChoiceView().setVisible(true);
      }else if(e.getSource() == btn1) {   
      
         String idx = IdxText.getText();
         String name = NmText.getText();
         String department = DpText.getText();
         String Computer = CpText.getText();
         int temp = 100;
         
         if(Computer.equals("")) {
            Computer = null;
         }
         temp = control.EmployeeSignUp(Integer.parseInt(idx), name, department, Computer);

         switch (temp) {
         case 0: // 입력 실패
            JOptionPane.showMessageDialog(null, "사원 등록에 실패했습니다. ");
            break;
         case 1: // 입력 성공
            
           this.setVisible(false);
            new NewWorkerView().setVisible(true);
            break;
         case 2: // 이미 사원으로 등록됨 -> 입력 X
            JOptionPane.showMessageDialog(null, "이미 사원으로 등록되어있는 사원입니다. ");
            break;
         }
         
       
         //사원 DB로 정보가 넘어가야 함
      }
   }
   

}