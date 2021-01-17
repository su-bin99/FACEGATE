package View;

import java.awt.image.*;
import javax.swing.border.EmptyBorder;

import Model.Controller;
import Model.FaceRecognization;

import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SignUpView extends JFrame implements ActionListener {
   JButton CancelBtn = new JButton("CANCEL");
   JButton SubmitBtn = new JButton("SUBMIT");
   FaceRecognization FR;
   Controller control;

   JTextField IdText = new JTextField(20);
   JTextField PwdText = new JTextField(20);
   JTextField CheckPwdText = new JTextField(20);
   JTextField EnText = new JTextField(20);

   Image _ku_logoImg = null;

   public SignUpView() {

      super("FACEGATE"); // title
      this.setSize(800, 650);
      this.getContentPane().setBackground(Color.WHITE);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      init();
      this.setVisible(true);
      FR = new FaceRecognization();
   }

   public void init() {
      this.setForeground(Color.WHITE);
      try {
         _ku_logoImg = ImageIO.read(new File("Img/KU_Logo.png"));
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Fail to load image.");
         System.exit(0);
      }

      this.setIconImage(_ku_logoImg);
      this.setLayout(null);

      JLabel la = new JLabel("FACE GATE");
      la.setSize(400, 100);
      la.setForeground(Color.white);
      la.setOpaque(true);
      la.setBackground(new Color(0x0E345B));
      la.setHorizontalAlignment(JLabel.CENTER);
      la.setFont(la.getFont().deriveFont(40.0f));

      this.add(la);
      la.setBounds(200, 110, 400, 100);

      JLabel la2 = new JLabel("ADMINISTRATOR REGISTER");
      la2.setSize(400, 40);
      la2.setForeground(new Color(0x0E345B));
      la2.setOpaque(false);
      la2.setBackground(Color.white);
      la2.setHorizontalAlignment(JLabel.CENTER);
      la2.setFont(la2.getFont().deriveFont(20.0f));

      this.add(la2);
      la2.setBounds(200, 220, 400, 40);

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(5, 2));

      this.add(panel);
      panel.setBounds(200, 270, 400, 200);

      JLabel IdLabel = new JLabel("ID");
      JLabel PwdLabel = new JLabel("Password");
      JLabel PwdCheckLabel = new JLabel("Password Check");
      JLabel EmployeeNumLabel = new JLabel("Worker No.");

      CancelBtn.setForeground(Color.white);
      CancelBtn.setOpaque(true);
      CancelBtn.setBackground(new Color(0xFFC000));
      CancelBtn.setBorderPainted(false);
      CancelBtn.setFocusPainted(false);

      SubmitBtn.setForeground(Color.white);
      SubmitBtn.setOpaque(true);
      SubmitBtn.setBackground(new Color(0xFFC000));
      SubmitBtn.setBorderPainted(false);
      SubmitBtn.setFocusPainted(false);

      panel.add(IdLabel);
      panel.add(IdText);
      panel.add(PwdLabel);
      panel.add(PwdText);
      panel.add(PwdCheckLabel);
      panel.add(CheckPwdText);
      panel.add(EmployeeNumLabel);
      panel.add(EnText);
      panel.add(CancelBtn);
      panel.add(SubmitBtn);

      initListener();
   }

   public void initListener() {
      // TODO Auto-generated method stub
      CancelBtn.addActionListener(this);
      SubmitBtn.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (e.getSource() == CancelBtn) {
         this.setVisible(false);
         new MainView().setVisible(true);
      } else if (e.getSource() == SubmitBtn) {
         // renew administrator DB
        
         String id = IdText.getText();
         String pwd = PwdText.getText();
         String checkPwd = CheckPwdText.getText();
         String employeeNum = EnText.getText();
         int temp = 100;

         if (pwd.equals(checkPwd)) {
            temp = Controller.ManagerSignUp(id, pwd, Integer.parseInt(employeeNum));

            switch (temp) {
            case 0: // 입력 실패e
               JOptionPane.showMessageDialog(null, "관리자 등록에 실패했습니다. ");
               break;
            case 1: // 입력 성공
               JOptionPane.showMessageDialog(null, "관리자 등록에 성공했습니다. ");
               break;
            case 2: // 사원 아님 -> 입력 X
               JOptionPane.showMessageDialog(null, "사원이 아니므로 관리자 등록이 불가능합니다. ");
               break;
            case 3: // 이미 관리자 -> 입력X
               JOptionPane.showMessageDialog(null, "이미 관리자로 등록되어있는 사원입니다. ");
               break;
            }
            this.setVisible(false);
            new MainView().setVisible(true);
         } else {
            JOptionPane.showMessageDialog(null, "입력하신 비밀번호가 다릅니다.");
         }

      }
   }
}