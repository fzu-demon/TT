import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UII extends JFrame {
    public static int stars = 0;

    public static void main(String[] args) {
            star();
    }

    public static void star(){
        stars = 0;
        JFrame jf = new JFrame("福建十三水");
        jf.setBounds(550,300,960,500);
        JPanel jPanel = new JPanel();
        jPanel.setBounds(550,300,960,500);
        jPanel.setLayout(null);



        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(270,100,80,35);
        JTextField userText = new JTextField(20);
        userText.setBounds(350,100,165,35);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(270,200,80,35);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(350,200,165,35);
        jPanel.add(userLabel);
        jPanel.add(userText);
        jPanel.add(passwordLabel);
        jPanel.add(passwordText);

        JButton jButton = new JButton("注册");
        jButton.setBounds(450,260,80,50);
        jButton.setBorderPainted(true);
        jPanel.add(jButton);

        JButton jButton1 = new JButton("登录");
        jButton1.setBounds(270,260,80,50);
        jButton1.setBorderPainted(true);
        jPanel.add(jButton1);

        jf.setContentPane(jPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                jf.dispose();
                log();
            }
        });

        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                Test.username = userText.getText();
                Test.password = new String(passwordText.getPassword());
                stars = 1;
                Test.log();
                jf.dispose();
                hit();
            }
        });

//        ImageIcon icon = new ImageIcon("image/1.jpg");
//        JLabel p = new JLabel(icon);
//        p.setBounds(-150,-50,1080,500);
//        jPanel.add(p);
//        jf.repaint();
    }       //开始界面

    public static void log(){
        JFrame jFrame = new JFrame("注册");
        jFrame.setBounds(550,300,960,500);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(250,100,80,25);
        JTextField userText = new JTextField(20);
        userText.setBounds(350,100,165,25);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(250,150,80,25);
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(350,150,165,25);

        JLabel stu_number = new JLabel("stu_number");
        stu_number.setBounds(250,200,80,25);
        JTextField student_number = new JTextField(20);
        student_number.setBounds(350,200,165,25);
        JLabel stu_password = new JLabel("stu_password:");
        stu_password.setBounds(250,250,100,25);
        JPasswordField student_password = new JPasswordField(20);
        student_password.setBounds(350,250,165,25);

        JButton jButton = new JButton("完成");
        jButton.setBounds(400,300,80,50);

        jPanel.add(userLabel);
        jPanel.add(userText);
        jPanel.add(passwordLabel);
        jPanel.add(passwordText);
        jPanel.add(stu_number);
        jPanel.add(student_number);
        jPanel.add(stu_password);
        jPanel.add(student_password);
        jPanel.add(jButton);

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                Test.username = userText.getText();
                Test.password = new String(passwordText.getPassword());
                Test.student_number = student_number.getText();
                Test.student_password = new String(student_password.getPassword());
                Test.register();
                hit();
                jFrame.dispose();
            }
        });
    }         //注册界面

    public static void hit(){
        int t ;
        JFrame jFrame = new JFrame("提示");
        jFrame.setBounds(750,400,450,400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        t = Test.listen;
        String res = "错误信息，请重新输入";
        if (t == 0){
            res = "成功！";
        }

        if (t == 1001){
            res = "用户名已被使用";
        }

        if (t == 1002){
            res = "学号已绑定";
        }

        if (t == 1003){
            res = "教务处认证失败";
        }

        if (t == 1005){
            res = "用户名或密码错误!";
        }

        JLabel message = new JLabel(res,SwingConstants.CENTER);
        message.setBounds(150,150,200,50);
        JButton jButton = new JButton("确定");
        jButton.setBounds(205,300,80,50);

        jPanel.add(message);
        jPanel.add(jButton);

        jFrame.setContentPane(jPanel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 进行逻辑处理即可
                jFrame.dispose();
                if (stars == 1 && t == 0){
                    game();
                }
                else {
                    star();
                }
            }
        });
    }          //提示信息

    public static void game(){
        JFrame jf = new JFrame("福建十三水");
        jf.setBounds(550,300,960,500);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);

        JButton jButton = new JButton("发牌");
        JButton jButton1 = new JButton("出牌");
        JButton jButton2 = new JButton("注销");
        JButton jButton3 = new JButton("排行榜");
        JButton jButton4 = new JButton("历史记录");
        jButton.setBounds(100,250,80,50);
        jButton1.setBounds(100,330,80,50);
        jButton2.setBounds(860,40,80,50);
        jButton3.setBounds(780,40,80,50);
        jButton4.setBounds(680,40,100,50);

        jPanel.add(jButton);
        jPanel.add(jButton1);
        jPanel.add(jButton2);
        jPanel.add(jButton3);
        jPanel.add(jButton4);
        JLabel jLabel = new JLabel("",SwingConstants.CENTER);
        JLabel jLabel1 = new JLabel("",SwingConstants.CENTER);
        jf.add(jPanel);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Test.star();
                jLabel.setText(Test.card);
                jLabel.setBounds(180,250,600,50);
                jLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
                jLabel.setBackground(Color.BLUE);
                jLabel.setOpaque(true);
                jPanel.add(jLabel);
                jPanel.repaint();
            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Test.submit();
                jLabel1.setText(Test.answer);
                jLabel1.setBounds(180,330,600,50);
                jLabel1.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
                jLabel1.setBackground(Color.PINK);
                jLabel1.setOpaque(true);
                jPanel.add(jLabel1);
                jPanel.repaint();
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                hit();
            }
        });
    }       //游戏大厅
}
