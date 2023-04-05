package com.cxk.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxk.mapper.BillMapper;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.model.domain.response.BillResponse;
import com.cxk.model.domain.response.DateOrCategoryResponse;
import com.cxk.model.domain.response.OCRResponse;
import com.cxk.model.domain.response.StatisticsResponse;
import com.cxk.model.entity.Bill;
import com.cxk.service.BillService;
import com.cxk.utils.OCRUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 针对表【tb_bill(账单表)】的数据库操作Service实现
 * @createDate 2023-02-10 16:14:48
 */
@Service
@Slf4j
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill>
        implements BillService {

    @Resource
    private BillMapper billMapper;

    @Override
    public boolean addBill(BillAddRequest bill, Integer userId) {
        if (userId == null) {
            //todo 抛出异常  用户id为空
            log.error("用户id为空");
            return false;
        }

        if (bill == null) {
            //todo 抛出异常  账单信息为空
            log.error("账单信息为空");
            return false;
        }

        Bill bill1 = new Bill();
        bill1.setUserId(userId);
        bill1.setMoney(bill.getBillAmount());
        bill1.setCategory(bill.getBillCategory());
        bill1.setBillDate(bill.getBillDate());
        bill1.setShop(bill.getBillShopkeeper());
        bill1.setRemark(bill.getBillRemark());

        log.info("bill1:{}", bill1);

        boolean saveResult = this.save(bill1);
        if (!saveResult) {
            //todo 抛出异常  账单信息保存失败
            log.error("账单信息保存失败");
            return false;
        }
        return true;

    }

    @Override
    public List<BillAddRequest> getBillList(Integer userId) {
        if (userId == null) {
            //todo 抛出异常  用户id为空
            log.error("用户id为空");
            return null;
        }
        //根据用户id查询账单信息
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Bill> billList = this.list(queryWrapper);
        if (billList == null) {
            //todo 抛出异常  账单信息为空
            log.error("账单信息为空");
            return null;
        }

        //将账单信息转换为前端需要的格式
        List<BillAddRequest> billList1 = billList.stream().map(bill -> {
            BillAddRequest billAddRequest = new BillAddRequest();
            billAddRequest.setBillAmount(bill.getMoney());
            billAddRequest.setBillDate(bill.getBillDate());
            billAddRequest.setBillShopkeeper(bill.getShop());
            billAddRequest.setBillRemark(bill.getRemark());
            billAddRequest.setBillCategory(bill.getCategory());
            return billAddRequest;
        }).collect(Collectors.toList());
        log.info("billList1:{}", billList1);
        return billList1;
    }


    @Override
    public Bill getOldBill(Integer id) {
        if (id == null) {
            //todo 抛出异常  账单id为空
            log.error("账单id为空");
            return null;
        }
        Bill bill = this.getById(id);
        if (bill == null) {
            //todo 抛出异常  账单信息为空
            log.error("账单信息为空");
            return null;
        }
        return bill;
    }

    @Override
    public boolean updateBill(Bill newBill, Integer billId) {
        Bill oldBill = this.getOldBill(billId);
        if (newBill == null) {
            //todo 抛出异常  账单信息为空
            log.error("新改的账单信息为空");
            return false;
        }

        if (oldBill == null) {
            //todo 抛出异常  账单信息为空
            log.error("旧的账单信息为空");
            return false;
        }
        newBill.setId(oldBill.getId());
        boolean updateResult = this.updateById(newBill);
        if (!updateResult) {
            //todo 抛出异常  账单信息更新失败
            log.error("账单信息更新失败");
            return false;
        }
        log.info("账单信息更新成功");
        return true;

    }

    @Override
    public List<DateOrCategoryResponse> getBillListByDateOrCategory(String category, String date, Integer userId) {
        if (userId == null) {
            //todo 抛出异常  用户id为空
            log.error("用户id为空");
            return null;
        }
        if (StringUtils.isBlank(category) && date == null) {
            //todo 抛出异常  分类和日期都为空
            log.error("分类和日期都为空");
            return null;
        }
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId);
        if (StringUtils.isNotBlank(category)) {
            billQueryWrapper.eq("category", category);
        }
        if (date != null) {
            //查询当月的账单信息，date的格式为2023-02
            String dateStr = date + "%";
            billQueryWrapper.like("bill_date", dateStr);
        }
        List<Bill> billList = billMapper.selectList(billQueryWrapper);
        log.info("userId=" + userId);
        log.info("category= " + category);
        log.info("date=" + date);
        if (billList == null) {
            //todo 抛出异常  账单信息为空
            log.error("账单信息为空，没有查询到任何数据");
            return null;
        }
        log.info("billList= " + billList);

        //将账单信息转换为前端需要的格式 并且按照日期分类
        List<DateOrCategoryResponse> dateOrCategoryResponseList = new ArrayList<>();
        HashSet<String> stringHashSet = new HashSet<>();
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            Date billDate = bill.getBillDate();
            //将日期格式转换为yyyy-MM的格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = simpleDateFormat.format(billDate);
            if (stringHashSet.contains(dateStr)) {
                //如果包含这个日期，将账单信息添加到这个日期的账单信息中
                for (int j = 0; j < dateOrCategoryResponseList.size(); j++) {
                    DateOrCategoryResponse dateOrCategoryResponse = dateOrCategoryResponseList.get(j);
                    if (dateOrCategoryResponse.getDate().equals(dateStr)) {
                        //将bill转换为billResponse,使用spring的BeanUtils工具类
                        BillResponse billResponse = new BillResponse();
                        BeanUtils.copyProperties(bill, billResponse);
                        dateOrCategoryResponse.getBills().add(billResponse);
                    }
                }
            } else {
                //如果没有包含这个日期，新建一个日期的账单信息
                DateOrCategoryResponse dateOrCategoryResponse = new DateOrCategoryResponse();
                dateOrCategoryResponse.setDate(dateStr);
                List<BillResponse> bills = new ArrayList<>();
                //将bill转换为billResponse,使用spring的BeanUtils工具类
                BillResponse billResponse = new BillResponse();
                BeanUtils.copyProperties(bill, billResponse);
                bills.add(billResponse);
                dateOrCategoryResponse.setBills(bills);
                dateOrCategoryResponseList.add(dateOrCategoryResponse);
                stringHashSet.add(dateStr);

            }
        }
        return dateOrCategoryResponseList;
    }


    @Override
    public List<Bill> getBillByMonth(String month, Integer userId) {
        //按照月份查询账单信息
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId);
        //数据库中的日期格式为yyyy-MM-dd
        billQueryWrapper.like("bill_date", month);
        List<Bill> billList = billMapper.selectList(billQueryWrapper);
        if (billList == null) {
            log.error("账单信息为空，没有查询到任何数据");
            throw new RuntimeException("账单信息为空，没有查询到任何数据");
        }
        return billList;
    }

    public StatisticsResponse statisticsByYear(String date, Integer userId) {
        //统计这一年每个月的收入和支出
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.like("bill_date", date);
        billQueryWrapper.eq("user_id", userId);
        List<Bill> billList = billMapper.selectList(billQueryWrapper);
        if (billList == null) {
            log.error("账单信息为空，没有查询到任何数据");
            throw new RuntimeException("账单信息为空，没有查询到任何数据");
        }

        //计算每个月的收入和支出
        List<BigDecimal> incomeList = new ArrayList<>();
        List<BigDecimal> expenseList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            BigDecimal income = new BigDecimal(0);
            BigDecimal expense = new BigDecimal(0);
            for (int j = 0; j < billList.size(); j++) {
                Bill bill = billList.get(j);
                //获取账单的日期
                Date billDate = bill.getBillDate();
                //获取账单的金额
                BigDecimal money = bill.getMoney();
                //获取账单的月份
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(billDate);
                int month = calendar.get(Calendar.MONTH) + 1;
                if (month == i + 1) {
                    //如果金额为负数，就是支出
                    if (money.compareTo(new BigDecimal(0)) < 0) {
                        expense = expense.add(money);
                    } else {
                        income = income.add(money);
                    }

                }
            }
            incomeList.add(income);
            expenseList.add(expense);
        }

        //计算每个分类的支出
        List<String> categoryList = new ArrayList<>();
        List<BigDecimal> categoryListMoney = new ArrayList<>();
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            //获取账单的类型
            //获取账单的金额
            BigDecimal money = bill.getMoney();
            //获取账单的分类
            String category = bill.getCategory();
            //如果金额为负数，就是支出
            if (money.compareTo(new BigDecimal(0)) < 0) {
                if (categoryList.contains(category)) {
                    int index = categoryList.indexOf(category);
                    BigDecimal newMoney = categoryListMoney.get(index).add(money);
                    categoryListMoney.set(index, newMoney);
                } else {
                    categoryList.add(category);
                    categoryListMoney.add(money);
                }
            }
        }
        //创建一个分类的对象
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setCategoryList(categoryList);
        statisticsResponse.setCategoryListMoney(categoryListMoney);
        statisticsResponse.setIncomeList(incomeList);
        statisticsResponse.setExpenseList(expenseList);
        return statisticsResponse;
    }

    public StatisticsResponse statisticsByMonth(String date, Integer userId) {
        //统计这一个月每天的收入和支出
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.like("bill_date", date);
        billQueryWrapper.eq("user_id", userId);
        List<Bill> billList = billMapper.selectList(billQueryWrapper);

        if (billList == null) {
            log.error("账单信息为空，没有查询到任何数据");
            throw new RuntimeException("账单信息为空，没有查询到任何数据");
        }
        //打印所有账单信息
        log.info("userId= " + userId + "的账单信息如下");
        for (Bill bill : billList) {
            //格式化日期xxxx-xx-xx
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String billDate = simpleDateFormat.format(bill.getBillDate());

            log.info("userId= " + userId + "  money= " + bill.getMoney() + "  billDate= " + billDate + "  billCategory= " + bill.getCategory());
        }

        //下面是数据处理部分

        //计算每天的收入和支出
        List<BigDecimal> incomeList = new ArrayList<>();
        List<BigDecimal> expenseList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            BigDecimal income = new BigDecimal(0);
            BigDecimal expense = new BigDecimal(0);
            for (Bill bill : billList) {
                Date billDate = bill.getBillDate();
                String day = String.valueOf(billDate.getDay());
                BigDecimal money = bill.getMoney();
                if (day.equals(String.valueOf(i))) {
                    if (money.compareTo(new BigDecimal(0)) > 0) {
                        income = income.add(bill.getMoney());
                    } else {
                        expense = expense.add(bill.getMoney());
                    }
                }
            }
            incomeList.add(income);
            expenseList.add(expense);
        }
        List<String> categoryList = new ArrayList<>();
        List<BigDecimal> categoryListMoney = new ArrayList<>();
        //计算每个分类的支出
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            String category = bill.getCategory();
            BigDecimal money = bill.getMoney();

            if (money.compareTo(new BigDecimal(0)) < 0) {
                if (categoryList.contains(category)) {
                    int index = categoryList.indexOf(category);
                    BigDecimal money1 = categoryListMoney.get(index);
                    money1 = money1.add(money);
                    categoryListMoney.set(index, money1);
                } else {
                    categoryList.add(category);
                    categoryListMoney.add(money);
                }
            }
        }

        //将计算结果封装到返回对象中
        StatisticsResponse statisticsResponse = new StatisticsResponse();
        statisticsResponse.setIncomeList(incomeList);
        statisticsResponse.setExpenseList(expenseList);
        statisticsResponse.setCategoryList(categoryList);
        statisticsResponse.setCategoryListMoney(categoryListMoney);
        log.info("categoryList= " + categoryList);
        log.info("categoryListMoney= " + categoryListMoney);
        log.info("statisticsByMonthResponse= " + statisticsResponse);
        return statisticsResponse;
    }

    @Override
    public OCRResponse handleImage(MultipartFile file) {
        OCRResponse ocrResponse = new OCRResponse();
        String resultJSON = OCRUtils.recognizeReceipt(file);
        JSONObject jsonObject = JSON.parseObject(resultJSON);
        log.info("jsonObject= " + jsonObject);
        //获取图片识别的结果
        String result = jsonObject.getString("result");
        log.info("result= " + result);
        //拿到result中的item_list
        JSONObject jsonObject1 = JSON.parseObject(result);
        String itemList = jsonObject1.getString("item_list");
        log.info("itemList= " + itemList);
        //将item_list转换为数组
        JSONArray jsonArray = JSON.parseArray(itemList);
        log.info("jsonArray= " + jsonArray);
        log.info(String.valueOf(jsonArray.size()));
        //遍历数组，获取每一个item
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            String item = jsonObject2.getString("key");
            log.info("key= " + item);
            //判断item中是否包含金额
            if (item.contains("money")) {
                String value = jsonObject2.getString("value");
                log.info("value= " + value);
                //将金额封装到返回对象中
                ocrResponse.setMoney(value);
            }
            //判断item中是否包含日期
            if (item.contains("date")) {
                String value = jsonObject2.getString("value");
                log.info("value= " + value);
                //将日期封装到返回对象中
                ocrResponse.setDate(value);
            }
            //判断item中是否包含no
            if (item.contains("no")) {
                String value = jsonObject2.getString("value");
                log.info("value= " + value);
                //将no封装到返回对象中
                ocrResponse.setNo(value);
            }

            if (item.contains("shop")) {
                String value = jsonObject2.getString("value");
                log.info("检测到有shop信息");
                log.info("value= " + value);
                //将no封装到返回对象中
                ocrResponse.setShop(value);
            }

            if (item.contains("shop_no")) {
                String value = jsonObject2.getString("value");
                log.info("value= " + value);
                //将no封装到返回对象中
                ocrResponse.setShop_no(value);
            }

            if (item.contains("sku")) {
                String value = jsonObject2.getString("value");
                log.info("value= " + value);
                //将no封装到返回对象中
                ocrResponse.setSku(value);
            }
        }
        log.info("结果如下");
        log.info("ocrResponse= " );
        log.info(ocrResponse.toString());


        return ocrResponse;
    }

    @Override
    public List<Bill> getBillBetweenDates(String startDate, String endDate, Integer userId) {
        //按照日期范围查询账单信息
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("user_id", userId);
        //数据库中的日期格式为yyyy-MM-dd
        billQueryWrapper.between("bill_date", startDate, endDate);
        List<Bill> billList = billMapper.selectList(billQueryWrapper);
        if (billList == null) {
            log.error("账单信息为空，没有查询到任何数据");
            throw new RuntimeException("账单信息为空，没有查询到任何数据");
        }
        return billList;
    }





}




