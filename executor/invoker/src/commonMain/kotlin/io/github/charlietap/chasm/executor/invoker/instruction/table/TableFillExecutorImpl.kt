@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.table

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.TableInstruction
import io.github.charlietap.chasm.executor.invoker.ext.index
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.contains
import io.github.charlietap.chasm.executor.runtime.ext.peekFrame
import io.github.charlietap.chasm.executor.runtime.ext.popI32
import io.github.charlietap.chasm.executor.runtime.ext.popReference
import io.github.charlietap.chasm.executor.runtime.ext.table
import io.github.charlietap.chasm.executor.runtime.ext.tableAddress
import io.github.charlietap.chasm.executor.runtime.store.Store

internal fun TableFillExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: TableInstruction.TableFill,
): Result<Unit, InvocationError> = binding {

    val frame = stack.peekFrame().bind()
    val tableAddress = frame.state.module.tableAddress(instruction.tableIdx.index()).bind()
    val tableInstance = store.table(tableAddress).bind()

    val elementsToFill = stack.popI32().bind()
    val fillValue = stack.popReference().bind()
    val tableOffset = stack.popI32().bind()

    val fillRange = tableOffset..<(tableOffset + elementsToFill)

    if (!tableInstance.elements.indices.contains(fillRange)) {
        Err(InvocationError.Trap.TrapEncountered).bind<Unit>()
    }

    if (elementsToFill == 0) return@binding

    fillRange.forEach { tableIndex ->
        tableInstance.elements[tableIndex] = fillValue
    }
}
