package com.postek.email.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.postek.email.model.output.Result;
import com.postek.email.service.impl.captcha.CaptchaImageHelper;
import com.postek.email.service.impl.captcha.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @program: email
 * @description:
 * @author: Alleindrach@gmail.com
 * @create: 2019-07-25 15:29
 **/
@RestController
public class ServiceController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mail.fromMail.sender}")
    private String sender;

    @Value("${mail.fromMail.receiver}")
    private String receiver;

    @Autowired
    private CaptchaImageHelper captchaImageHelper;

    @Autowired
    private JavaMailSender javaMailSender;
    @RequestMapping("/captcha")
    public ResponseEntity<byte[]> captcha() {
        return captchaImageHelper.captchaImage(SecurityConstants.SECURITY_KEY);
    }

    /* *
     * @Description  http://localhost:8888/sendMail
     * @author dalaoyang
     * @email 397600342@qq.com
     * @method 发送文本邮件
     * @date
     * @param
     * @return
     */
    @RequestMapping("/send")
    public Result sendMail(@RequestBody JSONObject jsonData  )
//    data: { "HisMethod": "ZaiXianLiuYan",
// "Name": $("#txtName").val(),
// "UserType": $("select option:selected").val(),
// "CopName": $("#txtCopName").val(),
// "txtPhone": $("#txtPhone").val(),
// "txtEmail": $("#txtEmail").val(),
// "txtCopAddress": $("#txtCopAddress").val(),
// "txtYiXiang": $("#txtYiXiang").val(),
// "txtValidate": $("#txtValidate").val(),
// "txtDianHua": $("#txtDianHua").val(),
// "SiteID":5,
// "node":146010 },

    {
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(receiver);
        message.setSubject("[订购留言]");
        message.setText(jsonData.toJSONString());
        String captcha=jsonData.getString("txtValidate");
        if(!captchaImageHelper.checkCaptcha(request,captcha,SecurityConstants.SECURITY_KEY).isSuccess())
        {
            return Result.failWithMessage("验证码错误");
        }

        try {
            javaMailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
        return Result.successWithMessage("留言成功");
    }

    /* *
     * @Description  http://localhost:8888/sendHtmlMail
     * @author dalaoyang
     * @email 397600342@qq.com
     * @method 发送html邮件
     * @date
     * @param
     * @return
     */
    @RequestMapping("/sendHtmlMail")
    public String testHtmlMail() {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject("html mail");
            helper.setText(content, true);

            javaMailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
        return "success";
    }

    /* *
     * @Description http://localhost:8888/sendFilesMail
     * @author dalaoyang
     * @email 397600342@qq.com
     * @method 发送附件邮件
     * @date
     * @param
     * @return
     */
    @RequestMapping("/sendFilesMail")
    public String sendFilesMail() {
        String filePath="/Users/dalaoyang/Downloads/article_tag.sql";
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject("附件邮件");
            helper.setText("这是一封带附件的邮件", true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            javaMailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
        return "success";
    }

    /* *
     * @Description http://localhost:8888/sendInlineResourceMail
     * @author dalaoyang
     * @email 397600342@qq.com
     * @method 发送图片邮件
     * @date
     * @param
     * @return
     */
    @RequestMapping("/sendInlineResourceMail")
    public String sendInlineResourceMail() {
        String Id = "dalaoyang12138";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + Id + "\' ></body></html>";
        String imgPath = "/Users/dalaoyang/Downloads/dalaoyang.jpeg";
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject("这是有图片的邮件");
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(imgPath));
            helper.addInline(Id, res);

            javaMailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
        return "success";
    }
}
