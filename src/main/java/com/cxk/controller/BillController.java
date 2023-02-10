package com.cxk.controller;

import com.cxk.common.Code;
import com.cxk.common.Result;
import com.cxk.model.domain.Bill;
import com.cxk.model.domain.User;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.cxk.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/bill")
@Slf4j
public class BillController {


    @Resource
    private BillService billService;


    //todo 添加账单
    @PostMapping("/add")
    public Result addBill(@RequestBody BillAddRequest billAddRequest, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return null;
        }
        System.out.println("user = " + user);
        System.out.println("billAddRequest = " + billAddRequest);
        //todo 调用service层的方法，将数据存入数据库

        boolean addResult = billService.addBill(billAddRequest, user.getId());
        if (!addResult) {
            //todo 抛出异常  添加失败
            log.error("添加失败");
            return null;
        }
        log.info("账单添加成功");
        Result result = new Result();
        result.setCode(Code.SAVE_OK);
        result.setMsg("账单添加成功");
        return result;


    }

    //todo 查询账单列表
    @GetMapping("/list")
    public Result getAllBill(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return null;
        }
        Integer userId = user.getId();

        //todo 调用service层的方法，查询账单列表
        List<BillAddRequest> billList = billService.getBillList(userId);
        if (billList == null) {
            //todo 抛出异常  查询失败 没有查询到数据
            log.error("查询失败,没有查询到数据");
            return null;
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.GET_OK);
        result.setMsg("查询成功");
        result.setData(billList);
        return result;
    }


    //todo 修改账单
    @PutMapping("/update")
    public Result updateBill(@RequestBody Bill bill, HttpServletRequest request) {
        //todo 判断用户是否登录
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return null;
        }
        //todo 调用service层的方法，修改账单
        Bill oldBill = billService.getOldBill(bill.getId());
        boolean updateResult = billService.updateBill(bill, oldBill);

        if (!updateResult) {
            //todo 抛出异常  修改失败
            log.error("修改失败，未知错误");
            return null;
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.UPDATE_OK);
        result.setMsg("修改成功");
        return result;

    }

    //todo 删除账单 注意这里的删除为逻辑删除
    @DeleteMapping("/delete") //todo 传入id
    public Result deleteBill(@RequestParam Integer id, HttpServletRequest request) {
        //todo 判断用户是否登录
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return null;
        }
        //todo 调用service层的方法，删除账单
        log.info("id = " + id);
        boolean deleteResult = billService.removeById(id);
        if (!deleteResult) {
            //todo 抛出异常  删除失败
            log.error("删除失败，未知错误");
            return null;
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.DELETE_OK);
        result.setMsg("删除成功");
        return result;
    }



}
