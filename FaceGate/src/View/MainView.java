package View;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Model.*;

public class MainView extends JFrame implements ActionListener {
   Image _ku_logoImg, userImg, pwdImg = null;
   ImageIcon userIcon, pwdIcon;
   JLabel userPic, pwdPic;
   JPanel LogoPanel = new JPanel();
   JPanel IDPanel = new JPanel();
   JPanel PWPanel = new JPanel();
   JPanel btnPanel = new JPanel();

   JTextField ID = new JTextField(20);
   JPasswordField PW = new JPasswordField();
   JButton SignUpBtn = new JButton("SIGN UP");
   JButton LogInBtn = new JButton("LOG IN");
   Controller control;
   FaceRecognization FR;

   public MainView() {
      super("FACEGATE"); // title
      this.setSize(800, 650);
      this.getContentPane().setBackground(Color.WHITE);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      init();
      this.setVisible(true);
      control = new Controller();
      FR = new FaceRecognization();
   }

   public void init() {

      this.setForeground(Color.WHITE);
      try {
         _ku_logoImg = ImageIO.read(new File("Img/KU_Logo.png"));
         userImg = ImageIO.read(new File("Img/user.png"));
         pwdImg = ImageIO.read(new File("Img/key.png"));
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

      userImg = userImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);
      pwdImg = pwdImg.getScaledInstance(40, 40, Image.SCALE_DEFAULT);

      userIcon = new ImageIcon(userImg);
      pwdIcon = new ImageIcon(pwdImg);

      userPic = new JLabel(userIcon);
      pwdPic = new JLabel(pwdIcon);

      IDPanel.setLayout(null);
      IDPanel.add(userPic);
      IDPanel.add(ID);
      userPic.setBounds(5, 5, 40, 40);
      ID.setBounds(60, 0, 340, 50);

      PWPanel.setLayout(null);
      PWPanel.add(pwdPic);
      PWPanel.add(PW);
      pwdPic.setBounds(5, 5, 40, 40);
      PW.setBounds(60, 0, 340, 50);

      btnPanel.setLayout(null);
      btnPanel.add(SignUpBtn);
      SignUpBtn.setBounds(0, 10, 190, 40);
      btnPanel.add(LogInBtn);
      LogInBtn.setBounds(210, 10, 190, 40);

      SignUpBtn.setForeground(Color.white);
      SignUpBtn.setOpaque(true);
      SignUpBtn.setBackground(new Color(0xFFC000));
      SignUpBtn.setBorderPainted(false);
      SignUpBtn.setFocusPainted(false);

      LogInBtn.setForeground(Color.white);
      LogInBtn.setOpaque(true);
      LogInBtn.setBackground(new Color(0xFFC000));
      LogInBtn.setBorderPainted(false);
      LogInBtn.setFocusPainted(false);

      this.add(IDPanel);
      IDPanel.setOpaque(false);
      IDPanel.setBounds(200, 240, 400, 50);

      this.add(PWPanel);
      PWPanel.setOpaque(false);
      PWPanel.setBounds(200, 300, 400, 50);

      this.add(btnPanel);
      btnPanel.setOpaque(false);
      btnPanel.setBounds(200, 360, 400, 60);

      initListener();
   }

   public void initListener() {
      // TODO Auto-generated method stub
      SignUpBtn.addActionListener(this);
      LogInBtn.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if (e.getSource() == LogInBtn) {
         int temp = control.LogIn(ID.getText(), PW.getText());
         // return 0 : 로그인 실패
         // 성공시 사원번호 리턴
         if (temp != 0) {
            
            JOptionPane.showMessageDialog(null, "관리자 로그인 성공 \n   사원번호 : " + Integer.toString(temp));
            this.setVisible(false);
            new FaceRecognitionView(FR).setVisible(true);            
         } else {
            JOptionPane.showMessageDialog(null, "로그인에 실패했습니다. ");
         }
      } else if (e.getSource() == SignUpBtn) {
         this.setVisible(false);
         new SignUpView().setVisible(true);
      }
   }

   public static void main(String[] args) {
      MainView frame = new MainView();
   }
}