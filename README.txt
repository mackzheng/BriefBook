# BriefBook
Android学习过程   https://www.jianshu.com/u/112f511aff19

使编译的速度加快的方法：
修改：settings.gradle 的内容

例如：只需要JavaBasics 可以如下配置：

//========================================
 //基础
 include ':JavaBasics'
 //include ':DataStructure'
 //include ':Arithmetic'
 //include ':DesignMode'
 //========================================

 //跨平台开发
 //include ':Hybris'
 //include ':Reactnative'
 //include ':Flutter'

 //========================================

 //相机
 //include ':Camera'
 //include ':Cameralive'
 //========================================

 //网络
 //include ':Retrofit'
 //include ':Okhttp'
 //include ':Volley'

 //========================================
 //观察者
 //include ':eventbus'
 //include ':RxJava'
 //========================================

 //注解，依赖注入
 //include ':Dragger'
 //include ':Butterknife'
 //========================================

 //数据库
 //include ':GreenDao'
 //========================================

 //js桥接器
 //include ':JsBridge'
 //========================================

 //性能优化
 //include ':LeakCanary'
 //include ':BlockCanary'
 //========================================

 //图片 多媒体
 //include ':Glide'
 //include ':GpuImage'
 //include ':MediaAv'
 //========================================

 //native开发
 include ':Jnistudy'
 //========================================

 //框架
 //include ':retrofit+dagger+rxjava+geendao'
 //include ':Arouter' 路由框架
 //========================================