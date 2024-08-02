package ui.main.insight

import cafe.adriel.voyager.core.model.StateScreenModel

class InsightScreenModel : StateScreenModel<InsightScreenModel.State>(State.Result) {

    sealed interface State {
        data object Loading : State
        data object Result : State
    }
}
