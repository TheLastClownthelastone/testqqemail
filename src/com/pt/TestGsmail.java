package com.pt;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author nate-pt
 * @date 2021/6/23 15:32
 * @Since 1.8
 * @Description 邮箱中附带附件信息或者是图片信息
 */
public class TestGsmail {

    public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
        Properties properties = new Properties();

        // 表示SMTP发送邮件，必须进行身份验证
        properties.put("mail.smtp.auth","true");

        properties.put("mail.smtp.host","smtp.qq.com");



        properties.put("mail.smtp.user","xxxxxx@qq.com");

        properties.put("mail.smtp.password","xxxxx");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = properties.getProperty("mail.smtp.user");
                String password = properties.getProperty("mail.smtp.password");
                return new PasswordAuthentication(userName, password);
            }
        };

        // 创建会话

        Session  session =  Session.getInstance(properties,authenticator);

        // 创建邮件
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));

        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xxxxx@163.com"));

        message.setSubject("发送至阿里邮箱");

        //message.setContent(new File("C:\\Users\\pt\\Pictures\\Camera Roll\\3.jpg"),"text/html");

        MimeMultipart mm = new MimeMultipart();

        MimeBodyPart bodyPart = new MimeBodyPart();

        DataHandler dataHandler = new DataHandler(new FileDataSource(new File("C:\\Users\\pt\\Pictures\\Camera Roll\\3.jpg")));
        bodyPart.setDataHandler(dataHandler);

        bodyPart.setFileName(MimeUtility.encodeText(dataHandler.getName()));

        mm.addBodyPart(bodyPart);

        message.setContent(mm);

        Transport.send(message);
    }
}
