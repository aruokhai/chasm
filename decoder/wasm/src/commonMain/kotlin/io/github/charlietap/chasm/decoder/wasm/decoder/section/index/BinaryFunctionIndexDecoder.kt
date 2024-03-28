package io.github.charlietap.chasm.decoder.wasm.decoder.section.index

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.reader.WasmBinaryReader

internal fun BinaryFunctionIndexDecoder(
    reader: WasmBinaryReader,
): Result<Index.FunctionIndex, WasmDecodeError> = BinaryIndexDecoder(reader, Index::FunctionIndex)
