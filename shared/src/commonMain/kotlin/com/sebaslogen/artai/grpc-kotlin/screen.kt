@file:OptIn(pbandk.PublicForGeneratedCode::class)

package screen.v1

@pbandk.Export
public sealed class Style(override val value: Int, override val name: String? = null) : pbandk.Message.Enum {
    override fun equals(other: kotlin.Any?): Boolean = other is Style && other.value == value
    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = "Style.${name ?: "UNRECOGNIZED"}(value=$value)"

    public object PRIMARY : Style(0, "PRIMARY")
    public object SECONDARY : Style(1, "SECONDARY")
    public class UNRECOGNIZED(value: Int) : Style(value)

    public companion object : pbandk.Message.Enum.Companion<Style> {
        public val values: List<Style> by lazy { listOf(PRIMARY, SECONDARY) }
        override fun fromValue(value: Int): Style = values.firstOrNull { it.value == value } ?: UNRECOGNIZED(value)
        override fun fromName(name: String): Style = values.firstOrNull { it.name == name } ?: throw IllegalArgumentException("No Style with name: $name")
    }
}

@pbandk.Export
public data class ErrorTesterRequest(
    val errorCode: Int = 0,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.ErrorTesterRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.ErrorTesterRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.ErrorTesterRequest> {
        public val defaultInstance: screen.v1.ErrorTesterRequest by lazy { screen.v1.ErrorTesterRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.ErrorTesterRequest = screen.v1.ErrorTesterRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.ErrorTesterRequest> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.ErrorTesterRequest, *>>(1)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "error_code",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int32(),
                        jsonName = "errorCode",
                        value = screen.v1.ErrorTesterRequest::errorCode
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.ErrorTesterRequest",
                messageClass = screen.v1.ErrorTesterRequest::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
public data class GetScreenRequest(
    val screenId: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.GetScreenRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.GetScreenRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.GetScreenRequest> {
        public val defaultInstance: screen.v1.GetScreenRequest by lazy { screen.v1.GetScreenRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.GetScreenRequest = screen.v1.GetScreenRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.GetScreenRequest> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.GetScreenRequest, *>>(1)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "screen_id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "screenId",
                        value = screen.v1.GetScreenRequest::screenId
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.GetScreenRequest",
                messageClass = screen.v1.GetScreenRequest::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
public data class GetScreenResponse(
    val screenTitle: String = "",
    val components: List<screen.v1.ComponentV1> = emptyList(),
    val screenDescription: String? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.GetScreenResponse = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.GetScreenResponse> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.GetScreenResponse> {
        public val defaultInstance: screen.v1.GetScreenResponse by lazy { screen.v1.GetScreenResponse() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.GetScreenResponse = screen.v1.GetScreenResponse.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.GetScreenResponse> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.GetScreenResponse, *>>(3)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "screen_title",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "screenTitle",
                        value = screen.v1.GetScreenResponse::screenTitle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "screen_description",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(hasPresence = true),
                        jsonName = "screenDescription",
                        value = screen.v1.GetScreenResponse::screenDescription
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "components",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Repeated<screen.v1.ComponentV1>(valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = screen.v1.ComponentV1.Companion)),
                        jsonName = "components",
                        value = screen.v1.GetScreenResponse::components
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.GetScreenResponse",
                messageClass = screen.v1.GetScreenResponse::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
public data class ComponentV1(
    val component: Component<*>? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    public sealed class Component<V>(value: V) : pbandk.Message.OneOf<V>(value) {
        public class Button(button: screen.v1.ButtonV1) : Component<screen.v1.ButtonV1>(button)
        public class Card(card: screen.v1.CardV1) : Component<screen.v1.CardV1>(card)
    }

    val button: screen.v1.ButtonV1?
        get() = (component as? Component.Button)?.value
    val card: screen.v1.CardV1?
        get() = (component as? Component.Card)?.value

    override operator fun plus(other: pbandk.Message?): screen.v1.ComponentV1 = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.ComponentV1> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.ComponentV1> {
        public val defaultInstance: screen.v1.ComponentV1 by lazy { screen.v1.ComponentV1() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.ComponentV1 = screen.v1.ComponentV1.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.ComponentV1> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.ComponentV1, *>>(2)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = screen.v1.ButtonV1.Companion),
                        oneofMember = true,
                        jsonName = "button",
                        value = screen.v1.ComponentV1::button
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = screen.v1.CardV1.Companion),
                        oneofMember = true,
                        jsonName = "card",
                        value = screen.v1.ComponentV1::card
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.ComponentV1",
                messageClass = screen.v1.ComponentV1::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
public data class ButtonV1(
    val buttonId: String = "",
    val buttonTitle: String = "",
    val buttonStyle: screen.v1.Style = screen.v1.Style.fromValue(0),
    val buttonMetadata: Map<String, screen.v1.Style> = emptyMap(),
    val buttonDescription: String? = null,
    val buttonAction: screen.v1.ButtonAction? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.ButtonV1 = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonV1> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.ButtonV1> {
        public val defaultInstance: screen.v1.ButtonV1 by lazy { screen.v1.ButtonV1() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.ButtonV1 = screen.v1.ButtonV1.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonV1> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.ButtonV1, *>>(6)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "buttonId",
                        value = screen.v1.ButtonV1::buttonId
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_title",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "buttonTitle",
                        value = screen.v1.ButtonV1::buttonTitle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_style",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Enum(enumCompanion = screen.v1.Style.Companion),
                        jsonName = "buttonStyle",
                        value = screen.v1.ButtonV1::buttonStyle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_description",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(hasPresence = true),
                        jsonName = "buttonDescription",
                        value = screen.v1.ButtonV1::buttonDescription
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_metadata",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Map<String, screen.v1.Style>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Enum(enumCompanion = screen.v1.Style.Companion)),
                        jsonName = "buttonMetadata",
                        value = screen.v1.ButtonV1::buttonMetadata
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "button_action",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = screen.v1.ButtonAction.Companion),
                        jsonName = "buttonAction",
                        value = screen.v1.ButtonV1::buttonAction
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.ButtonV1",
                messageClass = screen.v1.ButtonV1::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }

    public data class ButtonMetadataEntry(
        override val key: String = "",
        override val value: screen.v1.Style = screen.v1.Style.fromValue(0),
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, screen.v1.Style> {
        override operator fun plus(other: pbandk.Message?): screen.v1.ButtonV1.ButtonMetadataEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonV1.ButtonMetadataEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<screen.v1.ButtonV1.ButtonMetadataEntry> {
            public val defaultInstance: screen.v1.ButtonV1.ButtonMetadataEntry by lazy { screen.v1.ButtonV1.ButtonMetadataEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.ButtonV1.ButtonMetadataEntry = screen.v1.ButtonV1.ButtonMetadataEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonV1.ButtonMetadataEntry> by lazy {
                val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.ButtonV1.ButtonMetadataEntry, *>>(2)
                fieldsList.apply {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = screen.v1.ButtonV1.ButtonMetadataEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Enum(enumCompanion = screen.v1.Style.Companion),
                            jsonName = "value",
                            value = screen.v1.ButtonV1.ButtonMetadataEntry::value
                        )
                    )
                }
                pbandk.MessageDescriptor(
                    fullName = "screen.v1.ButtonV1.ButtonMetadataEntry",
                    messageClass = screen.v1.ButtonV1.ButtonMetadataEntry::class,
                    messageCompanion = this,
                    fields = fieldsList
                )
            }
        }
    }
}

@pbandk.Export
public data class ButtonAction(
    val actionId: String = "",
    val actionTitle: String = "",
    val actionDescription: String = "",
    val actionEnabled: Boolean? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.ButtonAction = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonAction> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.ButtonAction> {
        public val defaultInstance: screen.v1.ButtonAction by lazy { screen.v1.ButtonAction() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.ButtonAction = screen.v1.ButtonAction.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.ButtonAction> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.ButtonAction, *>>(4)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "action_id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "actionId",
                        value = screen.v1.ButtonAction::actionId
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "action_title",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "actionTitle",
                        value = screen.v1.ButtonAction::actionTitle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "action_description",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "actionDescription",
                        value = screen.v1.ButtonAction::actionDescription
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "action_enabled",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(hasPresence = true),
                        jsonName = "actionEnabled",
                        value = screen.v1.ButtonAction::actionEnabled
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.ButtonAction",
                messageClass = screen.v1.ButtonAction::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
public data class CardV1(
    val cardId: String = "",
    val cardTitle: String = "",
    val cardImage: String = "",
    val cardStyle: screen.v1.Style? = null,
    val cardDescription: String? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): screen.v1.CardV1 = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<screen.v1.CardV1> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<screen.v1.CardV1> {
        public val defaultInstance: screen.v1.CardV1 by lazy { screen.v1.CardV1() }
        override fun decodeWith(u: pbandk.MessageDecoder): screen.v1.CardV1 = screen.v1.CardV1.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<screen.v1.CardV1> by lazy {
            val fieldsList = ArrayList<pbandk.FieldDescriptor<screen.v1.CardV1, *>>(5)
            fieldsList.apply {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card_id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "cardId",
                        value = screen.v1.CardV1::cardId
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card_title",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "cardTitle",
                        value = screen.v1.CardV1::cardTitle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card_style",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Enum(enumCompanion = screen.v1.Style.Companion, hasPresence = true),
                        jsonName = "cardStyle",
                        value = screen.v1.CardV1::cardStyle
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card_description",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(hasPresence = true),
                        jsonName = "cardDescription",
                        value = screen.v1.CardV1::cardDescription
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "card_image",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "cardImage",
                        value = screen.v1.CardV1::cardImage
                    )
                )
            }
            pbandk.MessageDescriptor(
                fullName = "screen.v1.CardV1",
                messageClass = screen.v1.CardV1::class,
                messageCompanion = this,
                fields = fieldsList
            )
        }
    }
}

@pbandk.Export
@pbandk.JsName("orDefaultForErrorTesterRequest")
public fun ErrorTesterRequest?.orDefault(): screen.v1.ErrorTesterRequest = this ?: ErrorTesterRequest.defaultInstance

private fun ErrorTesterRequest.protoMergeImpl(plus: pbandk.Message?): ErrorTesterRequest = (plus as? ErrorTesterRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ErrorTesterRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ErrorTesterRequest {
    var errorCode = 0

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> errorCode = _fieldValue as Int
        }
    }

    return ErrorTesterRequest(errorCode, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForGetScreenRequest")
public fun GetScreenRequest?.orDefault(): screen.v1.GetScreenRequest = this ?: GetScreenRequest.defaultInstance

private fun GetScreenRequest.protoMergeImpl(plus: pbandk.Message?): GetScreenRequest = (plus as? GetScreenRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun GetScreenRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): GetScreenRequest {
    var screenId = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> screenId = _fieldValue as String
        }
    }

    return GetScreenRequest(screenId, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForGetScreenResponse")
public fun GetScreenResponse?.orDefault(): screen.v1.GetScreenResponse = this ?: GetScreenResponse.defaultInstance

private fun GetScreenResponse.protoMergeImpl(plus: pbandk.Message?): GetScreenResponse = (plus as? GetScreenResponse)?.let {
    it.copy(
        components = components + plus.components,
        screenDescription = plus.screenDescription ?: screenDescription,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun GetScreenResponse.Companion.decodeWithImpl(u: pbandk.MessageDecoder): GetScreenResponse {
    var screenTitle = ""
    var components: pbandk.ListWithSize.Builder<screen.v1.ComponentV1>? = null
    var screenDescription: String? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> screenTitle = _fieldValue as String
            2 -> screenDescription = _fieldValue as String
            3 -> components = (components ?: pbandk.ListWithSize.Builder()).apply { this += _fieldValue as kotlin.sequences.Sequence<screen.v1.ComponentV1> }
        }
    }

    return GetScreenResponse(screenTitle, pbandk.ListWithSize.Builder.fixed(components), screenDescription, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForComponentV1")
public fun ComponentV1?.orDefault(): screen.v1.ComponentV1 = this ?: ComponentV1.defaultInstance

private fun ComponentV1.protoMergeImpl(plus: pbandk.Message?): ComponentV1 = (plus as? ComponentV1)?.let {
    it.copy(
        component = when {
            component is ComponentV1.Component.Button && plus.component is ComponentV1.Component.Button ->
                ComponentV1.Component.Button(component.value + plus.component.value)
            component is ComponentV1.Component.Card && plus.component is ComponentV1.Component.Card ->
                ComponentV1.Component.Card(component.value + plus.component.value)
            else ->
                plus.component ?: component
        },
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ComponentV1.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ComponentV1 {
    var component: ComponentV1.Component<*>? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> component = ComponentV1.Component.Button(_fieldValue as screen.v1.ButtonV1)
            2 -> component = ComponentV1.Component.Card(_fieldValue as screen.v1.CardV1)
        }
    }

    return ComponentV1(component, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForButtonV1")
public fun ButtonV1?.orDefault(): screen.v1.ButtonV1 = this ?: ButtonV1.defaultInstance

private fun ButtonV1.protoMergeImpl(plus: pbandk.Message?): ButtonV1 = (plus as? ButtonV1)?.let {
    it.copy(
        buttonMetadata = buttonMetadata + plus.buttonMetadata,
        buttonDescription = plus.buttonDescription ?: buttonDescription,
        buttonAction = buttonAction?.plus(plus.buttonAction) ?: plus.buttonAction,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ButtonV1.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ButtonV1 {
    var buttonId = ""
    var buttonTitle = ""
    var buttonStyle: screen.v1.Style = screen.v1.Style.fromValue(0)
    var buttonMetadata: pbandk.MessageMap.Builder<String, screen.v1.Style>? = null
    var buttonDescription: String? = null
    var buttonAction: screen.v1.ButtonAction? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> buttonId = _fieldValue as String
            2 -> buttonTitle = _fieldValue as String
            3 -> buttonStyle = _fieldValue as screen.v1.Style
            4 -> buttonDescription = _fieldValue as String
            5 -> buttonMetadata = (buttonMetadata ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, screen.v1.Style>> }
            6 -> buttonAction = _fieldValue as screen.v1.ButtonAction
        }
    }

    return ButtonV1(buttonId, buttonTitle, buttonStyle, pbandk.MessageMap.Builder.fixed(buttonMetadata),
        buttonDescription, buttonAction, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForButtonV1ButtonMetadataEntry")
public fun ButtonV1.ButtonMetadataEntry?.orDefault(): screen.v1.ButtonV1.ButtonMetadataEntry = this ?: ButtonV1.ButtonMetadataEntry.defaultInstance

private fun ButtonV1.ButtonMetadataEntry.protoMergeImpl(plus: pbandk.Message?): ButtonV1.ButtonMetadataEntry = (plus as? ButtonV1.ButtonMetadataEntry)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ButtonV1.ButtonMetadataEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ButtonV1.ButtonMetadataEntry {
    var key = ""
    var value: screen.v1.Style = screen.v1.Style.fromValue(0)

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as screen.v1.Style
        }
    }

    return ButtonV1.ButtonMetadataEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForButtonAction")
public fun ButtonAction?.orDefault(): screen.v1.ButtonAction = this ?: ButtonAction.defaultInstance

private fun ButtonAction.protoMergeImpl(plus: pbandk.Message?): ButtonAction = (plus as? ButtonAction)?.let {
    it.copy(
        actionEnabled = plus.actionEnabled ?: actionEnabled,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ButtonAction.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ButtonAction {
    var actionId = ""
    var actionTitle = ""
    var actionDescription = ""
    var actionEnabled: Boolean? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> actionId = _fieldValue as String
            2 -> actionTitle = _fieldValue as String
            3 -> actionDescription = _fieldValue as String
            4 -> actionEnabled = _fieldValue as Boolean
        }
    }

    return ButtonAction(actionId, actionTitle, actionDescription, actionEnabled, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForCardV1")
public fun CardV1?.orDefault(): screen.v1.CardV1 = this ?: CardV1.defaultInstance

private fun CardV1.protoMergeImpl(plus: pbandk.Message?): CardV1 = (plus as? CardV1)?.let {
    it.copy(
        cardStyle = plus.cardStyle ?: cardStyle,
        cardDescription = plus.cardDescription ?: cardDescription,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun CardV1.Companion.decodeWithImpl(u: pbandk.MessageDecoder): CardV1 {
    var cardId = ""
    var cardTitle = ""
    var cardImage = ""
    var cardStyle: screen.v1.Style? = null
    var cardDescription: String? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> cardId = _fieldValue as String
            2 -> cardTitle = _fieldValue as String
            3 -> cardStyle = _fieldValue as screen.v1.Style
            4 -> cardDescription = _fieldValue as String
            5 -> cardImage = _fieldValue as String
        }
    }

    return CardV1(cardId, cardTitle, cardImage, cardStyle,
        cardDescription, unknownFields)
}
