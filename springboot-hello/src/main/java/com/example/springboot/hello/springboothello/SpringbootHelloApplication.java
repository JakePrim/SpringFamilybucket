package com.example.springboot.hello.springboothello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @SpringBootConfiguration ：Springboot的配置类 -》 @Configuration ：标明这是一个配置类
 * @EnableAutoConfiguration：开启自动配置功能 ->
 *          @AutoConfigurationPackage:自动配置包:在当前类的约定的包下进行扫描 如果不是约定的基础包那么无法加载Spring容器中
 *              @Import(AutoConfigurationPackages.Registrar.class):包启动类所在的包进行默认扫描，该包下的所有的类都会被扫描到Spring容器中进行管理
 *          @Import({AutoConfigurationImportSelector.class})：加载指定的类到Spring容器中去
 *              AutoConfigurationImportSelector：自动配置导入选择器
 *              根据项目来判断你的项目需要哪些配置信息，然后把默认的配置内容导入Spring容器进行管理
 * @ComponentScan：组件扫描和自动装配，用来指定扫描容器的范围
 */
@SpringBootApplication //Spring boot 启动类注解
    //如果去掉注解 则无法启动
public class SpringbootHelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootHelloApplication.class, args);
    }

}
