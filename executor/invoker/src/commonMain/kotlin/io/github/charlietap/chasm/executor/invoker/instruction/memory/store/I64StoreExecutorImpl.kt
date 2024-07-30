@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.memory.store

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.MemoryInstruction
import io.github.charlietap.chasm.executor.memory.write.MemoryInstanceLongWriterImpl
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.popI64
import io.github.charlietap.chasm.executor.runtime.store.Store

internal inline fun I64StoreExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.I64Store,
): Result<Unit, InvocationError> =
    I64StoreExecutorImpl(
        store = store,
        stack = stack,
        instruction = instruction,
        storeNumberValueExecutor = ::StoreNumberValueExecutorImpl,
    )

internal inline fun I64StoreExecutorImpl(
    store: Store,
    stack: Stack,
    instruction: MemoryInstruction.I64Store,
    storeNumberValueExecutor: StoreNumberValueExecutor<Long>,
): Result<Unit, InvocationError> = storeNumberValueExecutor(
    store,
    stack,
    instruction.memoryIndex,
    instruction.memArg,
    Long.SIZE_BYTES,
    Stack::popI64,
    ::MemoryInstanceLongWriterImpl,
)
