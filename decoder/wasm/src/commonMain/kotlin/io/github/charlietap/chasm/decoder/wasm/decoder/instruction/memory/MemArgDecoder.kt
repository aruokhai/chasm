package io.github.charlietap.chasm.decoder.wasm.decoder.instruction.memory

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.MemArg
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError

internal fun MemArgDecoder(
    context: DecoderContext,
): Result<MemArg, WasmDecodeError> = binding {
    MemArg(context.reader.uint().bind(), context.reader.uint().bind())
}
