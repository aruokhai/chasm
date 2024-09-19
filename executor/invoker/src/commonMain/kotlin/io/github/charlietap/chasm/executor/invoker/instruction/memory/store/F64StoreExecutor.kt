@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.memory.store

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.MemoryInstruction
import io.github.charlietap.chasm.executor.memory.write.MemoryInstanceDoubleWriterImpl
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.popF64
import io.github.charlietap.chasm.executor.runtime.store.Store

internal typealias F64StoreExecutor = (Store, Stack, MemoryInstruction.F64Store) -> Result<Unit, InvocationError>

internal inline fun F64StoreExecutor(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.F64Store,
): Result<Unit, InvocationError> =
    F64StoreExecutor(
        store = store,
        stack = stack,
        instruction = instruction,
        storeNumberValueExecutor = ::StoreNumberValueExecutor,
    )

internal inline fun F64StoreExecutor(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.F64Store,
    storeNumberValueExecutor: StoreNumberValueExecutor<Double>,
): Result<Unit, InvocationError> = storeNumberValueExecutor(
    store,
    stack,
    instruction.memoryIndex,
    instruction.memArg,
    Double.SIZE_BYTES,
    Stack::popF64,
    ::MemoryInstanceDoubleWriterImpl,
)
