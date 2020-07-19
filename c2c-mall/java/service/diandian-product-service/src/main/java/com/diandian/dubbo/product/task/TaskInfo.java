package com.diandian.dubbo.product.task;

import com.diandian.dubbo.facade.model.order.OrderDetailModel;
import com.diandian.dubbo.facade.service.order.OrderDetailService;
import com.diandian.dubbo.facade.service.task.TaskService;
import com.diandian.dubbo.product.mapper.OrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@Slf4j
@EnableScheduling
public class TaskInfo {

    @Autowired
    private TaskService taskService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    //每天执行一次
    @Scheduled(cron = "0 0 0 1/1 * ? ")
    public void shareBenefit(){
        log.info("开始执行分润结算任务");
        taskService.ordercompleteShareBenefit();
    }

    /**
    * @Description:  自动收货，每天0:5分查询七天前的订单确认收货
    * @Param:
    * @return:
    * @Author: wsk
    * @Date: 2019/9/18
    */
    @Scheduled(cron="0 5 0 */1 * ?")
    public void automaticDelivery(){
        log.info("开始执行自动收货任务");
        List<OrderDetailModel> list=orderDetailMapper.automaticDelivery();
        for (OrderDetailModel orderDetailModel:list){
            orderDetailService.confirmTake(orderDetailModel.getId());
        }
    }
}
