package com.wl.security_demo.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import java.util.Collections;

public class CodeGenerator {

    public static void main(String[] args) {
        // 1. 数据库配置
        String url = "jdbc:mysql://47.98.104.35:3306/security_permission?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "123456";

        // 2. 开始构建
        FastAutoGenerator.create(url, username, password)
                // --- 全局配置 ---
                .globalConfig(builder -> {
                    builder.author("wangl") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式 (可选)
                            .outputDir(System.getProperty("user.dir") + "/src/main/java"); // 指定输出目录
                })
                // --- 包配置 ---
                .packageConfig(builder -> {
                    builder.parent("com.wl.security_demo") // 设置父包名
//                            .moduleName("mapper")         // 设置父包模块名 (最终包名: com.example.project.system)
                            .entity("domain.entity")      // 实体类包名 (DO)
                            .mapper("mapper")             // Mapper 接口包名
                            .xml("mapper.xml")            // XML 文件包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // XML 文件的自定义输出路径
                })
                // --- 策略配置 (核心) ---
                .strategyConfig(builder -> {
                    builder.addInclude("sys_user", "sys_role", "sys_permission","sys_user_role","sys_role_permission","biz_order","sys_dept"); // 设置需要生成的表名
//                            .addTablePrefix("sys_"); // 设置过滤表前缀 (生成的类名将不包含 sys_)

                    // 1. Entity (DO) 策略
                    builder.entityBuilder()
                            .enableLombok()             // 开启 Lombok
                            .enableTableFieldAnnotation() // 开启字段注解
                            .logicDeleteColumnName("del_flag") // 逻辑删除字段名
                            .logicDeletePropertyName("delFlag")
                            .enableFileOverride();

                    // 2. Mapper 策略
                    builder.mapperBuilder()
                            .enableMapperAnnotation()   // 开启 @Mapper 注解
                            .enableBaseResultMap()     // 生成通用的 ResultMap
                            .enableBaseColumnList().enableFileOverride();    // 生成通用的 ColumnList

                    // 3. Service 策略 (如不需要可注释掉)
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 Service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl").enableFileOverride();

                    // 4. Controller 策略 (如不需要可注释掉)
//                    builder.controllerBuilder()
//                            .enableRestStyle(); // 开启 @RestController
                })
                // 使用 Velocity 引擎
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}
