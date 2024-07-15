package io.github.charlietap.chasm.decoder.wasm.decoder.section

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.flatMap
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.ext.sectionType
import io.github.charlietap.chasm.decoder.wasm.section.SectionType

internal fun SectionTypeDecoder(
    context: DecoderContext,
): Result<SectionType, WasmDecodeError> = context.reader.ubyte().flatMap(UByte::sectionType)
