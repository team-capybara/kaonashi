package ui.main

import cafe.adriel.voyager.navigator.tab.Tab
import ui.main.home.HomeTab
import ui.main.home.HomeTabView
import ui.main.insight.InsightTab
import ui.main.insight.InsightTabView

data class MainTabViewState(
    val currentHomeTabView: HomeTabView = HomeTabView.ListView,
    val currentInsightTabView: InsightTabView = InsightTabView.Summary
) {

    fun copy(mainTabView: MainTabView): MainTabViewState {
        return when (mainTabView) {
            is HomeTabView -> copy(currentHomeTabView = mainTabView)
            is InsightTabView -> copy(currentInsightTabView = mainTabView)
            else -> this
        }
    }

    fun getCurrentTabViewWithTab(currentTab: Tab): MainTabView {
        return when (currentTab as MainTab) {
            HomeTab -> currentHomeTabView
            InsightTab -> currentInsightTabView
            else -> throw IllegalArgumentException("Unknown tab: $currentTab")
        }
    }
}
