package com.sebaslogen.artai

import screen.v1.ButtonAction
import screen.v1.ButtonV1
import screen.v1.CardV1
import screen.v1.ComponentV1
import screen.v1.GetScreenResponse
import screen.v1.Style


fun GetScreenResponse.toScreen(): McDScreen = McDScreen(
    screenTitle = this.screenTitle,
    screenDescription = this.screenDescription,
    components = this.components.map { it.toMcDScreenComponent() }
)

private fun ComponentV1.toMcDScreenComponent(): McDScreenComponent = when(this.component) {
    is ComponentV1.Component.Button -> component.value.toMcDButtonComponent()
    is ComponentV1.Component.Card -> component.value.toMcDCardComponent()
    null -> throw IllegalArgumentException("Unknown component type: $this") // TODO: How to do this right????
}

private fun CardV1.toMcDCardComponent(): McDScreenComponent.McDCardComponent = McDScreenComponent.McDCardComponent(
    cardId = this.cardId,
    cardTitle = this.cardTitle,
    cardStyle = this.cardStyle.toMcDStyle(),
    cardDescription = this.cardDescription,
    cardImage = this.cardImage,
)

private fun Style?.toMcDStyle(): McDStyle? = this?.let {
    when(this) {
        Style.PRIMARY -> McDStyle.PRIMARY
        Style.SECONDARY -> McDStyle.SECONDARY
        is Style.UNRECOGNIZED -> TODO("log unrecognized enum type")
    }
}

private fun ButtonV1.toMcDButtonComponent(): McDScreenComponent.McDButtonComponent = McDScreenComponent.McDButtonComponent(
    buttonId = this.buttonId,
    buttonTitle = this.buttonTitle,
    buttonStyle = this.buttonStyle.toMcDStyle(),
    buttonDescription = this.buttonDescription,
    buttonAction = this.buttonAction.toMcDButtonAction(),
    buttonMetadata = this.buttonMetadata,
)

private fun ButtonAction?.toMcDButtonAction(): McDButtonAction? = this?.let {
    McDButtonAction(
        actionId = this.actionId,
        actionTitle = this.actionTitle,
        actionDescription = this.actionDescription,
        actionEnabled = this.actionEnabled,
    )
}

