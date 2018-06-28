package com.tunnel.controller.member_controller;

import com.tunnel.bean.User;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/member/user")
@Api(tags = "个人信息模块")
public class UserAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;


    /**
     *编辑个人信息
     * @author xiazy
     * @date  2018/6/25 20:13
     * @param request
     * @param user
     * @param model
     * @return com.tunnel.common.bean.BasicRet
     */
    @RequestMapping(value = "/editUserInfo",method = RequestMethod.POST)
    @ApiOperation("编辑个人信息")
    public BasicRet editUserInfo(HttpServletRequest request,User user,Model model){
        BasicRet basicRet=new BasicRet();
        User userSession = (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
        user=userService.getMemberById(user.getId());
        userService.updateUserInfoById(user);
        model.addAttribute(AppConstant.MEMBER_SESSION_NAME,user);
        basicRet.setMessage("更新成功");
        basicRet.setResult(BasicRet.SUCCESS);
        return basicRet;
    }




    public static class UserRet extends BasicRet{
        private User user=new User();

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

}
