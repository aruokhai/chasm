package io.github.charlietap.chasm.decoder.wasm.decoder.type.aggregate

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.type.PackedType
import io.github.charlietap.chasm.ast.type.StorageType
import io.github.charlietap.chasm.ast.type.ValueType
import io.github.charlietap.chasm.decoder.wasm.Decoder
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.NUMBER_TYPE_RANGE
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.REFERENCE_TYPE_RANGE
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VECTOR_TYPE_RANGE
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.ValueTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.error.TypeDecodeError
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError

internal fun StorageTypeDecoder(
    context: DecoderContext,
): Result<StorageType, WasmDecodeError> =
    StorageTypeDecoder(
        context = context,
        packedTypeDecoder = ::PackedTypeDecoder,
        valueTypeDecoder = ::ValueTypeDecoder,
    )

internal fun StorageTypeDecoder(
    context: DecoderContext,
    packedTypeDecoder: Decoder<PackedType>,
    valueTypeDecoder: Decoder<ValueType>,
): Result<StorageType, WasmDecodeError> = binding {
    when (val encoded = context.reader.peek().ubyte().bind()) {
        in NUMBER_TYPE_RANGE,
        in VECTOR_TYPE_RANGE,
        in REFERENCE_TYPE_RANGE,
        -> {
            val valueType = valueTypeDecoder(context).bind()
            StorageType.Value(valueType)
        }
        in PACKED_TYPE_RANGE -> {
            val packedType = packedTypeDecoder(context).bind()
            StorageType.Packed(packedType)
        }
        else -> Err(TypeDecodeError.InvalidStorageType(encoded)).bind<StorageType>()
    }
}
