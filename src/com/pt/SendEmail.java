package com.pt;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @author nate-pt
 * @date 2021/6/23 11:42
 * @Since 1.8
 * @Description 发送qq邮件
 */
public class SendEmail {

    public static void main(String[] args) throws GeneralSecurityException, MessagingException {
        // 设置发件人
        String from = "xxxxxx@qq.com";

        // 设置收件人
        String to = "xxxxxx@qq.com";

        // 设置邮件发送的服务器
        String host = "smtp.qq.com";

        // 获取当前系统的属性
        Properties properties = System.getProperties();

        // SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);

        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //设置系统属性
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");

        //获取发送邮件会话、获取第三方登录授权码
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "xxxxxx");
            }
        });

        Message message = new MimeMessage(session);

        //防止邮件被当然垃圾邮件处理，披上Outlook的马甲
        message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");

        message.setFrom(new InternetAddress(from));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        //邮件标题
        message.setSubject("This is the subject line!");

        BodyPart bodyPart = new MimeBodyPart();

        bodyPart.setText("我发送了文件给你");

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(bodyPart);



        //附件
        bodyPart = new MimeBodyPart();
        String fileName = "D:\\bcprov-jdk16-145.jar";
        DataSource dataSource = new FileDataSource(fileName);
        bodyPart.setDataHandler(new DataHandler(dataSource));
        bodyPart.setFileName("文件显示的名称");
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);

        Transport.send(message);
        System.out.println("mail transports successfully");


    }

}
