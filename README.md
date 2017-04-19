## 简介

EventBus、Otto、RxBus是Android端优化的publish/subscribe事件总线，简化了应用程序内各组件间、组件与后台线程间的通信。比如请求网络，等网络返回时通过Handler或Broadcast、LocalBroadcast通知UI，两个Fragment之间需要通过Listener通信，这些需求都可以通过EventBus实现。

## 应用场景

比如：由界面 A 跳转到界面 B ，然后点击 B 中的 button, 现在要更新 界面 A 的视图。再比如：界面有一个 界面 A,A 里面的有个 Fragment, 点击 Fragment 中的一个 button,跳转到界面 B, 点击界面 B的 button 要更新界面 A 的 Fragment 的视图，等等。

我们可以看出上面举例的两种场景，以前可以用startActivityForResult 和 interface 的方式实现的话,会比较麻烦,并且产生了很多的状态判断和逻辑判断,并且可能产生很多不必要的 bug, 代码量也比较大和繁琐,使用事件总线就可以能容易的避免这些问题。

## 对比

- Guava EventBus首先实现了一个基于发布订阅的消息类库，默认以注解来查找订阅者
- Otto借鉴Guava EventBus，针对Android平台做了修改，默认以注解来查找订阅、生产者
- EventBus和前两个都很像，v2.4后基于反射的命名约定查找订阅者，根据其自己的说法，效率上优于Otto
- 相邻组件还是用intent传递数据
- Otto是基于UI线程且不能发送黏性事件
- 都是基于观察者模式，用RxJava能轻松实现，但RxBus需要自己处理异常，但体积小
- EventBus可切换线程，3.0后也加入方法注解，建议使用EventBus，也就50K


## Thanks

- [EventBus](https://github.com/greenrobot/EventBus)
- [Otto](https://github.com/square/otto)
- [RxBus](https://github.com/YoKeyword/RxBus)


## License
   Apache-2.0
