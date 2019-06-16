package com.xupt.serviceImpl;

import com.xupt.bean.Action;
import com.xupt.dao.ActionMapper;
import com.xupt.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {


    @Autowired
    private ActionMapper actionMapper;

    @Override
    public List<Action> getAction(String nowDate) {
        return actionMapper.selectAction(nowDate);
    }

    @Override
    public int addRegisterMember(Integer actionId, String[] nums) {
        String groupId = null;
        if(nums.length != 1){
            groupId = UUID.randomUUID().toString();
        }
        int count = 0;
        for(int i=0;i<nums.length;i++){
            count += actionMapper.insertRegisterMember(nums[i],groupId,nums[0],actionId);
        }
        return count;
    }

    @Override
    public int addAction(Action action) {
        return actionMapper.insertAction(action);
    }
}
