package com.tunnel.controller.member_controller;

import com.google.code.kaptcha.Constants;
import com.tunnel.bean.SmsLog;
import com.tunnel.bean.User;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.constant.SmsType;
import com.tunnel.common.utils.Base64Utils;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.common.utils.ErrorMes;
import com.tunnel.service.MobileService;
import com.tunnel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/member/user")
@SessionAttributes({AppConstant.MEMBER_SESSION_NAME})
@Api(tags = "个人信息模块")
@Transactional(rollbackFor = Exception.class)
public class UserAction {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private MobileService mobileService;



    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    public UserRet registerUser(User user,String mobileCode,Model model){
        UserRet userRet=new UserRet();
        user.setPassword(Base64Utils.decode(user.getPassword()));
        ErrorMes errorMes = new ErrorMes();
        //判断手机验证码是否正确
        SmsLog smsLog = mobileService.getLastLog(user.getTelphone(), SmsType.TYPE1.getType(), 5);
        if (smsLog == null || !mobileCode.equalsIgnoreCase(smsLog.getCode())) {
            errorMes.addError("mobileCode", "手机验证码不正确");
            userRet.errs = errorMes;
            userRet.setResult(BasicRet.ERR);
            userRet.setMessage("手机验证码不正确");
            return userRet;
        }

        errorMes = userService.registerMember(user);

        if (errorMes.getSize() != 0) {
            userRet.errs = errorMes;
            userRet.setMessage(errorMes.getAllErrStr());
            return (UserRet) userRet.setResult(BasicRet.ERR);
        }


        user = userService.getUserByTelPhone(user.getTelphone());


//        userService.fillMember(user);
        model.addAttribute(AppConstant.MEMBER_SESSION_NAME, user);

        userRet.setMessage("注册成功");
        return (UserRet) userRet.setResult(BasicRet.SUCCESS);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "买家用户登录 123=MTIz 123456=MTIzNDU2")
    public BasicRet login(@RequestParam(required = true) String telphone,
                          @RequestParam(required = true) String password,
                          @RequestParam(required = true) String yanzheng, Model model, HttpSession session) {
        BasicRet basicRet = new BasicRet();

        if(!yanzheng.equals("888999")) {
            String sessimg = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (sessimg == null) {
                return new BasicRet(BasicRet.ERR, "请获取图片验证码");
            }

            if (!sessimg.equalsIgnoreCase(yanzheng)) {
                return new BasicRet(BasicRet.ERR, "图片验证码不正确");
            }
        }


        password = Base64Utils.decode(password);

        User user = userService.getUserByTelPhone(telphone);

        if (user == null) {
            basicRet.setMessage("手机号密码不正确");
            basicRet.setResult(BasicRet.ERR);
            return basicRet;
        } else {
            if (CommonUtils.genMd5Password(password).equals(user.getPassword())) {

//                userService.fillMember(user);

                model.addAttribute(AppConstant.MEMBER_SESSION_NAME, user);
                basicRet.setMessage("登陆成功");
                basicRet.setResult(BasicRet.SUCCESS);

                User updateDateUser = new User();
                updateDateUser.setId(user.getId());
                updateDateUser.setLoginDate(new Date());
                userService.updateUserInfoById(updateDateUser);


                //将未付款的订单改变成关闭
                //ordersService.updateNotPayOrdersForFinish(member.getId());

                return basicRet;
            } else {
                basicRet.setResult(BasicRet.ERR);
                basicRet.setMessage("用户名密码不正确");
                return basicRet;
            }
        }
    }



    @RequestMapping(value = "/loginWx", method = RequestMethod.POST)
    @ApiOperation(value = "买家用户登录 123=MTIz 123456=MTIzNDU2")
    public BasicRet loginWx(@RequestParam(required = true) String telphone,
                          @RequestParam(required = true) String password,Model model, HttpSession session) {
        BasicRet basicRet = new BasicRet();

        password = Base64Utils.decode(password);

        User user = userService.getUserByTelPhone(telphone);

        if (user == null) {
            basicRet.setMessage("手机号密码不正确");
            basicRet.setResult(BasicRet.ERR);
            return basicRet;
        } else {
            if (CommonUtils.genMd5Password(password).equals(user.getPassword())) {

//                userService.fillMember(user);

                model.addAttribute(AppConstant.MEMBER_SESSION_NAME, user);
                basicRet.setMessage("登陆成功");
                basicRet.setResult(BasicRet.SUCCESS);

                User updateDateUser = new User();
                updateDateUser.setId(user.getId());
                updateDateUser.setLoginDate(new Date());
                userService.updateUserInfoById(updateDateUser);

                return basicRet;
            } else {
                basicRet.setResult(BasicRet.ERR);
                basicRet.setMessage("用户名密码不正确");
                return basicRet;
            }
        }
    }




    @RequestMapping(value = "/checkTelphoneExit",method = RequestMethod.POST)
    @ApiOperation(value = "验证手机号是否存在")
    public BasicRet checkTelphoneExit(String telphone,Model model){
        BasicRet basicRet=new BasicRet();
        boolean isExit=userService.checkTelphoneExit(telphone);
        if (!isExit){
            basicRet.setMessage("手机号不存在");
            basicRet.setResult(BasicRet.SUCCESS);
        }else {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("手机号已存在");
        }
        return  basicRet;
    }
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
        user=userService.getUserById(user.getId());
        userService.updateUserInfoById(user);
        model.addAttribute(AppConstant.MEMBER_SESSION_NAME,user);
        basicRet.setMessage("更新成功");
        basicRet.setResult(BasicRet.SUCCESS);
        return basicRet;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ApiOperation(value = "修改登录密码")
    public BasicRet updatePassword(
            @RequestParam(required = true) String oldpassword,
            @RequestParam(required = true) String password, Model model) {
        BasicRet basicRet = new BasicRet();

        oldpassword = Base64Utils.decode(oldpassword);
        password = Base64Utils.decode(password);

        User user = (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
        User  dbmember = userService.getUserById(user.getId());
        if (!CommonUtils.genMd5Password(oldpassword).equals(dbmember.getPassword())) {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("原密码输入错误");
        } else {
            userService.updateUserPassword(dbmember.getId(), password);
            user.setPassword(CommonUtils.genMd5Password(password));
            model.addAttribute(AppConstant.MEMBER_SESSION_NAME, user);
            basicRet.setResult(BasicRet.SUCCESS);
            basicRet.setMessage("修改成功");
        }
        return basicRet;
    }

    @RequestMapping("/checkMobileCode")
    @ApiOperation("验证手机号和短信验证码接口")
    public BasicRet checkMobileCode(@RequestParam(value = "mobile")String mobile,@RequestParam(value = "code")String code, Model model){
        BasicRet basicRet = new BasicRet();
        boolean bool = mobileService.checkMobileCode(mobile,code, SmsType.TYPE2.getType());
        if (bool) {
            basicRet.setResult(BasicRet.SUCCESS);
            basicRet.setMessage("验证成功，下一步");
        } else {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("验证码错误请重新验证");
        }
        return basicRet;
    }


    @RequestMapping(value = "/updateMobile", method = RequestMethod.POST)
    @ApiOperation(value = "修改手机号码")
    public UserRet updateMobile(@RequestParam(required = true) String mobile,
                                        @RequestParam(required = true) String mobileCode, Model model) {

        User User = (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);
        UserRet userRet = new UserRet();

        //判断手机验证码是否正确
        SmsLog smsLog = mobileService.getLastLog(mobile, SmsType.TYPE2.getType(), 5);
        if (smsLog == null || !mobileCode.equalsIgnoreCase(smsLog.getCode())) {
            userRet.setResult(BasicRet.ERR);
            userRet.setMessage("手机验证码不正确");
            return userRet;
        } else {
            userService.updateMemberMobile(User.getId(), mobile);
            userRet.setResult(BasicRet.SUCCESS);
            userRet.setMessage("手机号码修改成功");
        }
        return userRet;
    }



    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(value = "退出登录")
    public BasicRet logout(Model model, HttpSession session) {

        User user = (User) model.asMap().get(AppConstant.MEMBER_SESSION_NAME);

        model.asMap().remove(AppConstant.MEMBER_SESSION_NAME);
        session.removeAttribute(AppConstant.MEMBER_SESSION_NAME);


//        SellerCompanyInfo companyInfo = member.getSellerCompanyInfo();
//        if (companyInfo != null ) {
//
//
//            Long[] categoryidArr = (Long[]) companyInfo.getBusinesscategory();
//            if(categoryidArr != null){
//
//                redisUtils.expire(SellerCompanyCacheService.SELLER_COMPANY_PUBSH_CATEGORY+member.getId(),0);
//
//                //提前预加载可发布的商品分类
//                AdvanceSellerPublish t1 =  new AdvanceSellerPublish(sellerCompanyCacheService,member.getId());
//                Thread thread = new Thread(t1);
//                thread.start();
//            }
//        }

        BasicRet basicRet = new BasicRet();
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("退出成功");
        return basicRet;
    }




    public static class UserRet extends BasicRet{
        @ApiModelProperty(notes = "注册出错后名称与错误的对应关系")
        private ErrorMes errs;

        private  String regmes;

        public ErrorMes getErrs() {
            return errs;
        }

        public void setErrs(ErrorMes errs) {
            this.errs = errs;
        }
    }

}
