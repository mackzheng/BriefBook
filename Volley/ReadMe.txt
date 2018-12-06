1.Volley的get和Post请求方式
   Get与Post

   StringResponse
   JsonObjectResponse
   JsonArrayResponse

2.Volley网络请求队列的建立和取消

3.Volley 与Activity生命周期的联动
  1.request销毁
  2.Tag

4.Volley 的简单的二次回调封装


见过很多在activity直接写网络请求，其实这个完全没有达到解耦的目的。
虽然代码量上去了，但是在结构上设计完全没有达到解耦的要求。
一旦需要对异常处理，那就要把整个工程的网络请求都要处理一遍。

1.第一步： 简单实用Volley
  MainActivity -> volley_get()
  MainActivity -> volley_post()

2.第二步： 简单封装Volley
这里作为基类：
VolleyRequest   请求，最为请求入口
VolleyResponse  返回，我们可以出里返回的统一特性。
VolleyDelegate  回调，这里是上层实现的接口层。
整体来讲MainActivity只需要关心请求和回调。
请求：
MainActivity ->   VolleyRequest.requestGet(this,url,"volley_get",this);
MainActivity ->   VolleyRequest.requestPost(this,url2,"volley_get",map,this);
回调：
MainActivity implements VolleyDelegate 只需要实现这两个方法
    public void onSuccess(String response)
    public void onError(String error)

3.第三步： 考虑还有没有优化地方。
实际上 VolleyDelegate 作为统一的出口，必然会有局限。
比如：一个项目里面有几个模块，但是模块的返回协议不一样，有些是C/C++后台数据，有些是JavaWeb后台数据，有些是PHP后台数据。
这些后台协议的包头和返回数据结构 如果都不太相同。怎么能够让终端适配更多的业务，然而不用去改动大部分的代码。
如果在当前这个工程里面做上面协议的集成，是一个很痛苦的事情。
当然直接复用肯定能够解决问题，能否有更好的结构来解决这个问题，最终能够让模块解耦，然UI和数据分离，无缝替换。

将数据处理和Activity 解耦，在businessLayer层处理完所有的数据。同时可以在BusinessLayer层构建ViewModel数据。

baseNetwork
    |-VolleyDelegate
    |-VolleyRequest
    |-VolleyResponse

businessLayer
    |-base            业务层基类
        |-BaseManager
    |-home            首页业务
        |-.....
    |-main            主页业务
        |- IMainDelegate
        |- IMainManager
        |- MainManager
    |-BusinessProtal  业务统一入口

ui层
MainActivity implements IMainDelegate
     |
     |-BusinessProtal.mainManager().getPhoneArea(this,url,"volley_get",this);

这也只是一种结构方式，还有很多其他的结构。
