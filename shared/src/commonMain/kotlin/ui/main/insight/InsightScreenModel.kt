package ui.main.insight

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ui.model.InsightSummary
import ui.repository.InsightRepository

class InsightScreenModel(
    private val insightRepository: InsightRepository
) : StateScreenModel<InsightScreenModel.State>(State.Loading) {

    sealed interface State {
        data object Loading : State
        data class Success(val summary: InsightSummary) : State
        data class Failure(val throwable: Throwable?) : State
    }

    init {
        getInsightSummary()
    }

    private fun getInsightSummary() {
        screenModelScope.launch {
            mutableState.value = State.Loading
            insightRepository.getInsightSummary()
                .onSuccess { mutableState.value = State.Success(it) }
                .onFailure { mutableState.value = State.Failure(it) }
        }
    }
}
