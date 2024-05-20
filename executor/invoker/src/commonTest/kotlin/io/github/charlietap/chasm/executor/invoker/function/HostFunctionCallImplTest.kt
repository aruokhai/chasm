package io.github.charlietap.chasm.executor.invoker.function

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.instance.HostFunction
import io.github.charlietap.chasm.executor.type.ext.definedType
import io.github.charlietap.chasm.fixture.stack
import io.github.charlietap.chasm.fixture.store
import io.github.charlietap.chasm.fixture.type.functionType
import io.github.charlietap.chasm.fixture.type.i32ValueType
import io.github.charlietap.chasm.fixture.type.i64ValueType
import io.github.charlietap.chasm.fixture.type.resultType
import io.github.charlietap.chasm.fixture.value.i32
import io.github.charlietap.chasm.fixture.value.i64
import kotlin.test.Test
import kotlin.test.assertEquals

class HostFunctionCallImplTest {

    @Test
    fun `can execute a host function call and return a result`() {

        val store = store()
        val stack = stack()

        val functionType = functionType(
            params = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
            results = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
        )
        val definedType = functionType.definedType()

        val hostFunction: HostFunction = {
            listOf(
                i32(117),
                i64(118),
            )
        }

        val functionInstance = FunctionInstance.HostFunction(
            type = definedType,
            function = hostFunction,
        )

        val params = listOf(
            i32(115),
            i64(116),
        )

        params.forEach { value ->
            stack.push(Stack.Entry.Value(value))
        }

        val actual = HostFunctionCallImpl(
            store = store,
            stack = stack,
            function = functionInstance,
        )

        assertEquals(Ok(Unit), actual)
        assertEquals(0, stack.framesDepth())
        assertEquals(2, stack.valuesDepth())
        assertEquals(i64(118), stack.popValueOrNull()?.value)
        assertEquals(i32(117), stack.popValueOrNull()?.value)
    }

    @Test
    fun `returns an error if the host function does not return values matching its type`() {

        val store = store()
        val stack = stack()

        val functionType = functionType(
            params = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
            results = resultType(
                listOf(
                    i32ValueType(),
                    i64ValueType(),
                ),
            ),
        )
        val definedType = functionType.definedType()

        val hostFunction: HostFunction = {
            listOf(
                i32(117),
                i32(118),
            )
        }

        val functionInstance = FunctionInstance.HostFunction(
            type = definedType,
            function = hostFunction,
        )

        val params = listOf(
            i32(115),
            i64(116),
        )

        params.forEach { value ->
            stack.push(Stack.Entry.Value(value))
        }

        val expected = Err(
            InvocationError.HostFunctionInconsistentWithType(
                i64ValueType(),
                i32(118),
            ),
        )

        val actual = HostFunctionCallImpl(
            store = store,
            stack = stack,
            function = functionInstance,
        )

        assertEquals(expected, actual)
    }
}
