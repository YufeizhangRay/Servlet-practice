# Servlet-practice
Servlet权限控制模型  

2018年6月  
  
在学习SpringMVC以及整合SSH的时候，感觉有的地方不能理解到细节之处(例如为什么ModelAndView里面的数据可以被JSP拿到)，之后发现是学习的顺序存在问题，导致自己对Servlet与JSP的所知少之又少。于是特意专门学习了一下，并且完成了一个由Servlet和JSP构建的用户登录权限控制模型(这里又学习到了cookie与session的新知识~)，同时也对之前学习SpringMVC的工作原理及过程有了更进一步的理解。  
  
可以自定义一个类然后实现`Servlet接口`并实现其中的接口方法来获得自己的`servlet`，同时需要在`web.xml`文件中配置自定义的`servlet`。
  
`Servlet`的生命周期：  
>1.构造方法  
`Tomcat`(`servlet`容器)在收到浏览器的`URL`请求后，会自动用构造方法实例化`servlet`。构造方法`只会调用一次`，所以在`web容器`中的每一个`servlet`都是`单例`的。  
2.init  
构造方法之后马上被执行，且`只执行一次`，作用是`初始化`。构造化方法也能做初始化，但是`init方法`带有参数`ServletConfig`，作用更大。  
3.service  
每次在浏览器刷新请求的时候都会调用一次`service`方法。  
4.destroy  
`只执行一次`,在`web应用`被卸载前执行。用于释放`servlet`占用的计算机资源。  
  
![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/servlet%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.jpg)  
ServletConfig接口：  
>可以读取到`web.xml`文件中`servlet`的初始化值：  
>>`String getServletName()`获取当前`servlet`的名字；  
`Sting getInitParameter(String name)`获取当前指定名称的`初始化参数的值`；  
`Enumeration getInitParameterName()`获取当前`Servlet`所有`初始化参数的名字`组成的枚举；  
`ServletContext getServletContext()`获取代表当前`web应用`的`ServletContext`对象。`ServletContext`对象是一个`域对象`，服务器不关闭`ServletContext`对象不消失，整个`web应用`的所有的`servle`共享`ServletContext`对象的数据。  
  
GenericServlet抽象类：实现了`ServletConfig接口`   
>若开发者每次都去实现`ServletConfig接口`，则每次都需要实现大量的接口方法，但是这些方法大部分却很少会用到。`GenericServlet抽象类`本身实现了`ServletConfig接口`，替开发者实现了平时用不到的`ServletConfig接口`方法(同时将`service`方法重定义为`abstract`方法，以留给子类实现),从而减少了开发的代码量。  
  
HttpServlet抽象类：继承了`GenericServlet抽象类`并实现了`service`方法  
>此时开发者仍然需要每次都将参数`ServletRequset`与`ServletRespouse`强制转换成`HttpServletRequset`与`HttpServletRespouse`，以此拿到方法名去判断请求的类型，再根据不同的请求类型做出不同的响应，仍然很繁琐，需要简化。于是`HttpServlet`在`service`里调用了`重载`的`service`方法，直接传入已经强制转换好的`HttpServletRequset`与`HttpServletRespouse`为参数，并且在重载的`servic`e方法中根据方法类型“`GET`”或者“`POST`”选择调用`doGet`或者`doPost`方法。
此时继承`HttpServlet抽象类`的子类只需要根据不同的业务需求`重写``doGet`和`doPost`方法即可，大大减少了繁琐的操作。  
  

![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/servletConfig.jpg)

![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/Servlet%E4%BD%93%E7%B3%BB%E7%BB%93%E6%9E%84.jpg)  

JSP(Java Server Pages)：  
>`JSP`是一种动态网页技术标准，本质上是一个`Servlet`。`index.jsp`文件在被访问的时候首先会自动将该页面翻译成一个`index_jsp.java`文件，即`Servlet`代码。这个类继承了`HttpJspBese`，而`HttpJspBese`又继承了`HttpServlet`，由于继承属于`is-a`的关系，于是`JSP`本质上就是一个`Servlet`，需要在`Web容器`中执行。而`Servlet`想要在容器中执行就需要在`web.xml`文件中配置，对于`JSP`这些由容器自动执行。  
  
`JSP` 9个内置对象  
>1.`pageContext` 表示页容器，代表当前页面运行的一些属性 EL表达式、 标签 、上传   
2.`request` 其实就是`HttpServletRequest`，可以用于服务器端取得客户端的信息：头信息 、Cookie 、请求参数 ，最大用处在MVC设计模式上   
3.`response` 其实就是`HttpServletReponse`，可以用于服务器端回应客户端信息：Cookie、重定向   
4.`session` 表示服务器与客户端所建立的会话，不同的JSP页面可以进行交互。表示每一个用户，用于登录验证上   
5.`application` 其实就是`ServletContext`的对象实例，表示整个服务器   
6.`config` 取得初始化参数，初始化参数在web.xml文件中配置   
7.`exception` 表示的是错误页的处理操作 只能在<%@ page is isErrorPage = "true" %>的JSP文件中使用   
8.`page` 如同this一样，代表整个JSP页面自身   
9.`out` 输出 ，但是尽量使用表达式输出
  
>输入输出：`request`、`response`、`out`   
作用域间的通信：`session`、`application`、`pageContext`、`request`   
servlet对象：`page`、`config`  
错误对象：`exception`  
  
四个域对象的作用范围：  
>`pageContext`：只能应用在当前页面的一次请求中  
`request` ：只要在同一个请求中，无论经过多少动态资源都可以，但是只能是转发  
`session`：只要在一次新的会话中(浏览器不关闭)，无论经过多少动态资源，无论是转发还是重定向都可以，例如：购物车  
`application`：只要在web应用中，无论多少个新的会话，无论经过多少动态资源，无论是转发还是重定向都可以，例如：QQ聊天室  
  
请求的重定向与转发：
>1.`转发`是一种`服务器行为`，`request`被保存。另一个`servlet`或其他资源中的`request`对象，跟转发的为`同一个request`对象。由于是服务器行为，所以客户端的地址栏`不会改变`，从始到终只有`一次`，`一个`。    
2.`重定向`是一种`客户端行为`，实际上发送的是`两次请求`。`request`不会被保存，两个`servlet`中的`request不是同一个`，地址栏也会`发生改变`。  
3.`转发`只能转发到`web应用`内部的资源，`重定向`可以到`任何资源`。  
4.`转发`中地址用"/"表示`web应用`的根目录，`重定向`用地址用"/"表示`站点`的根目录。
