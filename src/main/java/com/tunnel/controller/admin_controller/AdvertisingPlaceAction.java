package com.tunnel.controller.admin_controller;

import com.tunnel.common.constant.AppConstant;
import io.swagger.annotations.Api;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

@RestController
@RequestMapping("/rest/admin/adtPlace")
@SessionAttributes({AppConstant.ADMIN_SESSION_NAME})
@Api(tags = "广告位相关", description = "广告位")
@Transactional(rollbackFor = Exception.class)
public class AdvertisingPlaceAction {

}
