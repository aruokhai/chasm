@file:Suppress("NOTHING_TO_INLINE")

package io.github.charlietap.chasm.executor.invoker.instruction.control

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.executor.invoker.function.HostFunctionCall
import io.github.charlietap.chasm.executor.invoker.function.WasmFunctionCall
import io.github.charlietap.chasm.executor.runtime.Stack
import io.github.charlietap.chasm.executor.runtime.error.InvocationError
import io.github.charlietap.chasm.executor.runtime.ext.function
import io.github.charlietap.chasm.executor.runtime.ext.popFunctionAddress
import io.github.charlietap.chasm.executor.runtime.instance.FunctionInstance
import io.github.charlietap.chasm.executor.runtime.store.Store

internal typealias CallRefExecutor = (Store, Stack) -> Result<Unit, InvocationError>

internal inline fun CallRefExecutor(
    store: Store,
    stack: Stack,
): Result<Unit, InvocationError> =
    CallRefExecutor(
        store = store,
        stack = stack,
        tailRecursion = false,
        hostFunctionCall = ::HostFunctionCall,
        wasmFunctionCall = ::WasmFunctionCall,
    )

internal inline fun CallRefExecutor(
    store: Store,
    stack: Stack,
    tailRecursion: Boolean,
    crossinline hostFunctionCall: HostFunctionCall,
    crossinline wasmFunctionCall: WasmFunctionCall,
): Result<Unit, InvocationError> = binding {

    val value = stack.popFunctionAddress().bind()

    when (val instance = store.function(value.address).bind()) {
        is FunctionInstance.HostFunction -> hostFunctionCall(store, stack, instance).bind()
        is FunctionInstance.WasmFunction -> wasmFunctionCall(store, stack, instance, tailRecursion).bind()
    }
}
