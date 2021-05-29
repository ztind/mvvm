
#### 工程概要
mvvm：base library <br/>
sample：project demo

#### 软件架构
- Kotlin
- MVVM + Jetpack + Repository + Retrofit2 + Okhttp3 + Flow + Coroutines

#### 使用示例
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


