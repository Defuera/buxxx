package ru.justd.bux.product.model

import android.util.Log
import okhttp3.*
import ru.justd.bux.app.model.ApiService
import rx.Emitter
import rx.Observable
import rx.Single
import javax.inject.Inject

class RemoteProductDataSource @Inject constructor(
        private val apiService: ApiService,
        private val okHttpClient: OkHttpClient
) {

    fun getListProducts(): Single<List<Product>> = apiService.getListProducts()

    fun getProduct(productId: String): Single<Product> = apiService.getProduct(productId)

    fun observeProduct(productId: String): Observable<Product> {

        return Observable.create<Product>(
                { emmiter ->
//                    val listener =

                    okHttpClient.newWebSocket(
                            Request.Builder().url("https://rtf.beta.getbux.com/subscriptions/me").build(),
                            object : WebSocketListener() {
                                override fun onOpen(webSocket: WebSocket, response: Response) {
                                    //connect to ticker channel
                                    webSocket.send("""{"subscribeTo":["trading.product.$productId"]}""")
                                }

                                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                                    emmiter.onError(t)
                                }

                                override fun onMessage(webSocket: WebSocket?, message: String) {
//                            val ticker = gson.fromJson<Ticker>(text, Ticker::class.java)
//                            subject.onNext(ShiffrTicker(GdaxApi.NAME, "BTC", "EUR", ticker.price))
                                    Log.i("DensTest", message)
                                }

                            }
                    )

                },
                Emitter.BackpressureMode.DROP)
    }

}