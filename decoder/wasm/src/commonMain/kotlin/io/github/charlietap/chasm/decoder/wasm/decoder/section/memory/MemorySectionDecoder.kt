package io.github.charlietap.chasm.decoder.wasm.decoder.section.memory

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.ast.module.Memory
import io.github.charlietap.chasm.ast.type.MemoryType
import io.github.charlietap.chasm.decoder.wasm.Decoder
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.decoder.type.memory.MemoryTypeDecoder
import io.github.charlietap.chasm.decoder.wasm.decoder.vector.VectorDecoder
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.section.MemorySection

internal fun MemorySectionDecoder(
    context: DecoderContext,
): Result<MemorySection, WasmDecodeError> =
    MemorySectionDecoder(
        context = context,
        vectorDecoder = ::VectorDecoder,
        memoryTypeDecoder = ::MemoryTypeDecoder,
    )

internal fun MemorySectionDecoder(
    context: DecoderContext,
    vectorDecoder: VectorDecoder<MemoryType>,
    memoryTypeDecoder: Decoder<MemoryType>,
): Result<MemorySection, WasmDecodeError> = binding {

    val memoriesTypes = vectorDecoder(context, memoryTypeDecoder).bind()

    val memories = memoriesTypes.vector.mapIndexed { idx, memoryType ->
        val index = Index.MemoryIndex(idx.toUInt())
        Memory(index, memoryType)
    }

    MemorySection(memories)
}
