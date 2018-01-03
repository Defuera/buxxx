package ru.justd.bux.product.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.justd.bux.BuildConfig
import ru.justd.bux.app.model.ApiService
import ru.justd.bux.app.model.NetworkError
import ru.justd.bux.app.model.ServerError
import rx.Single
import java.io.IOException
import javax.inject.Inject

class RemoteProductDataSource @Inject constructor(
        private val apiService: ApiService,
        private val okHttpClient: OkHttpClient) {

    private val gson = Gson()

    fun getListProducts(): Single<List<Product>> {
        return Single.create<List<Product>> { emitter ->
            try {
                val response = okHttpClient
                        .newCall(
                                Request.Builder()
                                        .get()
                                        .url("${BuildConfig.HOST}core/16/products")
                                        .build()
                        )
                        .execute()

                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<List<Product>>() {}
                    emitter.onSuccess(gson.fromJson(response.body()!!.string(), typeToken.type))
                } else {
                    val error = gson.fromJson(response.body()!!.string(), ServerError::class.java)
                    emitter.onError(error)
                }

            } catch (e: IOException) {
                throw NetworkError()
            }

        }
    }

    fun getProduct(productId: String): Single<Product> = apiService.getProduct(productId)

}