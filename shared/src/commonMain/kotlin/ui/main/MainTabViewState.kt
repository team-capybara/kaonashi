package ui.main

import ui.main.home.HomeTab
import ui.main.home.HomeTabView
import ui.main.insight.InsightTab
import ui.main.insight.InsightTabView

data class MainTabViewState(
    val currentHomeTabView: HomeTabView = HomeTabView.ListView,
    val currentInsightTabView: InsightTabView = InsightTabView.Summary
) {

    fun copy(tabView: MainTabView): MainTabViewState {
        return when (tabView) {
            is HomeTabView -> copy(currentHomeTabView = tabView)
            is InsightTabView -> copy(currentInsightTabView = tabView)
            else -> this
        }
    }

    fun getCurrentTabViewWithTab(currentTab: MainTab): MainTabView {
        return when (currentTab) {
            HomeTab -> currentHomeTabView
            InsightTab -> currentInsightTabView
            else -> throw IllegalArgumentException("Unknown tab: $currentTab")
        }
    }
}
