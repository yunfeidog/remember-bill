package com.cxk.mapper;

import com.cxk.model.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @description 针对表【tb_bill(账单表)】的数据库操作Mapper
* @createDate 2023-02-10 16:14:48
* @Entity com.cxk.model.entity.Bill
*/
@Mapper
public interface BillMapper extends BaseMapper<Bill> {

}




