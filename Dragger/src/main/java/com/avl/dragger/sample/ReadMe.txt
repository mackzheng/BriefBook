Dagger2主要的注解：

Binds
BindsInstance
BindsOptionalOf
Component
Lazy
MapKey
MembersInjector
Module
Provides
Reusable
Subcomponent

实际上这个框架和Spring的IOC是同一个方式。
感觉客户端整个架构，要渐渐走向了JavaEE开发的模式。

Retrofit -> Struts     Proxy：动态代理
Dagger2  -> Spring     IOC：依赖注入
GreenDao -> Hibernate  ORM：对象关系映射

这里讨论主要注解的使用方法和场景: