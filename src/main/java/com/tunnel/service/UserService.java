package com.tunnel.service;

import com.tunnel.bean.User;
import com.tunnel.bean.UserExample;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.common.utils.ErrorMes;
import com.tunnel.common.utils.StringUtils;
import com.tunnel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户服务service
 *
 * @author xiazy
 * @create 2018-06-25 19:59
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    public void updateUserInfoById(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *修改登录密码
     * @author xiazy
     * @date  2018/6/27 18:28
     * @param id
     * @param password
     * @return void
     */
    public void updateUserPassword(long id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(CommonUtils.genMd5Password(password));
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     *修改手机号码
     * @author xiazy
     * @date  2018/6/27 18:28
     * @param id
     * @param mobile
     * @return void
     */
    public void updateMemberMobile(long id, String mobile) {
        User user = new User();
        user.setId(id);
        user.setTelphone(mobile);
        userMapper.updateByPrimaryKeySelective(user);
    }


    /**
     *注册用户
     * @author xiazy
     * @date  2018/6/30 9:41
     * @param user
     * @return com.tunnel.common.utils.ErrorMes
     */
    public ErrorMes registerMember(User user){

        ErrorMes errorMes = new ErrorMes();

        checkRegisterMember(errorMes, user);

        if (errorMes.getSize() != 0) {
            return errorMes;
        }

        if (!user.getTelphone().equals(AppConstant.MOCK_MOBILE)&&checkTelphoneExit(user.getTelphone())) {
            errorMes.addError("telphone", "该手机号码已存在");
        }


        if (errorMes.getSize() == 0) {
            user.setPassword(CommonUtils.genMd5Password(user.getPassword()));
            user.setRegisterDate(new Date());
            userMapper.insertSelective(user);
        }

        return errorMes;
    }
    private void checkRegisterMember(ErrorMes mes, User user) {
        if (!StringUtils.hasText(user.getPassword())) {
            mes.addError("password", "密码不可为空");
        }

        if (!StringUtils.hasText(user.getTelphone())) {
            mes.addError("mobile", "手机号不可为空");
        }
    }

    public boolean checkTelphoneExit(String telphone){
        User user=userMapper.selectByTelphone(telphone);
        if(user!=null){
            return  true;
        }else {
            return  false;
        }
    }

    public User getUserByTelPhone(String telphone){
        return userMapper.selectByTelphone(telphone);
    }


}
