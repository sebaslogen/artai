package com.sebaslogen.artai

import screen.v1.ButtonAction
import screen.v1.ButtonV1
import screen.v1.CardV1
import screen.v1.ComponentV1
import screen.v1.GetScreenResponse
import screen.v1.style


fun GetScreenResponse.toScreen(): McDScreen = McDScreen(
    screenTitle = this.screen_title,
    screenDescription = this.screen_description,
    components = this.components.map { it.toMcDScreenComponent() }
)

private fun ComponentV1.toMcDScreenComponent(): McDScreenComponent = when { // TODO: How to make it exhaustive?
    this.button != null -> this.button.toMcDButtonComponent()
    this.card != null -> this.card.toMcDCardComponent()
    else -> throw IllegalArgumentException("Unknown component type: $this")
}

private fun CardV1.toMcDCardComponent(): McDScreenComponent.McDCardComponent = McDScreenComponent.McDCardComponent(
    cardId = this.card_id,
    cardTitle = this.card_title,
    cardStyle = this.card_style.toMcDStyle(),
    cardDescription = this.card_description,
    cardImage = this.card_image,
)

private fun style?.toMcDStyle(): McDStyle? = this?.let {
    when(this) {
        style.PRIMARY -> McDStyle.PRIMARY
        style.SECONDARY -> McDStyle.SECONDARY
    }
}

private fun ButtonV1.toMcDButtonComponent(): McDScreenComponent.McDButtonComponent = McDScreenComponent.McDButtonComponent(
    buttonId = this.button_id,
    buttonTitle = this.button_title,
    buttonStyle = this.button_style.toMcDStyle(),
    buttonDescription = this.button_description,
    buttonAction = this.button_action.toMcDButtonAction(),
    buttonMetadata = this.button_metadata,
)

private fun ButtonAction?.toMcDButtonAction(): McDButtonAction? = this?.let {
    McDButtonAction(
        actionId = this.action_id,
        actionTitle = this.action_title,
        actionDescription = this.action_description,
        actionEnabled = this.action_enabled,
    )
}

