package com.example.demo.controller;

import com.example.demo.mqtt.MqttPushClient;
import com.example.demo.mqtt.R;
import com.example.demo.mqtt.WebHookConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/")
@Slf4j
public class TestController {

    @Autowired
    private MqttPushClient mqttPushClient;

//    @ApiOperation(value = "发布主题", notes = "测试发布主题")
    @GetMapping(value = "/publishTopic")
    public R publishTopic() {
        mqttPushClient.publish(0,false,"mytopic","测试一下发布消息");
        return R.ok("发布成功");
    }


    @PostMapping("/webHook")
    public void onWebHook(@RequestBody Map<String,String> param){
        System.out.println("进入方法");
        String action = param.get(WebHookConstant.ACTION);
        //System.out.println(action);
        String username = param.get(WebHookConstant.USERNAME);
        if (WebHookConstant.ACTION_DISCONNECTED.equals(action)){
            onClientConnected(username);
        }else if (WebHookConstant.ACTION_CONNECTED.equals(action)){
            clientConnected(username);
        } else{
            clientConnect(username);
        }
    }
    /**
     * 设备连接中断监听
     */
    private void onClientConnected(String username){
        System.out.println("设备离线--用户名为:"+username); //输出kwhua
    }
    private void clientConnected(String username){
        System.out.println("设备上线--用户名为:"+username); //输出kwhua
    }
    private void clientConnect(String username){
        System.out.println("设备重连--用户名为:"+username); //输出kwhua
    }


    /*public void receiveWebHookData(@RequestBody Map<String, Object> param) {
        String action ="client_connected";
        //测试内容
        log.info("监听到客户端活动信息=========");
        Iterator it = param.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            log.info(key + "=====" + value.toString());
        }
        log.info("以上是接收的所有信息=========");
        //设备连接成功
        if (WebHookConstant.ACTION_CONNECTED.equals(action)) {
            log.info("设备连接成功=========");
        }
        //设备断开连接成功
        if (WebHookConstant.ACTION_DISCONNECTED.equals(action)) {
            log.info("设备断开连接成功=========");
        }
        //设备数据上传
        if (WebHookConstant.MESSAGE_PUBLISH.equals(action)) {
            log.info("设备数据上传=========");
        }
    }*/


}
