## 简介
四者都是 Android 端优化的 publish/subscribe事件总线，简化了应用程序内各组件间、组件与后台线程间的通信。比如请求网络，等网络返回时通过 Handler 或 Broadcast、LocalBroadcast 通知 UI，两个 Fragment 之间需要通过 Listener 通信，这些需求都可以通过事件总线实现。

## 应用场景
比如：由界面 A 跳转到界面 B ，然后点击 B 中的 button, 现在要更新 界面 A 的视图。再比如：界面有一个 界面 A, A 里面的有个 Fragment, 点击 Fragment 中的一个 button, 跳转到界面 B, 点击界面 B 的 button 要更新界面 A 的 Fragment 的视图，等等。

我们可以看出上面举例的两种场景，以前可以用 startActivityForResult 和 interface 的方式实现的话，会比较麻烦，并且产生了很多的状态判断和逻辑判断，并且可能产生很多不必要的 bug, 代码量也比较大和繁琐，使用事件总线就可以能容易的避免这些问题。

## 对比
- Guava EventBus 首先实现了一个基于发布订阅的消息类库，默认以注解来查找订阅者，该库是 google，体积庞大
- Otto 借鉴 Guava EventBus，针对 Android 平台做了修改，默认以注解来查找订阅、生产者
- EventBus 和前两个都很像，默认以注解来查找订阅者，效率上优于 Otto，支持 Sticky
- 都是基于观察者模式，用 RxJava 能轻松实现，RxBusOld 需要自己处理异常（在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出==》重新订阅事件），但体积小
- EventBus、RxBus（支持tag） 可在方法注解中切换线程，其他的需要自己判断线程状态
- Sticky 黏性事件：先发送了再注册也能接收到（相邻组件传递数据还是用 Intent 的 Bundle）
- 不考虑体积大小、没去实测效率，EventBus、RxBus > Otto > RxBusOld > Messenger，建议EventBus（支持黏性、也可自定义tag）把。

## Thanks
- [EventBus](https://github.com/greenrobot/EventBus)
- [Otto](https://github.com/square/otto)
- [RxBusOld](https://github.com/YoKeyword/RxBus) 
- [RxBus](https://github.com/AndroidKnife/RxBus) 
- [Messenger](https://github.com/Kelin-Hong/MVVMLight) 

## License
   Apache-2.0
