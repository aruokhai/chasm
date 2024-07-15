package io.github.charlietap.chasm.decoder.wasm.decoder.section.element

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.error.SectionDecodeError
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError

internal fun ElementKindDecoder(
    context: DecoderContext,
): Result<ElementKind, WasmDecodeError> = binding {
    when (val byte = context.reader.byte().bind()) {
        ElementKind.FuncRef.byte -> ElementKind.FuncRef
        else -> Err(SectionDecodeError.UnknownElementKind(byte)).bind<ElementKind>()
    }
}
