package com.tunnel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tunnel.bean.Admin;
import com.tunnel.bean.AdminExample;
import com.tunnel.common.utils.Base64Utils;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    public Admin getByUserName(String username){
        return  adminMapper.getByUserName(username);
    }

    public Admin getById(long id){
        return adminMapper.selectByPrimaryKey(id);
    }

    public void updateByPrimaryKeySelective(Admin admin){
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public  void  deleteById(long id){
        adminMapper.deleteByPrimaryKey(id);
    }

    public PageInfo listByPage(int pageNo, int pageSize, Admin admin){
        PageHelper.startPage(pageNo,pageSize);
        AdminExample example =  new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        if(admin != null){
            if(StringUtils.hasText(admin.getName())){
                criteria.andNameLike("%"+admin.getName()+"%");
            }
        }

        List<Admin> list =  adminMapper.selectByExample(example);
        return  new PageInfo(list);
    }

    /**
     * 新增管理员
     * @param admin
     * @return
     */
    public int addAdmin(Admin admin){
        String password = Base64Utils.decode(admin.getPassword());
        admin.setPassword(CommonUtils.genMd5Password(password));
        return adminMapper.insertSelective(admin);
    }
}
