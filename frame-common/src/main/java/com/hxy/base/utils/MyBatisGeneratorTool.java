package com.hxy.base.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动入口类
 * 
 * @author lihongtao
 *
 */
public class MyBatisGeneratorTool {

	public static void main(String[] args) {
		try {
			List<String> warnings = new ArrayList<String>();
			ConfigurationParser cp = new ConfigurationParser(warnings);
			// file:/E:/workspace/mybatis-generator-core/bin/
			File configurationFile = new File(MyBatisGeneratorTool.class.getResource("/generatorConfig.xml").toURI());
			Configuration config = cp.parseConfiguration(configurationFile);

			//设置为true，每次都是覆盖操作
			DefaultShellCallback shellCallback = new DefaultShellCallback(true);

			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
			myBatisGenerator.generate(null);
			System.out.println("------------------------生成成功------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
