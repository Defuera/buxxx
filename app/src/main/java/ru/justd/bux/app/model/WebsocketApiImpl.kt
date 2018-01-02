package ru.justd.bux.app.model

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import ru.justd.bux.BuildConfig
import rx.Observable
import rx.subjects.PublishSubject

private const val TAG = "WebsocketApiImpl"

class WebsocketApiImpl constructor(
        private val okHttpClient: OkHttpClient
) : WebsocketApi {

    private val gson = Gson()
    private val subject = PublishSubject.create<TradingQuote>()
    private lateinit var webSocket: WebSocket
    
    override fun connect() {
        webSocket = okHttpClient.newWebSocket(
                Request.Builder().url("${BuildConfig.WS_HOST}subscriptions/me").build(),
                object : WebSocketListener() {
                    override fun onOpen(webSocket: WebSocket, response: Response) {
                        Log.d(TAG, ": ");
                    }

                    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                        subject.onError(t)
                        webSocket.cancel()
                        connect()
                    }

                    override fun onMessage(webSocket: WebSocket?, text: String) {
                        if (text.contains("trading.quote")) {
                            val typeToken = object : TypeToken<Event<TradingQuote>>() {}.type
                            val event = gson.fromJson<Event<TradingQuote>>(text, typeToken)
                            val quote = event.body

                            Log.i(TAG, "new quote: $quote")
                            subject.onNext(quote)
                        } else {
                            Log.d(TAG, "onMessage: $text")
                        }
                    }

                }
        )
    }

    override fun observeProduct(productId: String): Observable<TradingQuote> =
            subject
                .doOnSubscribe {
                    Log.i(TAG, "subscribe to product: $productId")
                    webSocket.send("""{"subscribeTo":["trading.product.$productId"]}""")
                }

    override fun unsubscribe(productId: String) {
        Log.i(TAG, "unsubscribe from product: $productId");
        webSocket.send("""{"unsubscribeFrom":["trading.product.$productId"]}""")
    }

}

private data class Event<out T>(
        val t: String,
        val body: T
)