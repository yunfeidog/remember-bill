package com.cxk.controller;

import com.cxk.common.Code;
import com.cxk.common.Result;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.model.domain.request.DateOrCategoryRequest;
import com.cxk.model.domain.request.DateRequest;
import com.cxk.model.domain.request.StatisticsRequest;
import com.cxk.model.domain.response.DateOrCategoryResponse;
import com.cxk.model.domain.response.StatisticsResponse;
import com.cxk.model.entity.Bill;
import com.cxk.model.entity.User;
import com.cxk.service.BillService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.cxk.constant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/bill")
@Slf4j
@Api(tags = "账单模块管理接口")
public class BillController {
    @Resource
    private BillService billService;


    /**
     * 添加账单信息
     * todo 前端
     *
     * @param billAddRequest 账单信息
     * @param request        请求
     * @return Result
     */
    @PostMapping("/add")
    public Result addBill(@RequestBody BillAddRequest billAddRequest, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return new Result(Code.ERR, "用户未登录");
        }
        log.info("user = {}", user);
        log.info("billAddRequest = {}", billAddRequest);
        //todo 调用service层的方法，将数据存入数据库
        boolean addResult = billService.addBill(billAddRequest, user.getId());
        if (!addResult) {
            //todo 抛出异常  添加失败
            log.error("添加失败");
            return new Result(Code.ERR, "账单添加失败,可能是没找到用户的id");
        }
        log.info("账单添加成功");
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("账单添加成功");
        return result;
    }


    /**
     * 查询全部账单信息
     *
     * @param request 请求
     * @return Result
     */
    @GetMapping("/list")
    public Result getAllBill(HttpServletRequest request) {
        Integer userId = isLogin(request);
        if (userId == -1) {
            return new Result(Code.ERR, "用户未登录");
        }

        //todo 调用service层的方法，查询账单列表
        List<BillAddRequest> billList = billService.getBillList(userId);
        if (billList == null) {
            //todo 抛出异常  查询失败 没有查询到数据
            log.error("查询失败,没有查询到数据");
            return new Result(Code.ERR, "查询失败,没有查询到数据");
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(billList);
        return result;
    }


    /**
     * 根据日期或者类别查询账单信息
     *
     * @param dateOrCategoryRequest 日期或者类别
     * @param request               请求
     * @return 对应的账单信息列表
     */
    @PostMapping("/listByDateOrCategory")
    public Result getBillListByDateOrCategory(@RequestBody DateOrCategoryRequest dateOrCategoryRequest, HttpServletRequest request) {
        Integer userId = isLogin(request);
        if (userId == -1) {
            return new Result(Code.ERR, "用户未登录");
        }
        String category = dateOrCategoryRequest.getCategory();
        Date date = dateOrCategoryRequest.getDate();
        log.info("date = " + date);
        List<DateOrCategoryResponse> list = billService.getBillListByDateOrCategory(category, date, userId);
        log.info("list= " + list);
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(list);
        return result;

    }


    /**
     * 按照月份查询此人的全部账单信息
     */
    @PostMapping("/queryByMonth")
    public Result queryByMonth(@RequestBody DateRequest dateRequest, HttpServletRequest request) {
        Integer login = isLogin(request);
        if (login == -1) {
            return new Result(Code.ERR, "用户未登录");
        }
        String month = dateRequest.getDate();
        List<Bill> list = billService.getBillByMonth(month, login);
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(list);
        return result;
    }


    @PostMapping("/statisticsByYear")
    public Result statisticByYear(@RequestBody StatisticsRequest statisticsRequest, HttpServletRequest request) {
        Integer userId = isLogin(request);
        if (userId == -1) {
            return new Result(Code.ERR, "用户未登录");
        }
        String year = statisticsRequest.getYear();
        StatisticsResponse list = billService.statisticsByYear(year, userId);
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(list);
        return result;
    }

    @PostMapping("/statisticsByMonth")
    public Result statisticsByMonth(@RequestBody StatisticsRequest statisticsRequest, HttpServletRequest request) {
        Integer userId = isLogin(request);
        if (userId == -1) {
            return new Result(Code.ERR, "用户未登录");
        }
        String month = statisticsRequest.getMonth();
        log.info("month = " + month );
        String year = statisticsRequest.getYear();
        if (month.length()<2) {
            month = "0" + month;
            log.info("修改过的month =" + month );
        }
        String date = year + "-" + month;
        log.info("date = " + date);
        StatisticsResponse list = billService.statisticsByMonth(date, userId);
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(list);
        return result;
    }

    /**
     * 修改账单信息
     *
     * @param bill    原始账单信息
     * @param request 请求
     * @return 修改是否成功
     */
    @PutMapping("/update")
    public Result updateBill(@RequestBody Bill bill, HttpServletRequest request) {
        //todo 判断用户是否登录
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return new Result(Code.ERR, "用户未登录");
        }
        //todo 调用service层的方法，修改账单
        Bill oldBill = billService.getOldBill(bill.getId());
        boolean updateResult = billService.updateBill(bill, oldBill);

        if (!updateResult) {
            //todo 抛出异常  修改失败
            log.error("修改失败，未知错误");
            return new Result(Code.ERR, "修改失败，未知错误");
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("修改成功");
        return result;

    }


    /**
     * 删除账单信息
     *
     * @param id      账单id
     * @param request 请求
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public Result deleteBill(@PathVariable Integer id, HttpServletRequest request) {
        //todo 删除账单 注意这里的删除为逻辑删除
        //todo 判断用户是否登录
        //todo 传入id
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return new Result(Code.ERR, "用户未登录");
        }
        //todo 调用service层的方法，删除账单
        log.info("id = " + id);
        boolean deleteResult = billService.removeById(id);
        if (!deleteResult) {
            //todo 抛出异常  删除失败
            log.error("删除失败，未知错误");
            return new Result(Code.ERR, "删除失败，未知错误");
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("删除成功");
        return result;
    }

    /**
     * 判断用户是否登录
     * 1. 从session中获取用户信息
     * 2. 判断用户信息是否为空
     * 3. 如果为空，返回-1
     * 4. 如果不为空，返回用户id
     */
    private static Integer isLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            return -1;
        }
        return user.getId();
    }


    /**
     * 根据id查询此人的全部账单信息
     *
     * @param id      账单id
     * @param request 请求
     * @return 账单信息
     */
    @GetMapping("/{id}")
    public Result getBillById(@PathVariable Integer id, HttpServletRequest request) {
        //todo 根据id查询账单
        //todo 判断用户是否登录
        //todo 传入id
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return new Result(Code.ERR, "用户未登录");
        }
        //todo 调用service层的方法，删除账单
        log.info("id = " + id);
        Bill bill = billService.getById(id);
        if (bill == null) {
            //todo 抛出异常  删除失败
            log.error("查询失败，未知错误");
            return new Result(Code.ERR, "查询失败，未知错误");
        }
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("查询成功");
        result.setData(bill);
        return result;
    }



    //接收前端的图片，处理得到账单的信息
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        //todo 判断用户是否登录
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            //todo 抛出异常  用户未登录
            log.error("用户未登录");
            return new Result(Code.ERR, "用户未登录");
        }
        //todo 调用service层的方法，处理图片
        //todo 返回数据
        Result result = new Result();
        result.setCode(Code.OK);
        result.setMsg("上传成功");
        return result;
    }




}
