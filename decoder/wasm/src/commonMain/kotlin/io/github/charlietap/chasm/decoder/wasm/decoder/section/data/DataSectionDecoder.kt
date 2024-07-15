package io.github.charlietap.chasm.decoder.wasm.decoder.section.data

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.module.DataSegment
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.decoder.wasm.Decoder
import io.github.charlietap.chasm.decoder.wasm.context.DecoderContext
import io.github.charlietap.chasm.decoder.wasm.decoder.vector.VectorDecoder
import io.github.charlietap.chasm.decoder.wasm.error.WasmDecodeError
import io.github.charlietap.chasm.decoder.wasm.section.DataSection

internal fun DataSectionDecoder(
    context: DecoderContext,
) = DataSectionDecoder(
    context = context,
    segmentDecoder = ::DataSegmentDecoder,
    vectorDecoder = ::VectorDecoder,
)

internal fun DataSectionDecoder(
    context: DecoderContext,
    segmentDecoder: Decoder<DataSegment>,
    vectorDecoder: VectorDecoder<DataSegment>,
): Result<DataSection, WasmDecodeError> = binding {

    val segments = vectorDecoder(context, segmentDecoder).bind().vector.mapIndexed { idx, segment ->
        segment.copy(idx = Index.DataIndex(idx.toUInt()))
    }

    DataSection(segments)
}
