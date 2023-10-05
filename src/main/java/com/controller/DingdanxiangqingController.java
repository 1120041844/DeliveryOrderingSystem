package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.DingdanxiangqingEntity;

import com.service.DingdanxiangqingService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 订单详情
 * 后端接口
 * @author
 * @email
 * @date 2021-03-09
*/
@RestController
@Controller
@RequestMapping("/dingdanxiangqing")
public class DingdanxiangqingController {
    private static final Logger logger = LoggerFactory.getLogger(DingdanxiangqingController.class);

    @Autowired
    private DingdanxiangqingService dingdanxiangqingService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = dingdanxiangqingService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        DingdanxiangqingEntity dingdanxiangqing = dingdanxiangqingService.selectById(id);
        if(dingdanxiangqing!=null){
            return R.ok().put("data", dingdanxiangqing);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody DingdanxiangqingEntity dingdanxiangqing, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<DingdanxiangqingEntity> queryWrapper = new EntityWrapper<DingdanxiangqingEntity>()
            .eq("odd", dingdanxiangqing.getOdd())
            .eq("hx_types", dingdanxiangqing.getHxTypes())
            .eq("number", dingdanxiangqing.getNumber())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DingdanxiangqingEntity dingdanxiangqingEntity = dingdanxiangqingService.selectOne(queryWrapper);
        if(dingdanxiangqingEntity==null){
            dingdanxiangqingService.insert(dingdanxiangqing);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody DingdanxiangqingEntity dingdanxiangqing, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<DingdanxiangqingEntity> queryWrapper = new EntityWrapper<DingdanxiangqingEntity>()
            .notIn("id",dingdanxiangqing.getId())
            .eq("odd", dingdanxiangqing.getOdd())
            .eq("hx_types", dingdanxiangqing.getHxTypes())
            .eq("number", dingdanxiangqing.getNumber())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        DingdanxiangqingEntity dingdanxiangqingEntity = dingdanxiangqingService.selectOne(queryWrapper);
        if(dingdanxiangqingEntity==null){
            dingdanxiangqingService.updateById(dingdanxiangqing);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        logger.debug("Controller:"+this.getClass().getName()+",delete");
        dingdanxiangqingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

