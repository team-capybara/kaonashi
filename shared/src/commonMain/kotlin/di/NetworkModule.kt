package di

import com.russhwolf.settings.Settings
import data.Api
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal val networkModule = module {
    single {
        HttpClient {
            defaultRequest {
                url.takeFrom(URLBuilder().takeFrom(Api.BASE_URL))
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val settings: Settings = get()
                        val accessToken = settings.getStringOrNull("accessToken")
                        accessToken?.let { BearerTokens(it, "") }
                    }
                    refreshTokens {
                        val settings: Settings = get()
                        val accessToken = settings.getStringOrNull("accessToken")
                        accessToken?.let { BearerTokens(it, "") }
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
        }
    }
}
