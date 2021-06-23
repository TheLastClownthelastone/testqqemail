package com.pt;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author nate-pt
 * @date 2021/6/23 14:30
 * @Since 1.8
 * @Description 从qq邮箱发送邮件给网易邮箱
 */
public class QQToWANYI {

    public static void main(String[] args) throws MessagingException {
        // 创建属性对象用来接受发送邮件的属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth","true");
        // 填写对应的发送方的服务器
        props.put("mail.smtp.host","smtp.qq.com");
        // 设置端口号 qq邮箱端口为587
        props.put("mail.smtp.port","587");
        // 写信人的账号
        props.put("mail.smtp.user","xxxxxxx@qq.com");
        // 写信人的密钥
        props.put("mail.smtp.password","xxxxxxx");

        // 构建授权信息，用于进行smtp身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.smtp.user");
                String password = props.getProperty("mail.smtp.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 使用环境属性和授权信心构建邮箱会话
        Session session = Session.getInstance(props,authenticator);

        // 创建邮件信息
        MimeMessage message = new MimeMessage(session);

        // 设置发送邮件人地址
        message.setFrom(new InternetAddress(props.getProperty("mail.smtp.user")));

        // 设置接受邮件人地址
        message.setRecipients(Message.RecipientType.TO,"xxxxx@163.com");

        // 设置邮箱的标题
        message.setSubject("跨服务器发送邮箱");

        // 设置邮箱的内容体
        message.setContent("终于跨服务器了","text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);


    }
}
