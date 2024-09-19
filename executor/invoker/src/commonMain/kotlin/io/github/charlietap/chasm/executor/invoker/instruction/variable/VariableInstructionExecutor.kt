package io.github.charlietap.chasm.executor.invoker.instruction.variable

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.instruction.VariableInstruction
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.store.Store

internal typealias VariableInstructionExecutor = (VariableInstruction, Store, Stack) -> Result<Unit, InvocationError>

internal fun VariableInstructionExecutor(
    instruction: VariableInstruction,
    store: Store,
    stack: Stack,
): Result<Unit, InvocationError> =
    VariableInstructionExecutor(
        instruction = instruction,
        store = store,
        stack = stack,
        localGetExecutor = ::LocalGetExecutor,
        localSetExecutor = ::LocalSetExecutor,
        localTeeExecutor = ::LocalTeeExecutor,
        globalGetExecutor = ::GlobalGetExecutor,
        globalSetExecutor = ::GlobalSetExecutor,
    )

internal fun VariableInstructionExecutor(
    instruction: VariableInstruction,
    store: Store,
    stack: Stack,
    localGetExecutor: LocalGetExecutor,
    localSetExecutor: LocalSetExecutor,
    localTeeExecutor: LocalTeeExecutor,
    globalGetExecutor: GlobalGetExecutor,
    globalSetExecutor: GlobalSetExecutor,
): Result<Unit, InvocationError> = binding {
    when (instruction) {
        is VariableInstruction.LocalGet -> localGetExecutor(stack, instruction).bind()
        is VariableInstruction.LocalSet -> localSetExecutor(stack, instruction).bind()
        is VariableInstruction.LocalTee -> localTeeExecutor(stack, instruction).bind()
        is VariableInstruction.GlobalGet -> globalGetExecutor(store, stack, instruction).bind()
        is VariableInstruction.GlobalSet -> globalSetExecutor(store, stack, instruction).bind()
    }
}
