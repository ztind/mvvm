
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
- Activity
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

- Fragment
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


