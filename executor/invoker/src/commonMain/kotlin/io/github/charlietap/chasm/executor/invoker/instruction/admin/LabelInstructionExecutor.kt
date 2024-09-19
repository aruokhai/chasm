@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.admin

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.popLabel

internal typealias LabelInstructionExecutor = (Stack.Entry.Label, Stack) -> Result<Unit, InvocationError>

@Suppress("UNUSED_PARAMETER")
internal inline fun LabelInstructionExecutor(
    label: Stack.Entry.Label,
    stack: Stack,
): Result<Unit, InvocationError> = binding {
    stack.popLabel().bind()
}
