package io.github.charlietap.chasm.executor.instantiator.import

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import io.github.charlietap.chasm.ast.module.Import
import io.github.charlietap.chasm.ast.module.Module
import io.github.charlietap.chasm.executor.invoker.ext.functionType
import io.github.charlietap.chasm.executor.runtime.error.ModuleTrapError
import io.github.charlietap.chasm.executor.runtime.ext.function
import io.github.charlietap.chasm.executor.runtime.instance.ExternalValue
import io.github.charlietap.chasm.executor.runtime.store.Store
import io.github.charlietap.chasm.type.ext.definedType
import io.github.charlietap.chasm.type.ext.functionType
import io.github.charlietap.chasm.ast.module.Import as ModuleImport

internal typealias FunctionImportMatcher = (Store, Module, Import.Descriptor.Function, ExternalValue.Function) -> Result<Boolean, ModuleTrapError>

internal fun FunctionImportMatcher(
    store: Store,
    module: Module,
    descriptor: ModuleImport.Descriptor.Function,
    import: ExternalValue.Function,
): Result<Boolean, ModuleTrapError> = binding {
    val actualFunction = store.function(import.address).bind()
    val actualFunctionType = actualFunction.functionType().bind()

    val requiredImportType = module.types[descriptor.typeIndex.idx.toInt()]
    val requiredFunctionType = requiredImportType.recursiveType.definedType().functionType()

    requiredFunctionType == actualFunctionType
}
