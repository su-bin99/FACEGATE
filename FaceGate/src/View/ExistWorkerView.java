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
   
   
   String header[]={"ÀÌ¸§", "¼Ò¼Ó", "»ç¿ø°ü¸®", "Á¤º¸»èÁ¦"};
    String contents[][]={
            {"¹è¼öºó", "µðÀÚÀÎÆÀ", "0", "0"},
            {"±è¼ö¹Î", "µðÀÚÀÎÆÀ", "0", "0"},
            {"ÀåÇý¸²", "½Ã½ºÅÛÆÀ", "0", "0"},
            {"ÀÌ°¡Çö", "µ¥ÀÌÅÍÆÀ", "0", "0"},
            {"Â÷Èñ¼ö", "µ¥ÀÌÅÍÆÀ", "0", "0"},
            {"È«¿¹ÁÖ", "½Ã½ºÅÛÆÀ", "0", "0"},
            {"À±Âù", "½Ã½ºÅÛÆÀ", "0", "0"},
            {"È«±æµ¿", "ÀÎ»çÆÀ", "0", "0"},
            {"ÀÌ±æµ¿", "Àç¹«ÆÀ", "0", "0"},
            {"±è±æµ¿", "È«º¸ÆÀ", "0", "0"},
            {"¹è±æµ¿", "È«º¸ÆÀ", "0", "0"},
            {"Àå±æµ¿", "±âÈ¹ÆÀ","0", "0"},
            {"À±±æµ¿", "Àç¹«ÆÀ", "0", "0"},
            {"¹Ú±æµ¿", "ÀÎ»çÆÀ","0", "0"},
            {"Á¤±æµ¿", "±âÈ¹ÆÀ", "0", "0"},
            {"°­±æµ¿", "°³¹ßÆÀ","0", "0"},
            {"ÀÓ±æµ¿", "±âÈ¹ÆÀ","0", "0"}
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
       JLabel pn2 = new JLabel("¹è¼öºó(°ü¸®ÀÚ)");
       JLabel pn3 = new JLabel("DB/µðÀÚÀÎÆÀ");      
      
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
       * DB¿¡¼­ »ç¿øÁ¤º¸¸¦ ÀÐ¾î¿Í¼­ º¸¿©Áà¾ß ÇÔ. 
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
               JOptionPane.showMessageDialog(null," »èÁ¦µÇ¾ú½À´Ï´Ù.");
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
               JOptionPane.showMessageDialog(null," »ç¿øÁ¤º¸¸¦ Á¶È¸ÇÕ´Ï´Ù..");
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