package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import Model.FaceRecognization;

public class ExistWorkerView extends JFrame  implements ActionListener{
   JTable table;
   Image _ku_logoImg, homeImg, prevImg = null;
   ImageIcon homeIcon, prevIcon;
   
   JButton home = new JButton();
   JButton prev = new JButton();
   JPanel ManagerInfo = new JPanel();
   JScrollPane jscp1;
   
   FaceRecognization FR;
   
   
   String header[]={"�̸�", "�Ҽ�", "�������", "��������"};
    String contents[][]={
            {"�����", "��������", "0", "0"},
            {"�����", "��������", "0", "0"},
            {"������", "�ý�����", "0", "0"},
            {"�̰���", "��������", "0", "0"},
            {"�����", "��������", "0", "0"},
            {"ȫ����", "�ý�����", "0", "0"},
            {"����", "�ý�����", "0", "0"},
            {"ȫ�浿", "�λ���", "0", "0"},
            {"�̱浿", "�繫��", "0", "0"},
            {"��浿", "ȫ����", "0", "0"},
            {"��浿", "ȫ����", "0", "0"},
            {"��浿", "��ȹ��","0", "0"},
            {"���浿", "�繫��", "0", "0"},
            {"�ڱ浿", "�λ���","0", "0"},
            {"���浿", "��ȹ��", "0", "0"},
            {"���浿", "������","0", "0"},
            {"�ӱ浿", "��ȹ��","0", "0"}
    };
    
   public ExistWorkerView() {
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
       JLabel pn2 = new JLabel("�����(������)");
       JLabel pn3 = new JLabel("DB/��������");      
      
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
      
      this.add(home);
      this.add(prev);
      this.add(ManagerInfo);

      home.setBounds(5, 5, 50, 40);
      ManagerInfo.setBounds(5,500 , 120, 60);
      prev.setBounds(60, 5, 40, 40);
      /*
       * DB���� ��������� �о�ͼ� ������� ��. 
       */

       DefaultTableModel dtm = new DefaultTableModel(contents, header);
       table = new JTable(dtm);
       table.getColumnModel().getColumn(2).setCellRenderer(new TableCell2());
        table.getColumnModel().getColumn(2).setCellEditor(new TableCell2());
 
        table.getColumnModel().getColumn(3).setCellRenderer(new TableCell1());
        table.getColumnModel().getColumn(3).setCellEditor(new TableCell1());

       jscp1 = new JScrollPane(table); 
       jscp1.setBounds(150, 150, 500, 100);

       this.add(jscp1, BorderLayout.CENTER);


      initListener();
   }
   
   public void initListener() {
      home.addActionListener(this);
      prev.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      if(e.getSource() == home) {
         this.setVisible(false);
           new FaceRecognitionView(FR).setVisible(true);
      }else if(e.getSource() == prev) {
         this.setVisible(false);
           new ManageChoiceView().setVisible(true);
      }
   }
   
    class TableCell1 extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;
 
        public TableCell1() {
            jb = new JButton("Del");
            jb.addActionListener(e -> {
               int row = table.getSelectedRow();
               DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(row);
               JOptionPane.showMessageDialog(null," �����Ǿ����ϴ�.");
            });
        }
 
        @Override
        public Object getCellEditorValue() {
            return null;
        }
 
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return jb;
        }
 
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return jb;
        }
    } 
    
    class TableCell2 extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        JButton jb;
 
        public TableCell2() {
            jb = new JButton("Go");
            jb.addActionListener(e -> {
               JOptionPane.showMessageDialog(null," ��������� ��ȸ�մϴ�..");
            });
        }
 
        @Override
        public Object getCellEditorValue() {
            return null;
        }
 
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            return jb;
        }
 
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                int column) {
            return jb;
        }
    } 
}