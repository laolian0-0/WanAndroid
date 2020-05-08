# WanAndroid

个人练习项目，api来自玩安卓网站，java语言，基于MVVM模式开发，使用Rxjava+Retrofit+DataBinding+Glide+CC组件化等。

# APP结构

从上往下。

base（MVVM基类封装等）

network（网络请求封装）

common（组件化的公共层）

app（main）     home（首页）   ……

# MVVM核心基类

- View层核接口IBaseView

  ```java
  public interface IBaseView {
      /**内容页*/
      void showContent();
      /**显示加载页*/
      void showLoading();
      /**显示刷新数据为空的页面*/
      void onRefreshEmpty();
      /**显示刷新错误页面*/
      void onRefreshFailure(String message);
  }
  ```

- ViewModel

  ```java
  public interface IBaseViewModel<V> {
  	/**绑定view*/
      void attachUI(V view);
  	/**获取view*/
      V getPageView();
  	/**判断绑定状态*/
      boolean isUIAttached();
  	/**解绑*/
      void detachUI();
  }
  ```

  ```java
  public class BaseViewModel<V, M extends SuperBaseModel> extends ViewModel implements IBaseViewModel<V> {
      protected M model;
      /**view弱引用*/
      private Reference<V> mUIRef;
      @Override
      public void attachUI(V view) {
          mUIRef = new WeakReference<>(view);
      }
  	……
  }
  ```

- Model

  有三个类，下面两个都继承SuperBaseModel 
  
  SuperBaseModel 
  ->BaseModel  正常的model
  ->BasePagingModel 有分页数据的model
  
  ```java
  public abstract class SuperBaseModel<T> {
      protected Handler mUiHandler = new Handler(Looper.getMainLooper());
      protected ReferenceQueue<IBaseModelListener> mReferenceQueue;
      protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> mWeakListenerArrayList;
      private BaseCacheData<T> mData;
      protected CompositeDisposable compositeDisposable;
     /**
       * 有分页和无分页的实现方式可能不一样。所以交给子类去继承实现
       */
      protected interface IBaseModelListener {
      }
      public SuperBaseModel() {
          ……//初始化
      }
      /**
       * 注册监听
       *
       * @param listener
       */
      public void register(IBaseModelListener listener) {
          ……
      }
      public void unRegister(IBaseModelListener listener) {
          ……
      }
      ……
     /**刷新数据*/
      public abstract void refresh();
      /**加载数据*/
      protected abstract void load();
      ……
      //对缓存数据的处理，分app级别默认就有的，和请求缓存
      ……
  }
  ```
  
  ```java
  public abstract class BaseModel<T> extends SuperBaseModel<T>{
  
      public interface IModelListener<T> extends IBaseModelListener{
          void onLoadFinish(BaseModel model,T data);
          void onLoadFail(BaseModel model,String prompt);
      }
      ……
  }
  ```
  
  ```java
  public abstract class BasePagingModel<T> extends SuperBaseModel<T> {
      protected boolean isRefresh = true;
      protected int pageNumber = 0;
  
      public interface IModelListener<T> extends IBaseModelListener {
          void onLoadFinish(BasePagingModel model, T data, boolean isEmpty, boolean isFirstPage, boolean hasNextPage);
  
          void onLoadFail(BasePagingModel model, String prompt, boolean isFirstPage);
      }
      ……
  }
  ```
  
- BaseActivity

  ```java
  abstract class BaseActivity <V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements IBaseView {
   	protected VM viewModel;
      protected V viewDataBinding;
      private LoadService mLoadService;
     /**layout id*/
      public abstract @LayoutRes
      int getLayoutId();
  
      /**
       * viewModel
       */
      protected abstract VM getViewModel();
  
      protected abstract int getBindingVariable();
      ……
  }
  ```

# network

1，Retrofit+Rxjava封装，简化调用流程，一句话调用，
2，自定义截拦器添加默认请求头，
3，自定义错误处理根据返回的数据解析对错误的情况进行处理。
4，Retrofit请求完毕之后通过Rxjava派遣请求结果。
5，支持多域名。

# 目前进度

搭建整体框架

首页基本效果



## 效果

吸顶效果 

自定义TitleLayout + 自定义NestedScrollView + 自定义RefreshHeader 实现。

![首页吸顶效果](md_images/吸顶效果.gif)

# 关于作者

email：[3077075551@qq.com](mailto:3077075551@qq.com)
