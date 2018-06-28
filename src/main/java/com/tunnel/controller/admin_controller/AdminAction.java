package com.tunnel.controller.admin_controller;

import com.github.pagehelper.PageInfo;
import com.tunnel.service.AdminService;
import com.tunnel.bean.Admin;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.utils.Base64Utils;
import com.tunnel.common.utils.CommonUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@SessionAttributes({AppConstant.ADMIN_SESSION_NAME,AppConstant.MEMBER_SESSION_NAME})
@Api(tags = "后台管理员模块",description = "后台管理员信息相关接口")
@RequestMapping("/rest/admin")
public class AdminAction {

    @Autowired
    private AdminService adminService;


    @ApiOperation("登录")
    @RequestMapping(value =  "/login",method = RequestMethod.POST)
    public BasicRet login(String username, String password, HttpSession session){
        BasicRet basicRet =  new BasicRet();

        password = Base64Utils.decode(password);

        Admin admin =  adminService.getByUserName(username);
        if(admin == null || !admin.getPassword().equals(CommonUtils.genMd5Password(password))){
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("登陆失败");
        }else{
            session.setAttribute(AppConstant.ADMIN_SESSION_NAME,admin);
            basicRet.setMessage("登陆成功");
            basicRet.setResult(BasicRet.SUCCESS);
        }

        return  basicRet;
    }

    @PostMapping("/updatePwd")
    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldpwd",value = "旧密码",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "newpwd",value = "新密码",required = true,paramType = "query" ),
    })
    public BasicRet updatePwd(@RequestParam(required = true) String oldpwd,@RequestParam(required = true) String newpwd,Model model){
        BasicRet basicRet =  new BasicRet();

        Admin admin = (Admin) model.asMap().get(AppConstant.ADMIN_SESSION_NAME);

        admin =  adminService.getById(admin.getId());
        oldpwd = Base64Utils.decode(oldpwd);
        if(admin == null || !admin.getPassword().equals(CommonUtils.genMd5Password(oldpwd))) {
            basicRet.setResult(BasicRet.ERR);
            basicRet.setMessage("登陆失败");
            return  basicRet;
        }

        Admin updateAdmin = new Admin();
        updateAdmin.setPassword(CommonUtils.genMd5Password(newpwd));
        updateAdmin.setId(admin.getId());
        adminService.updateByPrimaryKeySelective(updateAdmin);

        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("修改成功");
        return  basicRet;
    }

    @PostMapping("/updateAdmin")
    @ApiOperation("修改管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "姓名",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "telphone",value = "手机号",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "privilege",value = "权限",required = false,paramType = "query" ),
    })
    public  BasicRet updateAdmin(@RequestParam(required = true) long id,
                                 @RequestParam(required = true) String name,
                                 @RequestParam(required = true) String telphone,
                                 @RequestParam(required = false) String password,
                                 @RequestParam(required = false) String privilege,
                                  Model model){

        BasicRet basicRet =  new BasicRet();

        Admin admin = (Admin) model.asMap().get(AppConstant.ADMIN_SESSION_NAME);


        Admin dbAdmin  =  adminService.getById(id);
        if(dbAdmin == null){
            basicRet.setMessage("要修改的管理员不存在");
            basicRet.setResult(BasicRet.ERR);
            return  basicRet;
        }

        Admin updateAdmin =new Admin();
        updateAdmin.setId(id);
        updateAdmin.setName(name);
        updateAdmin.setTelphone(telphone);
        updateAdmin.setPrivilege(privilege);

        if(StringUtils.hasText(password)){
            updateAdmin.setPassword(CommonUtils.genMd5Password(password));
        }


        adminService.updateByPrimaryKeySelective(updateAdmin);

        if(admin.getId().equals(id)){
            dbAdmin =  adminService.getById(id);
            model.addAttribute(AppConstant.ADMIN_SESSION_NAME,dbAdmin);
        }

        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("修改成功");
        return  basicRet;
    }

    @PostMapping("/deleteAdmin")
    @ApiOperation("删除管理员")
    public  BasicRet deleteAdmin(@RequestParam(required = true) long id){
        BasicRet basicRet =  new BasicRet();
        adminService.deleteById(id);

        basicRet.setMessage("删除成功");
        basicRet.setResult(BasicRet.SUCCESS);
        return  basicRet;
    }

    @PostMapping("/listAdmin")
    @ApiOperation("管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "姓名",name = "name",paramType = "query",dataType = "string"),
    })
    public BasicRet listAdmin(@RequestParam(required = true,defaultValue = "1") int pageNo,
                             @RequestParam(required = true,defaultValue = "20") int pageSize,
                             @RequestParam(required = false,defaultValue = "") String name){
        BasicRet basicRet =  new BasicRet();
        Admin admin = new Admin();
        admin.setName(name);
        PageInfo pageInfo = adminService.listByPage(pageNo,pageSize,admin);

        basicRet.pageInfoData.setPageInfo(pageInfo);
        basicRet.setResult(BasicRet.SUCCESS);
        return  basicRet;
    }

    @PostMapping("/getAdmin")
    @ApiOperation("获取管理员信息")
    public AdminDataRet getAdmin(long id){

        AdminDataRet adminDataRet = new AdminDataRet();
        Admin admin =  adminService.getById(id);
        if(admin == null){
            adminDataRet.setMessage("该管理员不存在");
            adminDataRet.setResult(BasicRet.ERR);
            return  adminDataRet;
        }

        adminDataRet.setResult(BasicRet.SUCCESS);
        adminDataRet.setAdmin(admin);

        return  adminDataRet;
    }

    private  class  AdminDataRet extends  BasicRet{
        private  Admin admin;

        public Admin getAdmin() {
            return admin;
        }

        public void setAdmin(Admin admin) {
            this.admin = admin;
        }
    }

    @RequestMapping(value = "/addAdmin",method = RequestMethod.POST)
    @ApiOperation(value ="添加管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名",required = true,paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "name",value = "姓名",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "telphone",value = "手机号",required = true,paramType = "query" ),
            @ApiImplicitParam(name = "privilege",value = "权限",required = false,paramType = "query" ),
    })
    public  BasicRet addAdmin(Admin admin){
        BasicRet basicRet = new BasicRet();

        Admin dbAdmin =  adminService.getByUserName(admin.getUserName());
        if(dbAdmin != null){
            return new BasicRet(BasicRet.ERR,"该管理员名已存在，不可重复添加");
        }
        adminService.addAdmin(admin);
        return new BasicRet(BasicRet.SUCCESS,"管理员新增成功");
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ApiOperation(value = "退出登录")
    public  BasicRet logout(HttpSession session,Model model){
        model.asMap().remove(AppConstant.ADMIN_SESSION_NAME);
        session.removeAttribute(AppConstant.ADMIN_SESSION_NAME);
        BasicRet basicRet = new BasicRet();
        basicRet.setResult(BasicRet.SUCCESS);
        basicRet.setMessage("退出成功");
        return  basicRet;
    }

}
