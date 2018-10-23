# Servlet-practice
Servlet权限控制模型  

2018年6月  
  
在学习SpringMVC以及整合SSH的时候，感觉有的地方不能理解到细节之处(例如为什么ModelAndView里面的数据可以被JSP拿到)，之后发现是学习的顺序存在问题，导致自己对Servlet与JSP的所知少之又少。于是特意专门学习了一下，并且完成了一个由Servlet和JSP构建的用户登录权限控制模型(这里又学习到了cookie与session的新知识~)，同时也对之前学习SpringMVC的工作原理及过程有了更进一步的理解。  
  
可以自定义一个类然后实现Servlet接口并实现其中的接口方法来获得自己的servlet，同时需要在web.xml文件中配置自定义的servlet。
  
Servlet的生命周期：  
>1.构造方法  
Tomcat(servlet容器)在收到浏览器的URL请求后，会自动用构造方法实例化servlet。构造方法只会调用一次，所以在web容器中的每一个servlet都是单例的。  
2.init  
构造方法之后马上被执行，且只执行一次，作用是初始化。构造化方法也能做初始化，但是init方法带有参数ServletConfig，作用更大。  
3.service  
每次在浏览器刷新请求的时候都会调用一次service方法。  
4.destroy  
只执行一次,在web应用被卸载前执行。用于释放servlet占用的计算机资源。  
  
![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/servlet%E7%94%9F%E5%91%BD%E5%91%A8%E6%9C%9F.jpg)  
ServletConfig接口：  
>可以读取到web.xml文件中servlet的初始化值：  
>>String getServletName()获取当前servlet的名字；  
Sting getInitParameter(String name)获取当前指定名称的初始化参数的值；  
Enumeration getInitParameterName()获取当前Servlet所有初始化参数的名字组成的枚举；  
ServletContext getServletContext()获取代表当前web应用的ServletContext对象。ServletContext对象是一个域对象，服务器不关闭ServletContext对象不消失，整个web应用的所有的servle共享ServletContext对象的数据。  
  
GenericServlet抽象类：实现了ServletConfig接口  
>若开发者每次都去实现ServletConfig接口，则每次都需要实现大量的接口方法，但是这些方法大部分却很少会用到。GenericServlet抽象类本身实现了ServletConfig接口，替开发者实现了平时用不到的ServletConfig接口方法(同时将service方法重定义为abstract方法，以留给子类实现),从而减少了开发的代码量。  
  
HttpServlet抽象类：继承了GenericServlet抽象类并实现了service方法  
>此时开发者仍然需要每次都将参数ServletRequset与ServletRespouse强制转换成HttpServletRequset与HttpServletRespouse，以此拿到方法名去判断请求的类型，再根据不同的请求类型做出不同的响应，仍然很繁琐，需要简化。于是HttpServlet在service里调用了重载的service方法，直接传入已经强制转换好的HttpServletRequset与HttpServletRespouse为参数，并且在重载的service方法中根据方法类型“GET”或者“POST”选择调用doGet或者doPost方法。
此时继承HttpServlet抽象类的子类只需要根据不同的业务需求重写doGet和doPost方法即可，大大减少了繁琐的操作。  
  

![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/servletConfig.jpg)

![](https://github.com/YufeizhangRay/image/blob/master/%E5%9B%BE%E7%89%87/Servlet%E4%BD%93%E7%B3%BB%E7%BB%93%E6%9E%84.jpg)  

JSP(Java Server Pages)：  
JSP是一种动态网页技术标准，本质上是一个Servlet。index.jsp文件在被访问的时候首先会自动将该页面翻译成一个index_jsp.java文件，即Servlet代码。这个类继承了HttpJspBese，而HttpJspBese又继承了HttpServlet，由于继承属于is-a的关系，于是JSP本质上就是一个Servlet，需要在Web容器中执行。而Servlet想要在容器中执行就需要在web.xml文件中配置，对于JSP这些由容器自动执行。  
  
`JSP` 9个内置对象  
>1.`pageContext` 表示页容器 EL表达式、 标签 、上传   
2.`request` 服务器端取得客户端的信息：头信息 、Cookie 、请求参数 ，最大用处在MVC设计模式上   
3.`response` 服务器端回应客户端信息：Cookie、重定向   
4.`session` 表示每一个用户，用于登录验证上   
5.`application` 表示整个服务器   
6.`config` 取得初始化参数，初始化参数在web.xml文件中配置   
7.`exception` 表示的是错误页的处理操作   
8.`page` 如同this一样，代表整个jsp页面自身   
9.`out` 输出 ，但是尽量使用表达式输出
