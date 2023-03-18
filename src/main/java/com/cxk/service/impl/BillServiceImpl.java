package com.cxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxk.mapper.BillMapper;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.model.domain.response.BillResponse;
import com.cxk.model.domain.response.DateOrCategoryResponse;
import com.cxk.model.domain.response.StatisticsResponse;
import com.cxk.model.entity.Bill;
import com.cxk.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        bill1.setBillType(bill.getBillType());
        //现在分类不是数组了，是字符串
//        List<String> billCategory = bill.getBillCategory();
//        //将分类数组转换为字符串存入数据库
//        String category = StringUtils.join(billCategory, ",");
//        bill1.setCategory(category);
//        log.info("category:{}", category);
//        bill1.setCategory(category);
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
            billAddRequest.setBillType(bill.getBillType());
            billAddRequest.setBillDate(bill.getBillDate());
            billAddRequest.setBillShopkeeper(bill.getShop());
            billAddRequest.setBillRemark(bill.getRemark());
            //现在分类不是数组了，是字符串
//            String category = bill.getCategory();
//            String[] split = category.split(",");
//            List<String> strings = Arrays.asList(split);
//            billAddRequest.setBillCategory(strings);
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
    public boolean updateBill(Bill bill, Bill oldBill) {
        if (bill == null) {
            //todo 抛出异常  账单信息为空
            log.error("新改的账单信息为空");
            return false;
        }
        if (oldBill == null) {
            //todo 抛出异常  账单信息为空
            log.error("旧的账单信息为空");
            return false;
        }
        //判断账单信息是否有修改
        if (bill.getMoney().equals(oldBill.getMoney()) && bill.getBillType().equals(oldBill.getBillType()) && bill.getCategory().equals(oldBill.getCategory()) && bill.getBillDate().equals(oldBill.getBillDate()) && bill.getShop().equals(oldBill.getShop()) && bill.getRemark().equals(oldBill.getRemark())) {
            //todo 抛出异常  账单信息未修改
            log.error("账单信息未修改");
            return false;
        }
        boolean updateResult = this.updateById(bill);
        if (!updateResult) {
            //todo 抛出异常  账单信息修改失败
            log.error("账单信息修改失败");
            return false;
        }
        return true;

    }

    @Override
    public List<DateOrCategoryResponse> getBillListByDateOrCategory(String category, Date date, Integer userId) {
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
            billQueryWrapper.eq("bill_date", date);
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
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            //创建一个账单信息
            BillResponse billResponse = new BillResponse();
            billResponse.setId(bill.getId());
            billResponse.setTypeName(bill.getCategory());
            billResponse.setPayType(bill.getBillType());
            billResponse.setAmount(bill.getMoney());
            billResponse.setDate(bill.getBillDate());
            billResponse.setRemark(bill.getRemark());
            billResponse.setShop(bill.getShop());
            boolean flag = false;
            for (int j = 0; j < dateOrCategoryResponseList.size(); j++) {
                DateOrCategoryResponse dateOrCategoryResponse = dateOrCategoryResponseList.get(j);
                if (dateOrCategoryResponse.getDate().equals(bill.getBillDate())) {
                    //如果日期相同，就把账单信息添加到这个日期下面
                    BigDecimal newIncome = dateOrCategoryResponse.getIncome().add(bill.getMoney());
                    BigDecimal newExpense = dateOrCategoryResponse.getExpense().add(bill.getMoney());
                    List<BillResponse> billResponseList = dateOrCategoryResponse.getBills();
                    billResponseList.add(billResponse);
                    DateOrCategoryResponse newDateOrCategoryResponse = new DateOrCategoryResponse();
                    newDateOrCategoryResponse.setDate(dateOrCategoryResponse.getDate());
                    newDateOrCategoryResponse.setIncome(newIncome);
                    newDateOrCategoryResponse.setExpense(newExpense);
                    newDateOrCategoryResponse.setBills(billResponseList);
                    dateOrCategoryResponseList.set(j, newDateOrCategoryResponse);
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            Date date1 = bill.getBillDate();
            BigDecimal money = bill.getMoney();
            BigDecimal income = new BigDecimal(0);
            BigDecimal expense = new BigDecimal(0);
            Integer billType = bill.getBillType();
            //判断账单类型 1-支出 0-收入
            if (billType == 1) {
                expense = money;
            } else {
                income = money;
            }

            //创建一个账单信息集合
            List<BillResponse> billResponseList = new ArrayList<>();
            billResponseList.add(billResponse);
            //创建一个日期分类的对象
            DateOrCategoryResponse dateOrCategoryResponse = new DateOrCategoryResponse();
            dateOrCategoryResponse.setDate(date1);
            dateOrCategoryResponse.setIncome(income);
            dateOrCategoryResponse.setExpense(expense);
            dateOrCategoryResponse.setBills(billResponseList);
            dateOrCategoryResponseList.add(dateOrCategoryResponse);
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
                //获取账单的类型
                Integer billType = bill.getBillType();
                //获取账单的月份
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(billDate);
                int month = calendar.get(Calendar.MONTH) + 1;
                if (month == i + 1) {
                    if (billType == 1) {
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
            Integer billType = bill.getBillType();
            //获取账单的金额
            BigDecimal money = bill.getMoney();
            //获取账单的分类
            String category = bill.getCategory();
            if (billType == 1) {
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
                if (day.equals(String.valueOf(i))) {
                    if (bill.getBillType() == 0) {
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
            if (bill.getBillType() == 1) {
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

}




