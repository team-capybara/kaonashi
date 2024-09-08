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

    fun copy(tabView: TabView): MainTabViewState {
        return when (tabView) {
            is HomeTabView -> copy(currentHomeTabView = tabView)
            is InsightTabView -> copy(currentInsightTabView = tabView)
            else -> this
        }
    }

    fun getCurrentTabViewWithTab(currentTab: Tab): TabView {
        return when (currentTab as MainTab) {
            HomeTab -> currentHomeTabView
            InsightTab -> currentInsightTabView
            else -> throw IllegalArgumentException("Unknown tab: $currentTab")
        }
    }
}
