import com.hxy.base.common.Constant;
import com.hxy.gen.service.SysGeneratorService;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


/**
 * 类的功能描述.
 * 自动生成代码测试类
 * @Auther hxy
 * @Date 2017/4/25
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class genTest {
    private static Logger logger = Logger.getLogger(genTest.class);
    @Resource
    private SysGeneratorService generatorService;
    @Before
    public void before(){
    }
    @Test
    public void generatorCode(){
        byte[] bytes=generatorService.generatorCode(new String[]{"bus_demo"}, Constant.genType.local.getValue());
    }
}

