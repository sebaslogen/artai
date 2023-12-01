// Code generated by Wire protocol buffer compiler, do not edit.
// Source: screen.v1.ErrorTesterRequest in screen.proto
package screen.v1

import com.squareup.wire.FieldEncoding
import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.ReverseProtoWriter
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.WireField
import com.squareup.wire.`internal`.JvmField
import kotlin.Any
import kotlin.AssertionError
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.DeprecationLevel
import kotlin.Int
import kotlin.Long
import kotlin.Nothing
import kotlin.String
import okio.ByteString

public class ErrorTesterRequest(
  /**
   * Error code must always be present
   */
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "errorCode",
    schemaIndex = 0,
  )
  public val error_code: Int = 0,
  unknownFields: ByteString = ByteString.EMPTY,
) : Message<ErrorTesterRequest, Nothing>(ADAPTER, unknownFields) {
  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN,
  )
  override fun newBuilder(): Nothing = throw
      AssertionError("Builders are deprecated and only available in a javaInterop build; see https://square.github.io/wire/wire_compiler/#kotlin")

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is ErrorTesterRequest) return false
    if (unknownFields != other.unknownFields) return false
    if (error_code != other.error_code) return false
    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + error_code.hashCode()
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    result += """error_code=$error_code"""
    return result.joinToString(prefix = "ErrorTesterRequest{", separator = ", ", postfix = "}")
  }

  public fun copy(error_code: Int = this.error_code, unknownFields: ByteString =
      this.unknownFields): ErrorTesterRequest = ErrorTesterRequest(error_code, unknownFields)

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<ErrorTesterRequest> = object :
        ProtoAdapter<ErrorTesterRequest>(
      FieldEncoding.LENGTH_DELIMITED, 
      ErrorTesterRequest::class, 
      "type.googleapis.com/screen.v1.ErrorTesterRequest", 
      PROTO_3, 
      null, 
      "screen.proto"
    ) {
      override fun encodedSize(`value`: ErrorTesterRequest): Int {
        var size = value.unknownFields.size
        if (value.error_code != 0) size += ProtoAdapter.INT32.encodedSizeWithTag(1,
            value.error_code)
        return size
      }

      override fun encode(writer: ProtoWriter, `value`: ErrorTesterRequest) {
        if (value.error_code != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.error_code)
        writer.writeBytes(value.unknownFields)
      }

      override fun encode(writer: ReverseProtoWriter, `value`: ErrorTesterRequest) {
        writer.writeBytes(value.unknownFields)
        if (value.error_code != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.error_code)
      }

      override fun decode(reader: ProtoReader): ErrorTesterRequest {
        var error_code: Int = 0
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> error_code = ProtoAdapter.INT32.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return ErrorTesterRequest(
          error_code = error_code,
          unknownFields = unknownFields
        )
      }

      override fun redact(`value`: ErrorTesterRequest): ErrorTesterRequest = value.copy(
        unknownFields = ByteString.EMPTY
      )
    }

    private const val serialVersionUID: Long = 0L
  }
}
