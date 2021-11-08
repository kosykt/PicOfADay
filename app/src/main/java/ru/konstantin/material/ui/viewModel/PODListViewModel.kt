package ru.konstantin.material.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.konstantin.material.BuildConfig
import ru.konstantin.material.model.ViewState
import ru.konstantin.material.ui.picture.PODRetrofitImpl
import ru.konstantin.material.ui.picture.PODServerResponseData
import ru.konstantin.material.ui.picture.PictureOfTheDayAPI

class PODListViewModel(
    val liveDataForViewToObserve: MutableLiveData<ViewState> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(startDate: String, endDate: String): LiveData<ViewState> {
        sendServerRequestGetPODList(startDate, endDate)
        return liveDataForViewToObserve
    }

    private fun sendServerRequestGetPODList(startDate: String, endDate: String) {
        liveDataForViewToObserve.value = ViewState.Loading
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            ViewState.Error(Throwable("You need API key"))
        } else {
//            val retrofit = retrofitImpl.getRetrofitImpl().getPictureListByDateInterval(apiKey, "2021-09-21", "2021-09-23")
            val retrofit = retrofitImpl.getRetrofitImplPODList()

            val service: PictureOfTheDayAPI = retrofit.create(PictureOfTheDayAPI::class.java)
            val call: Call<List<PODServerResponseData>> =
                service.getPictureListByDateInterval(startDate, endDate, apiKey)
            call.enqueue(object : Callback<List<PODServerResponseData>?> {
                override fun onResponse(
                    call: Call<List<PODServerResponseData>?>,
                    response: Response<List<PODServerResponseData>?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            ViewState.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                ViewState.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                ViewState.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<List<PODServerResponseData>?>, t: Throwable) {}
            })
        }
    }
}
