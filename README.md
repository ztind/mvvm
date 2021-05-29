
#### 软件架构
mvvm:base library <br/>
sample：project demo

#### 技术概要
- Kotlin
- MVVM + Jetpack + Repository + Retrofit2 + Okhttp3 + Flow + Coroutines

#### 版本说明
- JDK 1.8
- Gradle 6.5
- Android Studio 4.1.2

#### 使用示例
'''

    class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>() {
        override fun setLayoutResId(): Int = R.layout.activity_main
    }

'''

'''

    class MainViewModel :BaseViewModel<MainRepository,ActivityMainBinding>(){
        private val mActivity by lazy {
            mLifecycleOwner as MainActivity
        }
        override fun initView() {

        }

        override fun initData() {

        }
    }

'''

'''

    class MainRepository :BaseRepository {

    }

'''
