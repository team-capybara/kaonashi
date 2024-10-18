package ui.repository

import ui.model.InsightSummary

interface InsightRepository {
    suspend fun getInsightSummary(): Result<InsightSummary>
}
