# MySpringMVC 第二版本
该 web 框架基本功能完成。由于是极简版本，可能存在很多 bug。很多功能解析点不完善。

下面先看看各个包的作用

- annotation : 该包下主要放 spring 框架中常见的注解。

- bean : 该包放的是 bean 对象，将 xml 或注解中读取的信息拼装成 beanInfomation 对象用于注册到 ioc 容器中

- beanfactory : bean 工厂，用于注册 bean 和获取 bean。也包含或去 url 映射的链接。

- context : 核心调用类

- conversion : 参数转换，支持多种参数，暂不支持对象参数

- parameter : 参数发现，基于 ASM 的方式获取方法参数名。

- resources : 资源解析，包含 xml 等资源

- support : 注解注册，多种注解注册构建不同 bean 信息

- test : 测试框架是否能运行（能运行就好）

- web : 核心分派器，将请求分派给多个bean

- web.method : 方法处理，内部将参数进行绑定。


resources 文件夹下：
test.xml 用于测试 xml 中 bean 注入。 

