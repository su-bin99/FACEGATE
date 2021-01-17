package View;

import java.awt.Color;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.*;

public class FaceRecognitionView extends JFrame implements ActionListener {

   Image _ku_logoImg = null;
   JButton manage = new JButton("MANAGER PAGE");
   FaceRecognization FR;
   
   public FaceRecognitionView(FaceRecognization FR) {
      super("FACEGATE"); // title
      this.setSize(800, 650);
      this.getContentPane().setBackground(Color.WHITE);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      this.setVisible(true);
      this.FR = FR;
      init();
   }
   
   
   public void init() {
      // TODO Auto-generated method stub
      this.setForeground(Color.WHITE);
      try {
         _ku_logoImg = ImageIO.read(new File("Img/KU_Logo.png"));
      } catch (IOException e) {
         JOptionPane.showMessageDialog(null, "Fail to load image.");
         System.exit(0);
      }

      this.setIconImage(_ku_logoImg);
      this.setLayout(null);
      /*
       * show web-cam image
       * 
       * manager logout button?
       */
      
      this.add(manage);
      manage.setForeground(Color.white);
      manage.setOpaque(true);
      manage.setBackground(new Color(0xFFC000));
      manage.setBorderPainted(false);
      manage.setFocusPainted(false);
      manage.setBounds(550, 500, 180, 50);
      
      initListener();
      try {
         FR.start();
         //JOptionPane.showMessageDialog(null, "얼굴인식에 성공하였습니다.");
      }catch(Exception e) {
        e.getStackTrace();
      }
      
      
   }
   
   public void initListener() {
      // TODO Auto-generated method stub
      manage.addActionListener(this);
   }


   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if(e.getSource() == manage) {
         FR.setStop(true);
         this.setVisible(false);
           new ManageChoiceView().setVisible(true);
      }
   }

}