@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.memory.load

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.MemArg
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.memory.LinearMemory
import io.github.charlietap.chasm.executor.runtime.store.Store
import io.github.charlietap.chasm.executor.runtime.value.NumberValue.I64

internal inline fun I64SizedUnsignedLoadExecutorImpl(
    store: Store,
    stack: Stack,
    memArg: MemArg,
    sizeInBytes: Int,
): Result<Unit, InvocationError> =
    I64SizedUnsignedLoadExecutorImpl(
        store = store,
        stack = stack,
        memArg = memArg,
        sizeInBytes = sizeInBytes,
        loadSizedUnsignedNumberValueExecutor = ::LoadSizedUnsignedNumberValueExecutorImpl,
    )

internal inline fun I64SizedUnsignedLoadExecutorImpl(
    store: Store,
    stack: Stack,
    memArg: MemArg,
    sizeInBytes: Int,
    crossinline loadSizedUnsignedNumberValueExecutor: LoadSizedUnsignedNumberValueExecutor<Long, ULong>,
): Result<Unit, InvocationError> = loadSizedUnsignedNumberValueExecutor(
    store,
    stack,
    memArg,
    sizeInBytes,
    LinearMemory::readULong,
    ULong::toLong,
    ::I64,
)
