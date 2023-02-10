package com.cxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxk.model.domain.Bill;
import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.service.BillService;
import com.cxk.mapper.BillMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author houyunfei
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
        List<String> billCategory = bill.getBillCategory();
        //将分类数组转换为字符串存入数据库
        String category = StringUtils.join(billCategory, ",");
        bill1.setCategory(category);
        log.info("category:{}", category);
        bill1.setCategory(category);
        bill1.setBillDate(bill.getBillDate());
        bill1.setShop(bill.getBillShopkeeper());
        bill1.setRemark(bill.getBillRemark());
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
            String category = bill.getCategory();
            String[] split = category.split(",");
            List<String> strings = Arrays.asList(split);
            billAddRequest.setBillCategory(strings);
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


}




