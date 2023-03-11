# 用到的技术

## 1.Junit

作用：单元测试
maven依赖：

```xml

<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
```

## 2.Lombok

作用：简化代码,用来简化getter,setter,toString,equals,hashCode等方法
maven依赖：

```xml

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.2</version>
</dependency>
```

## 3.Mybatis-plus

作用：简化mybatis的开发
maven依赖：

```xml

<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```

## 4.mysql-connector-java

作用：连接mysql数据库
maven依赖：

```xml

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.47</version>
</dependency>
```

## 5.spring-boot-devtools

作用：热部署
maven依赖：

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

具体实现步骤：
1.在pom.xml中添加依赖
2.在idea中设置
3.在application.yml中添加配置

```yaml
spring:
  devtools:
    restart:
      enabled: true #开启热部署
      additional-paths: src/main/java #监控的文件夹
      exclude: static/**,public/** #不监控的文件夹
  freemarker:
    cache: false #页面不加载缓存，修改页面后立即生效
```

## 6.swagger3

作用：接口文档
开启后网站地址：http://localhost:8080/api/swagger-ui/index.html
maven依赖：

```xml

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

## 7.knife4j

作用：接口文档美化
开启后网站地址：http://localhost:8080/api/doc.html
maven依赖：

```xml

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
```
步骤：
在上面配置Swagger的Swagger3Config中添加@EnableKnife4j注解，该注解可以开启knife4j的增强功能。





