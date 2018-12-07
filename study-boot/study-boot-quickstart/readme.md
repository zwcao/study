一、使用springboot全家桶，有两种方式。会规范所有springboot环境的所有版本，例如spring-boot-starter-web，spring-boot-maven-plugin等
1. 继承springboot父pom
<parent>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-parent</artifactId>
	<version>2.1.1.RELEASE</version>
</parent>
2. 增加springboot的依赖
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-dependencies</artifactId>
	<version>${spring-boot-starter.version}</version>
	<type>pom</type>
	<scope>import</scope>
</dependency>

二、SpringBoot的关键注解
1. 入口注解
必须是@SpringBootApplication或者@EnableAutoConfiguration

2. 自动装配AutoConfiguation
使用自动装备，有两种方式。第一种是@SpringBootApplication，第二种是在任意的@configuraiton注解上，增加@EnableAutoConfiguration
如果不想自动自动装备某class，可以排除

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
或者
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})

自动装配，可以自动装配配置文件，也可以自动装配bean
@Configuration 必要注解

@EnableAutoConfiguration：

自动装配配置：@Value("${idgen.db.url}")

自动装配bean：
一般用在bean上
@Conditional
@ConditionalOnMissingBean
@ConditionalOnBean
@ConditionalOnResource
@ConditionOnMissingBean
@ConditionalOnProperty(prefix = "mongodb", name = "enable", havingValue = "true")
@ConfigurationProperties(prefix = "mongodb")
@ConditionalOnWebApplication
@ConditionalOnExpression

3.自定义starter
一般作为开箱即用的功能jar时，用starter模式更好，但是项目可能scan不到你的jar的bean，这是用starter可以解决此问题。
在META-INF/spring.factories
org.springframework.boot.autoconfigurer.EnableAutoConfiguration=\
    org.study.boot.quickstart.configuration.MyConfiguration
通过它可以自动加载某些bean，达到启动的效果    
    
    