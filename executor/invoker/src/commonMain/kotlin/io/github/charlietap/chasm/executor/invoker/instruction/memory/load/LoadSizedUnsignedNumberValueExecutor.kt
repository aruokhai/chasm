package io.github.charlietap.chasm.executor.invoker.instruction.memory.load

import com.github.michaelbull.result.Result
import io.github.charlietap.chasm.ast.instruction.MemArg
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.store.Store

internal typealias SignTransformer<S, U> = (U) -> S
internal typealias LoadSizedUnsignedNumberValueExecutor<S, U> = (
    Store,
    Stack,
    MemArg,
    Int,
    SizedNumberValueReader<U>,
    SignTransformer<S, U>,
    Constructor<S>,
) -> Result<Unit, InvocationError>
