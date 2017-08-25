
import com.hxy.sys.service.UserService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * 类的功能描述.
 *
 * @Auther hxy
 * @Date 2017/4/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class UserTest {
    private static Logger logger = Logger.getLogger(UserTest.class);
    private ApplicationContext ac=null;
    @Resource
    private UserService userService;
    @Before
    public void before(){
//        ac=new ClassPathXmlApplicationContext("spring-mybatis.xml");
//        userService= (UserService) ac.getBean("userService");
    }
    @Test
    public void save(){
//        UserEntity user=new UserEntity();
//        user.setId(Utils.uuid());
//        user.setLoginName("hxy");
//        user.setUserName("123");
//        user.setCreateTime(new Date());
//        user.setOfficeId("11");
        //userService.save(user);
    }
    @Test
    public void findById(){
        //UserEntity u = userService.queryObject("026a564bbfd84861ac4b65393644beef");

    }
}