package com.webnest.internship.dao;

import com.webnest.internship.service.ApplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class EnterpriseTest {
    @Autowired
    ApplyService applyService;
    @Test
    public void list(){
        System.out.println(111);
        List<Map<String, Object>> list = applyService.getApplyList(1);
        System.out.println(list);
//        for (Map<String, Object> map : list){
//            Set<String> set = map.keySet();
//            Iterator<String> it = set.iterator();
//            while (it.hasNext()){
//                Object key = it.next();
//                Object value = map.get(key);
//                System.out.println(value);
//            }
//        }
    }
}
