package com.house.apigateway.controller;

import com.house.apigateway.bean.User;
import com.house.apigateway.common.ResultMsg;
import com.house.apigateway.common.helper.UserHelper;
import com.house.apigateway.service.AccountService;
import com.house.apigateway.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/13
 * @ Description：
 * @ throws
 */
@Controller
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AgencyService agencyService;


//----------------------------注册流程-------------------------------------------


    @RequestMapping(value="accounts/register",method={RequestMethod.POST,RequestMethod.GET})
    public String accountsSubmit(User account, ModelMap modelMap){
        if (account == null || account.getName() == null) {
            return "/user/accounts/register";
        }
        ResultMsg retMsg =  UserHelper.validate(account);

        if (retMsg.isSuccess() ) {
            boolean exist = accountService.isExist(account.getEmail());
            if (!exist) {
                accountService.addAccount(account);
                modelMap.put("email", account.getEmail());
                return "/user/accounts/registerSubmit";
            }else {
                return "redirect:/accounts/register?" + ResultMsg.errorMsg("邮箱已被占用").asUrlParams();
            }
        }else {
            return "redirect:/accounts/register?" + ResultMsg.errorMsg("参数错误").asUrlParams();
        }
    }
}
