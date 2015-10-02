import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by inksmallfrog on 15-9-19.
 */
//用于抓取教务系统中的成绩单

//Gui界面
public class LoginGui extends JFrame{
    public String studentId = "2014302580136";              //学号
    public String password = "19951014";                    //密码
    public String identifyingCode;                          //验证码

    public String cookie;                                   //Cookie字符串

    final File identityPic = new File("identityPic.png");   //验证码存储
    final File scoreChart = new File("scoreChart.html");    //成绩单文件

    JLabel studentIdLabel;                                  //学号标签
    JTextField studentIdText;                               //学号文本框

    JLabel passwordLabel;                                   //密码标签
    JPasswordField passwordText;                            //密码文本框

    JLabel identifyingLabel;                                //验证码标签
    JTextField identifyingText;                             //验证码文本框
    JLabel identifyingPic;                                  //验证码图片

    JButton commitButton;                                   //提交按钮

    //创建登录GUI界面
    public LoginGui(){
        //设置窗口属性
        setSize(800, 600);
        setVisible(true);
        setLayout(null);

        //创建Gui
        CreateStudentIdGui();
        CreatePasswordGui();
        CreateIdentifyCodeGui();
        CreateCommitButton();
    }

    //创建学号gui
    public void CreateStudentIdGui(){
        //创建学号标签
        studentIdLabel = new JLabel("学号");
        studentIdLabel.setBounds(250, 150, 50, 20);

        //创建学号文本框
        studentIdText = new JTextField();
        studentIdText.setBounds(350, 150, 150, 20);
        studentIdText.setText("2014302580136");

        //文本框焦点监听事件
        studentIdText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                studentId = studentIdText.getText();
            }
        });

        //添加gui
        add(studentIdLabel);
        add(studentIdText);
    }

    //创建密码gui
    public void CreatePasswordGui(){
        //创建密码标签
        passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(250, 250, 50, 20);

        //创建密码文本框
        passwordText = new JPasswordField();
        passwordText.setText("19951014");
        passwordText.setBounds(350, 250, 150, 20);

        //添加密码文本框监听时间
        passwordText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                char[] buffer = passwordText.getPassword();
                password = String.copyValueOf(buffer);
            }
        });

        //添加到gui
        add(passwordLabel);
        add(passwordText);
    }

    //创建验证码gui
    public void CreateIdentifyCodeGui(){
        //创建验证码标签
        identifyingLabel = new JLabel("验证码");
        identifyingLabel.setBounds(250, 350, 50, 20);

        //创建验证码文本框
        identifyingText = new JTextField();
        identifyingText.setBounds(350, 350, 150, 20);

        //添加验证码焦点事件监听
        identifyingText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得验证码图片
                GetIdentifyingCode();
                Image icon = Toolkit.getDefaultToolkit().createImage(identityPic.getPath());
                identifyingPic.setIcon(new ImageIcon(icon));
            }

            @Override
            public void focusLost(FocusEvent e) {
                identifyingCode = identifyingText.getText();
            }
        });
        //添加文本框键盘监听
        identifyingText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                identifyingCode = identifyingText.getText();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //提交信息
                    if(!CommitInfo()){
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        //创建验证码显示标签
        identifyingPic = new JLabel();
        identifyingPic.setBounds(550, 340, 150, 40);

        //添加gui
        add(identifyingLabel);
        add(identifyingText);
        add(identifyingPic);

        //将窗口焦点设置到验证码文本框
        identifyingText.requestFocusInWindow();
    }

    //创建提交按钮gui
    public void CreateCommitButton(){
        //创建按钮
        commitButton = new JButton("查看");
        commitButton.setBounds(550, 400, 150, 40);

        //添加鼠标事件监听
        commitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!CommitInfo()){
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        //添加gui
        add(commitButton);
    }

    //获取验证码
    public void GetIdentifyingCode(){
        //登录地址
        String url = "http://210.42.121.241/";

        //获取登录网页的Cookie
        com.github.kevinsawicki.http.HttpRequest request = com.github.kevinsawicki.http.HttpRequest.get(url);
        cookie = request.header("Set-Cookie");

        //验证码地址
        url = "http://210.42.121.241/servlet/GenImg";

        //获取验证码图片及新Cookie
        request = com.github.kevinsawicki.http.HttpRequest.get(url).header("Cookie", cookie);
        request.receive(identityPic);
        cookie = request.header("Set-Cookie");
    }

    //获取成绩单
    public boolean CommitInfo(){
        //创建提交表单
        Map<String, String> postMap = new HashMap();
        postMap.put("id", studentId);
        postMap.put("pwd", password);
        postMap.put("xdvfb", identifyingCode);

        //登录地址
        String url = "http://210.42.121.241/servlet/Login";

        //利用Cookie和表单信息模拟登录
        com.github.kevinsawicki.http.HttpRequest request = com.github.kevinsawicki.http.HttpRequest.get(url)
                .header("Cookie", cookie)
                .form(postMap);

        String body = request.body();

        if(body.contains("验证码错误")){
            identifyingText.setText("验证码错误");
            identifyingText.requestFocusInWindow();
            return false;
        }
        else if(body.contains("用户名/密码错误")){
            identifyingText.setText("用户名/密码错误");
            studentIdText.requestFocusInWindow();
            return false;
        }

        System.out.println(request.code());

        //成绩单查询地址
        url = "http://210.42.121.241/servlet/Svlt_QueryStuScore" +
                "?year=0&term=&learnType=&scoreFlag=0&t=Fri%20Sep%2025%202015%2019:10:05%20GMT+0800%20(CST)";

        //取得成绩单
        request = com.github.kevinsawicki.http.HttpRequest.get(url)
                .header("Cookie", cookie);

        request.receive(scoreChart);

        MainGUI.EnterTableGui();

        return true;
    }
}