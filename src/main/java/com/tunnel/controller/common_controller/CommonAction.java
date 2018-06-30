package com.tunnel.controller.common_controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.tunnel.bean.SmsLog;
import com.tunnel.bean.User;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.constant.SmsType;
import com.tunnel.controller.member_controller.UserAction;
import com.tunnel.service.MobileService;
import com.tunnel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.PrivateKey;

@Controller
@RequestMapping("/rest/common")
@Api(tags = "通用模块")
public class CommonAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private UserService userService;
    @Autowired
    private MobileService mobileService;


    @RequestMapping(value = "/ImgCode", method = RequestMethod.GET)
    @ApiOperation(value = "图片验证码")
    public ResponseEntity<InputStreamResource> ImgCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, createText);

            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return null;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        String contentType = "image/jpeg";
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(new ByteArrayInputStream(captchaChallengeAsJpeg)));
    }

    /**
     *获取个人信息
     * @author xiazy
     * @date  2018/6/25 20:04
     * @param
     * @return com.tunnel.controller.mod_user.UserAction.UserRet
     */
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST)
    @ApiOperation("获取个人信息接口(session)")
    public UserInfoRet getUserInfo(Model model){
        UserInfoRet userRet=new UserInfoRet();
        User user= (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
        if (user==null){
            userRet.setMessage("用户未登录");
            userRet.setResult(BasicRet.TOKEN_ERR);
        }else{
            userRet.setResult(BasicRet.SUCCESS);
            userRet.setMessage("获取成功");
            userRet.setData(user);
        }
        return userRet;
    }

    @RequestMapping(value = "/db/getUserInfo",method = RequestMethod.POST)
    @ApiOperation("获取个人信息接口(数据库)")
    public UserInfoRet getUserInfoFromDb(Model model){
        UserInfoRet userRet=new UserInfoRet();
        User user= (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
        if (user==null){
            userRet.setMessage("用户未登录");
            userRet.setResult(BasicRet.TOKEN_ERR);
        }else{
            user=userService.getUserById(user.getId());
            userRet.setResult(BasicRet.SUCCESS);
            userRet.setMessage("获取成功");
            userRet.setData(user);
        }
        return userRet;
    }

    private  class  UserInfoRet extends  BasicRet{
        private User data;

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }
    }


}
