package ui.main.insight

import cafe.adriel.voyager.core.model.StateScreenModel
import ui.model.InsightSummary

class InsightScreenModel : StateScreenModel<InsightScreenModel.State>(State.Loading) {

    sealed interface State {
        data object Loading : State
        data class Success(val summary: InsightSummary) : State
        data class Failure(val throwable: Throwable?) : State
    }
}
