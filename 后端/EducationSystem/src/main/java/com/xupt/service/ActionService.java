package com.xupt.service;


import com.xupt.bean.Action;

import java.util.List;

public interface ActionService {
    List<Action> getAction(String nowDate);

    int addRegisterMember(Integer actionId, String[] nums);

    int addAction(Action action);
}
