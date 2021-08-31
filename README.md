
#### 工程概要

本项目基于Kotlin语言,采用MVVM架构封装了Jetpack里常用的基础组件到mvvm依赖库里，内部二次封装了:<br/>
1.Activity/Fragment + ViewModel + Repository模式<br/>
2.沉浸式状态栏<br/>
3.仿微信侧滑退出<br/>
4.Activity切换动画<br/>
5.网络请求<br/>
6.刷新组件<br/>
7.更多功能,待续...<br/>

#### 软件架构
- Kotlin
- MVVM + Jetpack + Repository + Retrofit2 + Okhttp3 + Flow + Coroutines

#### 快速开始

1) 在 project 的 build.gradle 文件中找到 allprojects{} 代码块添加

    ```
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
    ```

2) 在 app 的 build.gradle 文件中找到 dependencies{} 代码块添加

    ```
    dependencies {
        implementation 'io.github.ztind:mvvm:v1.0.0'
    }
    ```

#### 使用示例

- For Activity

    Activity:

        class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {

        }

    ViewModel:

        class MainViewModel :BaseViewModel<MainRepository,ActivityMainBinding>(){
            private val mActivity by lazy {
                mLifecycleOwner as MainActivity
            }
            override fun initView() {

            }

            override fun initData() {

            }
        }

    Repository:

        class MainRepository :BaseRepository {

        }

- For Fragment

    Fragment:

        class MessageFragment : BaseFragment<MessageViewModel,FragmentMessageBinding>() {

        }

    ViewModel:

        class MessageViewModel :BaseViewModel<MessageRepository,FragmentMessageBinding>(){
            private val mFragment by lazy {
                mLifecycleOwner as MessageFragment
            }

            override fun initView() {

            }

            override fun initData() {

            }
        }

    Repository:

        class MessageRepository : BaseRepository {

        }

- 网络模块

        AbstractRetrofits具体实现：
        class Retrofits : AbstractRetrofits() {
            /**
             * base url
             */
            override fun baseUrl(): String  = "https://gank.io/"
            /**
             * you token key
             */
            override fun tokenKey(): String = "Authorization"
            /**
             * you token value
             */
            override fun tokenValue(): String = "token"
            /**
             * 链接超时(秒)
             */
            override fun connectTime(): Long = 10
            /**
             * 读取超时(秒)
             */
            override fun readTime(): Long = 10
            /**
             * 写超时(秒)
             */
            override fun writeTime(): Long = 10
            /**
             * 请求头参数,可自定义添加
             */
            override fun headers(): HashMap<String, String> {
                val map = HashMap<String,String>()
                //手机厂商
                map["brand"] = android.os.Build.BRAND
                //手机型号
                map["model"] = android.os.Build.MODEL
                //手机系统版本
                map["release"] = android.os.Build.VERSION.RELEASE
                //app版本
                map["version"] = "1.0.0"
                return map
            }
            /**
             * 自定义拦截器
             */
            override fun httpInterceptor(): Interceptor = LogIntercept2()
            /**
             * 类实例获取
             */
            companion object{
                private val instance : Retrofits = Retrofits()
                @Synchronized
                fun getRetrofitsInstance(): Retrofits {
                    return instance
                }
            }
            /**
             * http请求-响应日志拦截器(1)
             */
            private class LogIntercept1 : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    //todo...
                }
            }
            /**
             * http请求-响应日志拦截器(2)
             */
            private class LogIntercept2 : Interceptor {
                 //todo...
            }
        }

        数据接口定义：
        interface MeiZiAPI {
            @GET("api/v2/data/category/Girl/type/Girl/page/{page}/count/{size}")
            suspend fun getData(@Path("page") page: Int, @Path("size") size: Int) : HttpResponse<MutableList<MeiZi>?>
        }

        Repository 中Flow包装：
        class NetworDemoRepository :BaseRepository{
            private val meiziAPI = Retrofits.getRetrofitsInstance().unauthorizedService().create(MeiZiAPI::class.java)
            suspend fun getData(page:Int,size:Int):Flow<HttpResponse<MutableList<MeiZi>?>>{
                return flow {
                    val data = meiziAPI.getData(page,size)
                    emit(data)
                }.flowOn(Dispatchers.IO)
            }
        }

        ViewModel中请求：
        viewModelScope.launch {
            mRepository.getData(page,size)
                .onStart {
                    //request start
                }
                .transform {
                    emit(CommonTransformHandler(it))
                }
                .catch {
                    CommonExceptionHandler.handler(it,loadState,mActivity){
                        ToastUtils.showShort(it.message)
                    }
                }
                .onCompletion {
                    //request end
                }
                .collectLatest {
                    //get data to refresh ui
                }
        }

### LICENSE

       Copyright ztind

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

           http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

