
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

        class Retrofits : AbstractRetrofits() {
            /**
             * base url
             */
            override fun baseUrl(): String  = "you api url"

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
                  val instance : Retrofits = Retrofits()
                  @Synchronized
                  fun getRetrofitsInstance(): Retrofits {
                      return instance
                  }
            }
        }

#### 代码提交规范
- `new:`  新功能 new: 增加xxx功能或模块
- `update:`   功能变更、调整、优化等。  update: 更新xxx内容
- `feature:`  对需求的响应，通常是有需求编号的情况。 feature:#需求编号，针对需求做了什么调整。
- `bugfix:`   bug修复，通常是有指定bug号的情况。  bugfix:#BUG编号，具体描述修复内容。
- `refactor:` 重构，不影响功能  refactor: 重构xxx功能及详细描述
- `format:`   代码格式调整    format: 格式化了xxx代码
- `comment:`  注释完善  comment: 完善了xxx注释信息
- `config:`   配置文件修改    config: 调整了xxx配置信息
- `junit:`    单元测试  junit: 进行了xxx单元测试
- `publish:`  发布时更新   publish: 更新xxx版本信息

