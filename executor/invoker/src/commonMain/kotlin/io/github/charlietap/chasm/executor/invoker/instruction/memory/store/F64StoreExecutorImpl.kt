@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.memory.store

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.MemoryInstruction
import io.github.charlietap.chasm.executor.invoker.ext.popF64
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.memory.LinearMemory
import io.github.charlietap.chasm.executor.runtime.store.Store

internal inline fun F64StoreExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.F64Store,
): Result<Unit, InvocationError> =
    F64StoreExecutorImpl(
        store = store,
        stack = stack,
        instruction = instruction,
        storeNumberValueExecutor = ::StoreNumberValueExecutorImpl,
    )

internal inline fun F64StoreExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.F64Store,
    storeNumberValueExecutor: StoreNumberValueExecutor<Double>,
): Result<Unit, InvocationError> = storeNumberValueExecutor(
    store,
    stack,
    instruction.memArg,
    Stack::popF64,
    LinearMemory::writeDouble,
)
