package data.repository

import data.Api
import data.model.InsightSummaryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ui.model.InsightSummary
import ui.repository.InsightRepository

class InsightRepositoryImpl(
    private val httpClient: HttpClient
) : InsightRepository {

    override suspend fun getInsightSummary(): Result<InsightSummary> = runCatching {
        httpClient.get(Api.WEEKLY_SUMMARY).body<InsightSummaryResponse>().toUiModel()
    }
}
