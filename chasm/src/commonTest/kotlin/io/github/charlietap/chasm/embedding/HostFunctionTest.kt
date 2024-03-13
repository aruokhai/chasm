package io.github.charlietap.chasm.embedding

import io.github.charlietap.chasm.ChasmResult
import io.github.charlietap.chasm.ast.type.FunctionType
import io.github.charlietap.chasm.ast.type.Limits
import io.github.charlietap.chasm.ast.type.MemoryType
import io.github.charlietap.chasm.ast.type.NumberType
import io.github.charlietap.chasm.ast.type.ResultType
import io.github.charlietap.chasm.ast.type.ValueType
import io.github.charlietap.chasm.executor.runtime.instance.HostFunction
import io.github.charlietap.chasm.executor.runtime.value.ExecutionValue
import io.github.charlietap.chasm.fixture.store
import io.github.charlietap.chasm.import.Import
import io.github.charlietap.chasm.integration.testRunner
import kotlin.test.Test
import kotlin.test.assertEquals

class HostFunctionTest {

    @Test
    fun `can run a wasm file with a host_function and return a correct result`() {

        val store = store()

        val memoryType = MemoryType(Limits(1u))
        val memoryExternal = memory(store, memoryType)

        val memoryImport = Import(
            "env",
            "memory",
            memoryExternal,
        )

        val functionType = FunctionType(
            ResultType(listOf(ValueType.Number(NumberType.I32))),
            ResultType(emptyList()),
        )

        val hostFunction: HostFunction = { _ ->
            println("hello")
            emptyList()
        }

        val external = function(store, functionType, hostFunction)
        val printlnImport = Import(
            "env",
            "println",
            external,
        )

        val result = testRunner(
            fileName = "host_function.wasm",
            fileDirectory = FILE_DIR,
            store = store,
            imports = listOf(memoryImport, printlnImport),
        )

        val expected = emptyList<ExecutionValue>()

        assertEquals(ChasmResult.Success(expected), result)
    }

    companion object {
        private const val FILE_DIR = "src/commonTest/resources/embedding/"
    }
}
