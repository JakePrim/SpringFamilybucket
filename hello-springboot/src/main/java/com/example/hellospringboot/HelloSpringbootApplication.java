package com.example.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * curl http://localhost:8080/actuator/health 健康检查
 * mvn clean package -Dmaven.test.skip 打包 跳过测试
 * java -jar hello-springboot-0.0.1-SNAPSHOT.jar 直接java -JAR 就可以跑起来
 */
@SpringBootApplication
@RestController
public class HelloSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringbootApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello(){
		return "Hello Spring";
	}

}
