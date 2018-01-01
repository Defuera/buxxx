package ru.justd.bux.app.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import ru.justd.bux.BuildConfig
import rx.Emitter
import rx.Observable

class WebsocketApiImpl constructor(
        private val okHttpClient: OkHttpClient
) : WebsocketApi {

    private val gson = Gson()

    var count = 0

    override fun observeProduct(productId: String): Observable<TradingQuote> {

        return Observable.create<TradingQuote>(
                { emmiter ->
                    //                    val listener =

                    okHttpClient.newWebSocket(
                            Request.Builder().url("${BuildConfig.WS_HOST}subscriptions/me").build(),
                            object : WebSocketListener() {
                                override fun onOpen(webSocket: WebSocket, response: Response) {
                                    //connect to ticker channel
                                    webSocket.send("""{"subscribeTo":["trading.product.$productId"]}""")
                                }

                                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                                    emmiter.onError(t)
                                }

                                override fun onMessage(webSocket: WebSocket?, message: String) {
                                    val text = if (count % 2 == 0) {
                                        "{\n" +
                                                " \"t\": \"trading.quote\",\n" +
                                                " \"body\": {\n" +
                                                " \"securityId\": \"sb26500\",\n" +
                                                " \"currentPrice\": \"10692.3\"\n" +
                                                " }\n" +
                                                "}"
                                    } else {
                                        message
                                    }
                                    count++


                                    if (text.contains("trading.quote")) {
                                        val typeToken = object : TypeToken<Event<TradingQuote>>(){}.type
                                        val event = gson.fromJson<Event<TradingQuote>>(text, typeToken)
                                        emmiter.onNext(event.body)
                                    }
                                    Log.i("DensTest", text)
//                                    Log.d("DensTest", "$ticker")
                                }

                            }
                    )

                },
                Emitter.BackpressureMode.DROP)
    }


}

private data class Event<out T>(
        val t: String,
        val body: T
)