package io.github.charlietap.chasm.decoder.wasm.decoder.instruction.control

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.ControlInstruction.BlockType
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.BinaryValueTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_NUMBER_I32
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_REFERENCE_EXTERNREF
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_REFERENCE_FUNCREF
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_REFERENCE_REF
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_REFERENCE_REF_NULL
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.VALUE_TYPE_VECTOR_V128
import io.github.charlietap.chasm.decoder.wasm.decoder.type.value.ValueTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.reader.WasmBinaryReader

internal fun BinaryBlockTypeDecoder(
    reader: WasmBinaryReader,
): Result<BlockType, WasmDecodeError> = BinaryBlockTypeDecoder(
    reader,
    ::BinaryValueTypeDecoder,
)

internal fun BinaryBlockTypeDecoder(
    reader: WasmBinaryReader,
    valueTypeDecoder: ValueTypeDecoder,
): Result<BlockType, WasmDecodeError> = binding {

    val firstByte = reader.peek().ubyte().bind()

    when (firstByte) {
        BLOCK_TYPE_EMPTY -> {
            reader.byte() // consume it
            BlockType.Empty
        }
        in VALUE_TYPE_REFERENCE_EXTERNREF..VALUE_TYPE_REFERENCE_FUNCREF,
        in VALUE_TYPE_REFERENCE_REF_NULL..VALUE_TYPE_REFERENCE_REF,
        in VALUE_TYPE_VECTOR_V128..VALUE_TYPE_NUMBER_I32,
        -> {
            BlockType.ValType(valueTypeDecoder(reader).bind())
        }

        else -> {
            // Not using TypeIndexDecoder here as it's stored in a signed leb s33
            val idx = reader.long().bind()
            BlockType.SignedTypeIndex(Index.TypeIndex(idx.toUInt()))
        }
    }
}

internal const val BLOCK_TYPE_EMPTY: UByte = 0x40u
