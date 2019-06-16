package com.xupt.dao;

import com.xupt.bean.Action;

import java.util.List;

public interface ActionMapper {
    List<Action> selectAction(String nowDate);

    int insertRegisterMember(String num, String groupId, String num1, Integer actionId);

    int insertAction(Action action);
}
