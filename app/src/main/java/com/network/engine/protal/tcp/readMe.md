这里可以换成 netty mina xmpp
长链处理 参考 netty

##TCP开发常见问题：
###问题1、粘包问题
解决方法一：TCP提供了强制数据立即传送的操作指令push，TCP软件收到该操作指令后，就立即将本段数据发送出去，而不必等待发送缓冲区满；
解决方法二：发送固定长度的消息
解决方法三：把消息的尺寸与消息一块发送
解决方法四：双方约定每次传送的大小
解决方法五：双方约定使用特殊标记来区分消息间隔
解决方法六：标准协议按协议规则处理，如Sip协议

###问题2、字符串编码问题
将中文字符串用utf8编码格式转换为字节数组发送时，一个中文字符可能会占用2～4个字节（假设为3个字节），这3个字节可能分3次接收，接收端每次接收完后用utf8编码格式转换为字符串，就会出现乱码，并导致接收长度计算错误的情况。
解决方法一：以字节数做为消息长度的计算单位，而不是字符个数。
解决方法二：发送方和接收方都采用unicode编码格式。

###问题3、长连接的保活问题
标准TCP层协议里把对方超时设为2小时，若服务器端超过了2小时还没收到客户的信息，它就发送探测报文段，若发送了10个探测报文段（每一个相隔75S）还没有收到响应，就假定客户出了故障，并终止这个连接。因此应对tcp长连接进行保活。

###问题4、缓冲区脏数据问题
同步发送的拷贝，是直接拷贝数据到基础系统缓冲区，拷贝完成后返回；
异步发送消息的拷贝，是将Socket自带的Buffer空间内的所有数据，拷贝到基础系统发送缓冲区，并立即返回；
因此异步发送时缓冲区设置不好会导致接收到脏数据的问题，如下所示：
第一次发送数据：1234567890
第一次接受数据：1234567890
第二次发送数据：abc
第二次接受数据：abc4567890
请参考：http://www.cnblogs.com/tianzhiliang/archive/2010/09/08/1821623.html
解决方法一：将缓冲区的大小设置为实际发送数据的大小。

###问题5、内存碎片问题
频繁的申请缓冲区会导致内存碎片的问题。
解决方法一：使用对象池和内存池。
请参考MSDN：http://msdn.microsoft.com/zh-cn/library/bb517542(v=vs.100).aspx
http://msdn.microsoft.com/zh-cn/library/system.net.sockets.socketasynceventargs.socketasynceventargs(v=vs.100).aspx


###问题6、乱序问题
多个线程使用异步通信方式向同一个接收端(socket)同时发送数据，会导致接收端接收的数据混乱。如下所示：
线程1第一次发送：123456789，假设未发送完，只发送了123
线程2第一次发送：abcdefgh，假设未发送完，只发送了abc
线程1第二次发送：456789，发送完成
线程2第二次发送：defgh，发送完成
接收端最终接收的数据为：123abc456789defgh。
解决方法一：一个连接的发送端线程排队发送数据。



#TCP的Socket编程，要做到质量稳定可靠效率高，对市场上90%的开发人员来说，是一项难度极高的工作。笔者有多年的socket开发经验，今天主要介绍socket编程中常见问题及注意事项： 
* 对于可变包长，必须定义包头，并在包头中定义包的总长度
    由于socket是字节流，就像流水一样，对于传输多个包的socket数据流来说，从中间无法得知一个包的起始位置，从中间位置观察数据包的特征也是一个不靠谱的做法。因此，只能从包的第一个字节起，一个一个计算每一个包的位置。
     通常的做法是，对每一个传输的数据包，定义一个包头和包体。包头为固定长度，通常在其中定义版本号、包体的长度（或总包的长度）、以及包类型字段。包体通常是可变长度的内容。收到包的一方，解析socket数据流时，先解析固定长度的包头，从包头中得到整个包的长度，然后取得包体内容，紧接着取下一个包的包头和包体，如此循环下去，就能顺序收到每一个包。

* socket接收调用一次不保证收到一个完整数据包
    调用socket的接收函数时，调用一次，可能会收到一个完整的对方发来的数据包，也可能只收到1/2个数据包，也可能1.5个数据包，也可能收到任何长度的包，或收到0个字节，这些情况都可能发生，这是正常情况，不是错误。当然，由于socket是全双工的字节流，你只可能收到一个一个字节，不可能收到半个字节，或2.5个字节。原因还是在于TCP是一个字节流，无头无尾，你只能根据socket收到的字节，自己来拼装成一个一个顺序的应用层的数据包。socket无法给你一个完整的数据包，只能给你一个一个收到的字节。
    接收分为阻塞和非阻塞，默认是阻塞模式。在调用接收函数时，如果是阻塞模式，将造成程序阻塞在该函数中，长时间不响应，程序不能处理其他工作，造成用户体验差；如果是非阻塞模式，一次调用无论成功或是失败，函数立即返回，如果长时间无数据可接收，又会导致CPU为100%的问题。所以，这里一般都需要使用多线程处理，小心地处理各种正常和异常情况。
   此外，网上一般都只有讨论阻塞的情况，对非阻塞的情况下的各种返回值，哪种是正常，哪种是阻塞，哪种是已经断开，资料非常少，需要自己的测试和验证去保证质量。

* 同理，socket发送调用一次也不保证整个包都发送出去
    调用socket的发送函数时，调用一次，也不保证整个包发送完成。通常对于阻塞而已，只有发送完成或失败才返回；对于非阻塞类型，发送一部分就可能返回，因此，需要检查返回值，如果只发送了一部分，需要再次调用send函数，以便把剩下的数据包全部发送出去，才能发送下一个数据包。
   与接收函数的调用类似，在调用发送函数时，如果是阻塞模式，将造成程序阻塞在该函数中，长时间不响应，程序不能处理其他工作，造成用户体验差；如果是非阻塞模式，一次调用无论成功或是失败，函数立即返回，如果长时间发送不出去，又会导致CPU为100%的问题。所以，这里一般也是需要使用多线程处理，小心地处理各种正常和异常的情况。
   此外，网上一般都只有讨论阻塞的情况，对非阻塞的情况下的各种返回值，哪种是正常，哪种是阻塞，哪种是已经断开，资料非常少，需要自己的测试和验证去保证质量。

*   socket收发可以同时进行
  由于socket是全双工的字节流，全双工意味着接收的时候也可以同时发送，发送的时候也可以同时接收。因此，如果软件要求高性能，一般采用多线程处理，才能达到收和发同时进行的效果。
  但是，需要注意，对于SSL而言，并不是全双工的，而是单工的，意味着SSL在发送的时候不能接收，接收的时候也不能发送。因此，SSL的效率，即使不考虑加密的影响，理论效率也只是普通socket的一半，如果考虑加密等因素，效率下降的就会更明显了。

* 大小字节序转换
      世界上的电脑CPU分为大端法和小端法两种。通常网络传输时都采用大端对齐法。对于AIX等系统，是大端对齐；而Windows、Linux等系统则是小端对齐。对于超过一个字节的short、int、int64及相应的unsigned数据类型，都需要进行大小字节序的转换。操作系统本身提供了ntohs和ntohl对16位和32位字节的整形进行转换，但并没有提供64位的字节序转换函数，此外，也没有提供float和double类型的数据类型进行转换的函数，怎么办呢？可以参考我写的另外一篇文章《socket编程必备函数：字节序转换C++模板函数，一劳永逸地代替ntoh或hton等函数》，这篇文章中，使用一个C++模板函数对所有的数据类型的转换进行了统一，使用简单方便，一劳永逸。

总之，socket编程的难度还是非常高的，必须有相当多的积累才可能编出质量过硬的软件。如果大家觉得难度过大，或者时间精力不足以从头开始编码，建议使用第三方的socket封装库来实现，专业的事情留给专业的人来做，质量更有保证，比如waisock就是一个非常优秀的socket封装库，官网是http://waisock.szxunxun.com/ ，大家不妨试试。
 