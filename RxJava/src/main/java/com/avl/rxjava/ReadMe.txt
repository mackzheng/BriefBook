
RxJava的线程切换
1.Schedulers.immediate()
2.Schedulers.newThread()
3.Schedulers.io()
4.Schedulers.computation()
5.AndroidSchedulers.mainThread()

Rxjava如何进行线程控制
1.subScribeOn(Schedulers)         处理
2.ObserverOn(AndroidSchedulers)   消费