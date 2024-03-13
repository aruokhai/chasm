@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.table

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.TableInstruction
import io.github.charlietap.chasm.executor.invoker.ext.index
import io.github.charlietap.chasm.executor.invoker.ext.peekFrameOrError
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.store.Store

internal inline fun ElementDropExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: TableInstruction.ElemDrop,
): Result<Unit, InvocationError> = binding {

    val frame = stack.peekFrameOrError().bind()
    val elementAddress = frame.state.module.elementAddress(instruction.elemIdx.index()).bind()
    val elementInstance = store.element(elementAddress).bind()

    store.elements[elementAddress.address] = elementInstance.copy(
        elements = mutableListOf(),
        dropped = true,
    )
}
