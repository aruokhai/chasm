package io.github.charlietap.chasm.decoder.wasm.decoder.type.composite

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.type.ArrayType
import io.github.charlietap.chasm.ast.type.CompositeType
import io.github.charlietap.chasm.ast.type.FunctionType
import io.github.charlietap.chasm.ast.type.StructType
import io.github.charlietap.chasm.decoder.wasm.Decoder
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.decoder.type.aggregate.ArrayTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.type.aggregate.StructTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.type.function.FunctionTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.error.TypeDecodeError
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError

internal fun CompositeTypeDecoder(
    context: DecoderContext,
): Result<CompositeType, WasmDecodeError> =
    CompositeTypeDecoder(
        context = context,
        functionTypeDecoder = ::FunctionTypeDecoder,
        structTypeDecoder = ::StructTypeDecoder,
        arrayTypeDecoder = ::ArrayTypeDecoder,
    )

internal fun CompositeTypeDecoder(
    context: DecoderContext,
    functionTypeDecoder: Decoder<FunctionType>,
    structTypeDecoder: Decoder<StructType>,
    arrayTypeDecoder: Decoder<ArrayType>,
): Result<CompositeType, WasmDecodeError> = binding {
    when (val compositeTypeByte = context.reader.ubyte().bind()) {
        ARRAY_COMPOSITE_TYPE -> CompositeType.Array(arrayTypeDecoder(context).bind())
        STRUCT_COMPOSITE_TYPE -> CompositeType.Struct(structTypeDecoder(context).bind())
        FUNCTION_COMPOSITE_TYPE -> CompositeType.Function(functionTypeDecoder(context).bind())
        else -> Err(TypeDecodeError.InvalidCompositeType(compositeTypeByte)).bind<CompositeType>()
    }
}

internal const val ARRAY_COMPOSITE_TYPE: UByte = 0x5Eu
internal const val STRUCT_COMPOSITE_TYPE: UByte = 0x5Fu
internal const val FUNCTION_COMPOSITE_TYPE: UByte = 0x60u
