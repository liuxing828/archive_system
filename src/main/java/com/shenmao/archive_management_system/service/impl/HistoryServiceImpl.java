package com.shenmao.archive_management_system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenmao.archive_management_system.mapper.HistoryMapper;
import com.shenmao.archive_management_system.mapper.UserMapper;
import com.shenmao.archive_management_system.pojo.History;
import com.shenmao.archive_management_system.pojo.PageBean;
import com.shenmao.archive_management_system.pojo.User;
import com.shenmao.archive_management_system.service.HistoryService;
import com.shenmao.archive_management_system.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public void borrow(Integer archiveId, String archiveUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        History history = new History();
        history.setUserId(userId);
        history.setArchiveId(archiveId);
        history.setBorrowDate(LocalDate.now());
        history.setReturnDate(LocalDate.now().plusDays(3));
        history.setStatus("审核中");
        history.setArchiveUrl(archiveUrl);
        historyMapper.borrow(history);
    }

    @Override
    public PageBean<History> list(Integer pageNum, Integer pageSize, String status) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUsername(username);

        PageBean<History> pb = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        if (user.getPermission().equals("管理员")){
        Page<History> p = (Page<History>) historyMapper.listA("审核中");
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        }else{
            Page<History> p = (Page<History>) historyMapper.list(user.getId(),status);
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
        }
        return pb;
    }

    @Override
    public void approval(History history) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String admin = (String) map.get("username");
        historyMapper.approval(history.getRecordId(), history.getStatus(),"", admin);
        if (history.getStatus().equals("未通过")){
            historyMapper.approval(history.getRecordId(), history.getStatus(),history.getReason(), admin);
        }
    }

    @Override
    public void delete(Integer recordId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUsername(username);

        if (user.getPermission().equals("管理员")){  //管理员
            historyMapper.deleteA(recordId);
        }else {  //普通用户
            historyMapper.delete(recordId, user.getId());
        }
    }

    @Override
    public History getReason(Integer archiveId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        History history = historyMapper.getReason(archiveId, userId);
        System.out.println(history);
        return history;
    }
}
