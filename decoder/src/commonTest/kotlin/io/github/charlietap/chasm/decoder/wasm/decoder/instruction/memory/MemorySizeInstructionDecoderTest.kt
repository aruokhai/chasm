package io.github.charlietap.chasm.decoder.wasm.decoder.instruction.memory

import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.ast.module.Index
import io.github.charlietap.chasm.decoder.decoder.Decoder
import io.github.charlietap.chasm.decoder.decoder.instruction.memory.MemorySizeInstructionDecoder
import io.github.charlietap.chasm.decoder.wasm.fixture.decoderContext
import io.github.charlietap.chasm.fixture.instruction.memorySizeInstruction
import io.github.charlietap.chasm.fixture.module.memoryIndex
import kotlin.test.Test
import kotlin.test.assertEquals

class MemorySizeInstructionDecoderTest {

    @Test
    fun `can decode a MemorySize instruction`() {

        val context = decoderContext()

        val expectedMemoryIndex = memoryIndex(117u)
        val memoryIndexDecoder: Decoder<Index.MemoryIndex> = {
            Ok(expectedMemoryIndex)
        }
        val expected = memorySizeInstruction(expectedMemoryIndex)

        val actual = MemorySizeInstructionDecoder(
            context = context,
            memoryIndexDecoder = memoryIndexDecoder,
        )

        assertEquals(Ok(expected), actual)
    }
}
