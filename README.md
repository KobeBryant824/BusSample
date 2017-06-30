## 简介
四者都是 Android 端优化的 publish/subscribe事件总线，简化了应用程序内各组件间、组件与后台线程间的通信。比如请求网络，等网络返回时通过 Handler 或 Broadcast、LocalBroadcast 通知 UI，两个 Fragment 之间需要通过 Listener 通信，这些需求都可以通过事件总线实现。

## 应用场景
比如：由界面 A 跳转到界面 B ，然后点击 B 中的 button, 现在要更新 界面 A 的视图。再比如：界面有一个 界面 A, A 里面的有个 Fragment, 点击 Fragment 中的一个 button, 跳转到界面 B, 点击界面 B 的 button 要更新界面 A 的 Fragment 的视图，等等。

我们可以看出上面举例的两种场景，以前可以用 startActivityForResult 和 interface 的方式实现的话，会比较麻烦，并且产生了很多的状态判断和逻辑判断，并且可能产生很多不必要的 bug, 代码量也比较大和繁琐，使用事件总线就可以能容易的避免这些问题。

## 对比
- Guava EventBus 首先实现了一个基于发布订阅的消息类库，默认以注解来查找订阅者，该库是 google，体积庞大
- Otto 借鉴 Guava EventBus，针对 Android 平台做了修改，默认以注解来查找订阅、生产者
- EventBus 和前两个都很像，默认以注解来查找订阅者，效率上优于 Otto，支持 Sticky（黏性事件：先发送了再注册也能接收到（相邻组件传递数据还是用 Intent 的 Bundle比较好））
- 都是基于观察者模式，用 RxJava 能轻松实现， RxBus 结合 Otto 注解、EventBus 线程切换，并支持 Tag 指定接收方（这点非常好，有时需要经常发送 String、List 等数据且不想定义多个相似实体，又不想其他地方回调）
- 事件回调中，如果发生异常：EventBus、Messenger 默认上线会抓取异常，Otto、RxBus 会抛运行时异常且订阅结束，这很危险，所以每个回调里最好自己trycatch
- 考虑事件的分发效率、库的更新速度、代码优雅性，个人感觉 EventBus（支持 Tag 注解就真的无敌了） > RxBus（自己处理异常） > Messenger > RxBusOld > Otto

## Thanks
- [EventBus](https://github.com/greenrobot/EventBus)
- [Otto](https://github.com/square/otto)
- [RxBus](https://github.com/AndroidKnife/RxBus) 
- [RxBusOld](https://github.com/YoKeyword/RxBus) 
- [Messenger](https://github.com/Kelin-Hong/MVVMLight) 

## License
- [Apache-2.0](http://www.apache.org/licenses/LICENSE-2.0)