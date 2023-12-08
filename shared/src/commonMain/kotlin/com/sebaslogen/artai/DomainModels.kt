package com.sebaslogen.artai

import screen.v1.Style

data class McDScreen(val screenTitle: String, val screenDescription: String?, val components: List<McDScreenComponent>)

sealed class McDScreenComponent {
    data class McDCardComponent(
        val cardId: String,
        val cardTitle: String,
        val cardStyle: McDStyle?,
        val cardDescription: String?,
        val cardImage: String,
    ) : McDScreenComponent()

    data class McDButtonComponent(
        val buttonId: String,
        val buttonTitle: String,
        val buttonStyle: McDStyle?,
        val buttonDescription: String?,
        val buttonAction: McDButtonAction?,
        val buttonMetadata: Map<String, Style>,
    ) : McDScreenComponent()
}

enum class McDStyle {
    PRIMARY, SECONDARY
}

data class McDButtonAction(
    val actionId: String,
    val actionTitle: String,
    val actionDescription: String,
    val actionEnabled: Boolean?,
)