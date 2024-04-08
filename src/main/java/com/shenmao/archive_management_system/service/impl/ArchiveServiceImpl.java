package com.shenmao.archive_management_system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shenmao.archive_management_system.mapper.ArchiveMapper;
import com.shenmao.archive_management_system.mapper.UserMapper;
import com.shenmao.archive_management_system.pojo.*;
import com.shenmao.archive_management_system.service.ArchiveService;
import com.shenmao.archive_management_system.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    private static final Integer normal = 0; //正常
    private static final String permission = "管理员";

    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private UserMapper userMapper;

    //添加档案
    @Override
    public void add(Archive archive) {
        archive.setArchiveDate(LocalDate.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        String createName = (String) map.get("username");
        archive.setCreateUser(createName);
        archiveMapper.add(archive);
    }

    //查询总档案（用户&管理员）
    //pageNum: 当前页码 pageSize：每页条数
    //用户：查询档案：&非保密；管理：查询档案：&保密
    @Override
    public PageBean<Archive> list(Integer pageNum, Integer pageSize, String archiveName, String archiveCategory, LocalDate archiveDate, String status) {
        //获取用户身份
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUsername(username);

        //1.创建pageBean对象
        PageBean<Archive> pb = new PageBean<>();
        //2.开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        if (user.getPermission().equals(permission)){  //管理员
            //3.调用Mapper
            List<Archive> list = archiveMapper.listA(archiveName, archiveCategory, archiveDate,status);
            //Page是pageHelper中提供的List的实现类，它提供了方法，可以获取pageHelper分页查询后得到的总记录条数和当前页数据
            //若不强转，多态是不允许父类使用子类类型的特有方法的
            Page<Archive> p = (Page<Archive>)list;
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
        }else {  //普通用户
            List<Archive> list = archiveMapper.list(archiveName, archiveCategory, archiveDate,status, normal);
            Page<Archive> p = (Page<Archive>)list;
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
        }

        return pb;
    }



    //用户查询自己档案
    @Override
    public List<Archive> itself(String status) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String createUser = (String) map.get("username");
        return archiveMapper.itself(createUser, status);
    }

    //删除档案
    @Override
    public void delete(Integer archiveId) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userMapper.findByUsername(username);

        if (user.getPermission().equals(permission)){  //管理员
            archiveMapper.deleteA(archiveId);
        }else {  //普通用户
            archiveMapper.delete(archiveId, username);
        }
    }

    @Override
    public List<Integer> countYear(String archiveDate) {
        List<Integer> count = archiveMapper.countYear(archiveDate);
        return count;
    }

    public List<Integer> countMonth(String year, String month) {
        List<Integer> count = archiveMapper.countMonth(year, month);
        return count;
    }

    @Override
    public List<Category> countCategory() {
        List<String> name = archiveMapper.countCategory1();
        List<Integer> count = archiveMapper.countCategory2();
        List<Category> list = new ArrayList<>();
        for (int i = 0; i <name.size(); i++) {
            Category category = new Category(name.get(i), count.get(i));
            list.add(category);
        }
//        List<Category> list1 = archiveMapper.countCategory();
//        System.out.println(list1);
        return list;
    }
    @Override
    public Integer countName(String username) {
        return archiveMapper.countName(username);
    }



    @Override
    public void updateStatus(Integer archiveId, String status, Integer secrecy,Integer userId) {
        archiveMapper.updateStatus(archiveId, status, secrecy);
    }

    @Override
    public void updateSecrecy(Integer archiveId, Integer secrecy) {
        archiveMapper.updateSecrecy(archiveId, secrecy);
    }

}
