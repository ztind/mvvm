
#### 工程概要
mvvm：base library <br/>
sample：project demo

#### 软件架构
- Kotlin
- MVVM + Jetpack + Repository + Retrofit2 + Okhttp3 + Flow + Coroutines

#### 使用示例
- Add mvvm library to you app build.gradle

        dependencies {
            implementation project(":mvvm")
        }

- For Activity

    Activity:

        class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
                override fun setLayoutResId(): Int = R.layout.activity_main
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
            override fun setLayoutResId(): Int = R.layout.fragment_message
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
    class Retrofits : AbstractRetrofits() {
        /**
         * base url
         */
        override fun baseUrl(): String  = "https://www.baidu.com"

        /**
         * token
         */
        override fun token(): String  = "you token"

        /**
         * 请求头参数:app版本名称
         */
        override fun version(): String = "1.0.0"

        /**
         * 请求头参数,可自定义添加
         */
        override fun headers(): HashMap<String, String> {
            val map = HashMap<String,String>()
            map["brand"] = android.os.Build.BRAND
            map["model"] = android.os.Build.MODEL
            map["release"] = android.os.Build.VERSION.RELEASE
            return map
        }

        override fun clientId(): String = "sample"

        override fun clientSec(): String = "sample"

        /**
         * 类实例获取
         */
        companion object{
            var instance : Retrofits?=null
                get() {
                    if (field==null){
                        field = Retrofits()
                    }
                    return field
                }
            @Synchronized
            fun getRetrofitsInstance(): Retrofits {
                return instance!!
            }
        }
    }


