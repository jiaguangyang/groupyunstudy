package com.jk.loginservice.service.impl;

import com.jk.loginservice.dao.LoginDao;
import com.jk.loginservice.service.LoginService;
import com.jk.model.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired
  private LoginDao loginDao;
  @Autowired
  private JedisPool jedisPool;

  @Override
  public HashMap<String, Object> login(String name ,String password) {
    HashMap<String, Object> hashMap = new HashMap<>();
    /*        GongSiiXanLu user = */
    User name1 = loginDao.queryName(name);
    if (name1 == null) {
      hashMap.put("code", 1);
      hashMap.put("msg", "账号错误");
      return hashMap;
    }
    //String passeword = user.getPassword();
    String username = name1.getName();
    String password1 = name1.getPassword();
    //String md516 = Md5Util.getMd516(user.getPassword());
    if (!password1.equals(password)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "密码错误！");
      return hashMap;
    }
    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    Jedis jedis = jedisPool.getResource();

    jedis.set("name",name1.getName());
    jedis.set("userId",name1.getId()+"");
    jedis.del("teacherId");
    jedis.del("jgId");
    jedis.close();
    return hashMap;
  }

  @Override
  public List<TeacherBean> queryZhuying() {
    List<TeacherBean> teacherBeans = loginDao.queryZhuying();
    return teacherBeans;
  }

  @Override
  public HashMap<String, Object> addUser(TeacherBean teacherBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    String teacherName=loginDao.queryTeacher(teacherBean.getTeacherAccount());
    if (teacherName!=null&&!teacherName.equals("")&&!teacherName.equals("null")){
      hashMap.put("code", 2);
      hashMap.put("msg", "用户名已存在");
      return hashMap;
    }
    Jedis jedis = jedisPool.getResource();
    String phone = jedis.get("phone");
    String teacherPhone = teacherBean.getTeacherPhone();
    if (phone==null||!phone.equals(teacherPhone)){
      jedis.del("phone");
      hashMap.put("code", 1);
      hashMap.put("msg", "请确认认证手机");
      return hashMap;
    }
    try {
      loginDao.addUser(teacherBean);
      hashMap.put("code", 0);
      hashMap.put("msg", "注册成功请等待审核");
      return hashMap;

    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 3);
      hashMap.put("msg", "注册失败");
      return hashMap;
    }
  }

  @Override
  public HashMap<String, Object> queryTeacherLogin(TeacherBean teacherBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    /*        GongSiiXanLu user = */
    TeacherBean teacherAccount = loginDao.queryTeacherLogin(teacherBean);
    Integer chec =loginDao.queryChec(teacherBean.getTeacherAccount());
    if (chec!=2){
      hashMap.put("code", 3);
      hashMap.put("msg", "对不起你还没有该权限");
      return hashMap;
    }
    if (teacherAccount == null) {
      hashMap.put("code", 1);
      hashMap.put("msg", "请确认账号！");
      return hashMap;
    }
    //String passeword = user.getPassword();
    String password = teacherAccount.getTeacherPwd();

    //String md516 = Md5Util.getMd516(user.getPassword());
    if (!teacherBean.getTeacherPwd().equals(password)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "密码错误！");
      return hashMap;
    }

    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    Jedis jedis = jedisPool.getResource();
    //String loginName = JSON.toJSONString(name1);
    jedis.set("name",teacherAccount.getTeacherAccount());
    jedis.set("teacherId",teacherAccount.getId()+"");
    jedis.del("userId");
    jedis.del("jgId");
    //jedis.del("")
    jedis.close();
    return hashMap;
  }

  @Override
  public HashMap<String, Object> addViden(VidenBean videnBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String teacherId = jedis.get("teacherId");
    int teacherId1 = Integer.parseInt(teacherId);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String format = simpleDateFormat.format(date);
    try {
      videnBean.setTeacherId(teacherId1);
      videnBean.setCreateTime(format);
      loginDao.addViden(videnBean);
      hashMap.put("code", 0);
      hashMap.put("msg", "上传成功请等待审核");
      return hashMap;
    }catch (Exception e){
      hashMap.put("code", 1);
      hashMap.put("msg", "上传失败");
      e.printStackTrace();
      return hashMap;
    }

  }
  //教师树
  @Override
  public List<TreeBean> queryTree() {
    Integer pid=0;
    //redisTemplate.boundValueOps("user").get(0,-1);
    return queryByPid(pid);
  }

  @Override
  public TeacherBean queryTeatherById() {
    Jedis jedis = jedisPool.getResource();
    String teacherId = jedis.get("teacherId");
    int id = Integer.parseInt(teacherId);
    return loginDao.queryTeatherById(id);
  }

  @Override
  public HashMap<String, Object> uploadRegtrs(TeacherBean teacherBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    String teacher = loginDao.queryTeacherById(teacherBean.getId());
    Jedis jedis = jedisPool.getResource();
    String phone = jedis.get("phone");
    if (!teacher.equals(teacherBean.getTeacherPhone())){
      if (phone==null||!phone.equals(teacherBean.getTeacherPhone())||phone.equals("")||phone.equals("null")){
        hashMap.put("code", 2);
        hashMap.put("msg", "请验证手机号");
        jedis.del("phone");
        jedis.close();
        return hashMap;
      }
    }
    try {
      loginDao.uploadRegtrs(teacherBean);
      hashMap.put("code", 0);
      hashMap.put("msg", "修改成功");
      return hashMap;
    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 1);
      hashMap.put("msg", "修改失败");
      return hashMap;
    }

  }

  @Override
  public Boolean updatePassword(String password) {
    try {
      Jedis jedis = jedisPool.getResource();
      String teacherId = jedis.get("teacherId");
      loginDao.updatePassword(teacherId, password);
      jedis.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public HashMap<String,Object> addRegUser(User user) {
    HashMap<String, Object> hashMap = new HashMap<>();
    User username  =loginDao.queryUserByName(user.getName());
    if (username!=null){
      hashMap.put("code", 1);
      hashMap.put("msg", "账号已存在");
      return hashMap;
    }
    try {
      loginDao.addRegUser(user);
      hashMap.put("code", 0);
      hashMap.put("msg", "注册成功请点击登录");
      return hashMap;
    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 1);
      hashMap.put("msg", "注册失败");
      return hashMap;
    }
  }
  //用户树
  @Override
  public List<UserTree> queryUserTree() {
    Integer pid=0;
    //redisTemplate.boundValueOps("user").get(0,-1);
    return queryUserTreeByPid(pid);
  }

  @Override
  public User queryUserById() {
    Jedis jedis = jedisPool.getResource();
    String userId = jedis.get("userId");
    User user = loginDao.queryUserById(userId);
    jedis.close();
    return  user;
  }

  @Override
  public HashMap<String, Object> updateUserById(User user) {
    HashMap<String, Object> hashMap = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String userId = jedis.get("userId");
    String name = jedis.get("name");
    User user1 = loginDao.queryUserById(userId);
    String phone = jedis.get("phone");
    //!phone.equals(teacherBean.getTeacherPhone())||phone.equals("")||phone.equals("null")

    if (!user.getUserPhone().equals(phone)&&!StringUtils.isNotEmpty(phone)) {
      hashMap.put("code", 1);
      hashMap.put("msg", "请验证先手机");
      return hashMap;
    }


    if(!user.getName().equals(name)){
      hashMap.put("code", 2);
      hashMap.put("msg", "用户名已存在");
      return hashMap;
    }
    try {
      loginDao.updateUserById(user);
      hashMap.put("code", 0);
      hashMap.put("msg", "编辑成功");
      jedis.close();
      return hashMap;
    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 3);
      hashMap.put("msg", "修改失败");
      return hashMap;
    }



  }

  @Override
  public Boolean updateUserPassword(String password) {
    try {
      Jedis jedis = jedisPool.getResource();
      String userId = jedis.get("userId");
      loginDao.updateUserPassword(userId, password);
      jedis.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Boolean addVideo(Video video) {
    Jedis jedis = jedisPool.getResource();
    String teacherId = jedis.get("teacherId");
    int tid = Integer.parseInt(teacherId);
    try {
      Date date = new Date();
      video.setTeacherId(tid);
      video.setVideoTime(date);
      loginDao.addVideo(video);
      return true;
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }

  }

  @Override
  public  HashMap<String, Object> queryProject(Integer page, Integer limit) {
    HashMap<String, Object> map = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String teacherId=jedis.get("teacherId");;




    int total=loginDao.queryCount(teacherId);
    int start=(page-1)*limit;

    List<Video> list = loginDao.queryVideoById(start,limit,teacherId);
    map.put("count", total);
    map.put("code", 0);
    map.put("data", list);
    return map;
  }

  @Override
  public HashMap<String, Object> queryInstitutionLogin(TeacherBean teacherBean) {
    HashMap<String, Object> hashMap = new HashMap<>();
    /*        GongSiiXanLu user = */
    Institutions  institutions= loginDao.queryInstitutionLogin(teacherBean.getTeacherAccount());
    //Integer chec =loginDao.queryChec(teacherBean.getTeacherAccount());
    if (institutions == null) {
      hashMap.put("code", 1);
      hashMap.put("msg", "请确认账号！");
      return hashMap;
    }

    if (institutions.getStart()!=2){
      hashMap.put("code", 3);
      hashMap.put("msg", "对不起你还没有该权限");
      return hashMap;
    }

    String password = institutions.getPassword();

    //String md516 = Md5Util.getMd516(user.getPassword());
    if (!teacherBean.getTeacherPwd().equals(password)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "密码错误！");
      return hashMap;
    }

    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    Jedis jedis = jedisPool.getResource();
    //String loginName = JSON.toJSONString(name1);
    jedis.set("name",institutions.getAccount());
    jedis.set("jgId",institutions.getId()+"");
    jedis.del("userId");
    jedis.del("teacherId");
    //jedis.del("")
    jedis.close();
    return hashMap;
  }

  @Override
  public HashMap<String, Object> addInformation(Institutions institutions) {
    HashMap<String, Object> hashMap = new HashMap<>();
    Institutions inst  =loginDao.queryInformationByName(institutions.getAccount());
    if (inst!=null){
      hashMap.put("code", 1);
      hashMap.put("msg", "账号已存在");
      return hashMap;
    }
    Jedis jedis = jedisPool.getResource();
    String phone = jedis.get("phone");
    String inlsPhone = institutions.getPhone();
    if (phone==null||!phone.equals(inlsPhone)){
      jedis.del("phone");
      hashMap.put("code", 2);
      hashMap.put("msg", "请确认认证手机");
      jedis.close();
      return hashMap;
    }
    try {
      loginDao.addInformation(institutions);
      hashMap.put("code", 0);
      hashMap.put("msg", "注册成功请等待审核");
      return hashMap;
    }catch (Exception e){
      e.printStackTrace();
      hashMap.put("code", 3);
      hashMap.put("msg", "注册失败");
      return hashMap;
    }
  }
  //机构树
  @Override
  public List<JgTree> queryJgTree() {
    Integer pid=0;
    //redisTemplate.boundValueOps("user").get(0,-1);
    return queryJgTreeByPid(pid);
  }

  @Override
  public Institutions queryupdateInstitution() {
    Jedis jedis = jedisPool.getResource();
    String jgId = jedis.get("jgId");
    Institutions institutions=loginDao.queryupdateInstitution(jgId);
    jedis.close();
    return institutions;
  }

  @Override
  public HashMap<String, Object> updateInformation(Institutions institutions) {
    HashMap<String, Object> hashMap = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String userId = jedis.get("userId");
    // User user1 = loginDao.queryUserById(userId);
    Institutions institutions1 = loginDao.queryInformationByName(institutions.getAccount());
    String phone = jedis.get("phone");
    String name = jedis.get("name");
    //!phone.equals(teacherBean.getTeacherPhone())||phone.equals("")||phone.equals("null")

    if (!institutions.getPhone().equals(phone) && !StringUtils.isNotEmpty(phone)) {
      hashMap.put("code", 1);
      hashMap.put("msg", "请验证先手机");
      return hashMap;
    }


    if (!institutions.getAccount().equals(name)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "账号已存在");
      return hashMap;
    }
    try {
      loginDao.updateInformation(institutions);
      hashMap.put("code", 0);
      hashMap.put("msg", "编辑成功");
      jedis.close();
      return hashMap;
    } catch (Exception e) {
      e.printStackTrace();
      hashMap.put("code", 3);
      hashMap.put("msg", "修改失败");
      return hashMap;
    }
  }

  @Override
  public Boolean updateInPassword(String password) {
    try {
      Jedis jedis = jedisPool.getResource();
      String jgId = jedis.get("jgId");
      loginDao.updateInPassword(jgId, password);
      jedis.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public HashMap<String, Object> queryJgTeacher(Integer page,Integer limit) {
    HashMap<String, Object> map = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String teacherId = jedis.get("jgId");
    int total=loginDao.queryJgTeacherCount(teacherId);
    int start=(page-1)*limit;

    List<TeacherBean> list = loginDao.queryJgTeacher(start,limit,teacherId);
    map.put("count", total);
    map.put("code", 0);
    map.put("data", list);
    jedis.close();
    return map;
  }

  @Override
  public HashMap<String, Object> queryJgteacher(Integer page,Integer limit,Integer teacherId) {
    HashMap<String, Object> map = new HashMap<>();

    int total=loginDao.queryJgTeacherCountByTeachId(teacherId);
    int start=(page-1)*limit;

    List<Video> list = loginDao.queryJgteacherByTeachId(start,limit,teacherId);
    map.put("count", total);
    map.put("code", 0);
    map.put("data", list);
    return map;
  }

  @Override
  public HashMap<String, Object> queryDingdan(Integer page,Integer limit) {
    HashMap<String, Object> map = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String userId = jedis.get("name");
    int total=loginDao.queryDingdanCount(userId);
    int start=(page-1)*limit;

    List<Dingdan> list = loginDao.queryDingdan(start,limit,userId);
    map.put("count", total);
    map.put("code", 0);
    map.put("data", list);
    jedis.close();
    return map;
  }

  @Override
  public HashMap<String, Object> phoneLogin(String phone, Integer code) {
    HashMap<String, Object> hashMap = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String phonecode = jedis.get("random"+phone);
    Integer parseIntphonecode = Integer.parseInt(phonecode);
    User user=loginDao.queryPhone(phone);
    if (user==null){
      hashMap.put("code", 1);
      hashMap.put("msg", "手机不存在");
      return hashMap;
    }
    if (!parseIntphonecode.equals(code)) {
      hashMap.put("code", 2);
      hashMap.put("msg", "验证码错误请重新发送");
      return hashMap;
    }
    jedis.set("name",user.getName());
    hashMap.put("code", 0);
    hashMap.put("msg", "登陆成功");
    jedis.close();
    return hashMap;
  }



/*  @Override
  public HashMap<String, Object> querySp(Integer page, Integer limit) {
    HashMap<String, Object> map = new HashMap<>();
    Jedis jedis = jedisPool.getResource();
    String userId = jedis.get("name");
    int total=loginDao.querySpCount(userId);
    int start=(page-1)*limit;

    List<Dingdan> list = loginDao.queryDingdan(start,limit,userId);
    map.put("count", total);
    map.put("code", 0);
    map.put("data", list);
    jedis.close();
  }*/

  private List<JgTree> queryJgTreeByPid(Integer pid) {
    List<JgTree> treeBeanList=loginDao.queryJgTreeByPid(pid);
    for (JgTree JgTree : treeBeanList) {
      List<JgTree> queryUserTreeByPid = queryJgTreeByPid(JgTree.getId());
      if (queryUserTreeByPid!=null&&queryUserTreeByPid.size()>0) {
        JgTree.setNodes(queryUserTreeByPid);
        JgTree.setSeletetable(false);
      }else{
        JgTree.setSeletetable(true);
      }
    }
    return treeBeanList;
  }

  private List<UserTree> queryUserTreeByPid(Integer pid) {
    List<UserTree> treeBeanList=loginDao.queryUserTreeByPid(pid);
    for (UserTree userTree : treeBeanList) {
      List<UserTree> queryUserTreeByPid = queryUserTreeByPid(userTree.getId());
      if (queryUserTreeByPid!=null&&queryUserTreeByPid.size()>0) {
        userTree.setNodes(queryUserTreeByPid);
        userTree.setSeletetable(false);
      }else{
        userTree.setSeletetable(true);
      }
    }
    return treeBeanList;
  }


  private List<TreeBean> queryByPid(Integer pid) {
    List<TreeBean> treeBeanList=loginDao.queryByPid(pid);
    for (TreeBean treeBean : treeBeanList) {
      List<TreeBean> queryByPid = queryByPid(treeBean.getId());
      if (queryByPid!=null&&queryByPid.size()>0) {
        treeBean.setNodes(queryByPid);
        treeBean.setSeletetable(false);
      }else{
        treeBean.setSeletetable(true);
      }
    }
    return treeBeanList;
  }
  @Override
  public Video queryMyVideoByid(Integer id) {
    return loginDao.queryMyVideoByid(id);
  }

  @Override
  public Boolean updateVideoById(Video video) {
    try {

      loginDao.updateVideoById(video);
      return true;
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }

  }
}