### 🥑 WanAndroid基础款（MVVM+Kotlin+Jetpack+组件化）

| 首页 | 项目 | 导航 |
| :--: | :--: | :--: |
| <img src="/github_imgs/w1.jpeg" width="256"/> | <img src="/github_imgs/w2.jpeg" width="256"/> | <img src="/github_imgs/w3.jpeg" width="256"/> |

| 登录 | 个人 | 收藏 |
| :--: | :--: | :--: |
| <img src="/github_imgs/w4.jpeg" width="256"/> | <img src="/github_imgs/w5.jpeg" width="256"/> | <img src="/github_imgs/w6.jpeg" width="256"/> |

---

### 🍓 项目介绍

项目采用组件化，架构如下：
| - | - | app<br>module | - | - |
| :--: | :--: | :--: | :--: | :--: |
| 用户<br>kb_user<br>module | 首页<br>kb_home<br>module | 项目<br>kb_project<br>module | 导航<br>kb_navigation<br>module | 个人<br>kb_me<br>module |
| - | - | 公共<br>kb_common<br>module | - | - |

`BaseViewModel.kt`
```kotlin
typealias vmBLOCK = suspend () -> Unit
open class BaseViewModel : ViewModel() {

    protected fun launch(block: vmBLOCK) {
        viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
    
    private fun onError(e: Exception) {
        Log.d("onError", "onError: $e")
    }
}
```
`BaseRepository.kt`
```kotlin
open class BaseRepository {
    suspend fun <T> dealResp(
        block: suspend () -> BaseResp<T>,liveData: RespStateData<T>,) {

        var result = BaseResp<T>()
        result.responseState = BaseResp.ResponseState.REQUEST_START
        liveData.value = result

        try {
            result = block.invoke()
            when (result.errorCode) {
                Constants.HTTP_SUCCESS -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_SUCCESS
                }
                Constants.HTTP_AUTH_INVALID -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ToastUtil.showMsg("认证过期，请重新登录！")
                    ARouter.getInstance().build(Constants.PATH_LOGIN).navigation()
                }
                else -> {
                    result.responseState = BaseResp.ResponseState.REQUEST_FAILED
                    ToastUtil.showMsg("code:" + result.errorCode.toString() + " / msg:" + result.errorMsg)
                }
            }

        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is HttpException,
                is ConnectException,
                -> {
                    //网络error
                    ToastUtil.showMsg("网络错误！")
                }
                else -> {
                    ToastUtil.showMsg("未知错误！")
                }
            }
            result.responseState = BaseResp.ResponseState.REQUEST_ERROR
        } finally {
            liveData.value = result
        }
    }
}
```
---

### 🥝 感谢
* [Retrofit](https://github.com/square/retrofit)
* [OkHttp](https://github.com/square/okhttp)
* [kotlinx-coroutines](https://github.com/Kotlin/kotlinx.coroutines)
* [Koin](https://insert-koin.io/docs/quickstart/android-viewmodel)
* [bannerviewpager](https://github.com/zhpanvip/BannerViewPager)
* [coil](https://github.com/coil-kt/coil/)
* [roundedimageview](https://github.com/vinc3m1/RoundedImageView)
* [flexbox](https://github.com/google/flexbox-layout)
* [ARouter](https://github.com/alibaba/ARouter)
* [mmkv](https://github.com/Tencent/MMKV/)
---

### 🍇 版本说明（持续更新...）
待完成：页面状态统一UI（Loading-UI，Error-UI）

V1.1 - 2022-08-30<br>
bug fix ... 

V1.0 - 2022-08-25<br>
项目上传，持续更新

---

### 🍋 License
```
Copyright [2022] [stew]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
