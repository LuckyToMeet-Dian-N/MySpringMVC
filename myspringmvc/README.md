# MySpringMVC 第一版本
手写 SpringMVC 计划分多个版本进行，第一版本只是实现。

如果想看部署后情况，下载第一版本的手写 SpringmMVC 将其打成 war 包后放入 Tomcat 服务器运行集合。
默认的链接为 http://localhost:8080/test/test?userName=abc&gentle=123
可以看到结果。如果有上下文路径请在 http://locahost:8080/上下文/后面如上

第一版本仅仅为了实现而写代码，毫无艺术可言。仅仅提供思路即可。

第一版本的 springmvc 框架支持注解 @Service、@RequestMapping、@Controller、@Autowire。

支持参数名与请求参数进行数据绑定，底层使用 ASM 实现（JVM 默认不支持保留参数名字，编译后会变成 arg0 等，需要从字节码中拿取参数名）
支持类型类型有（Integer,Double,Long、String、request、response，以及自定义对象类型）等。

支持包扫描，可使用 property 文件配置扫描路径。
支持自动对象注入。

