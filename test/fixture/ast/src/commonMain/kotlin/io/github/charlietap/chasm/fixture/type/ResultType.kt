package io.github.charlietap.chasm.fixture.type

import io.github.charlietap.chasm.ast.type.ResultType
import io.github.charlietap.chasm.ast.type.ValueType

fun resultType(
    types: List<ValueType> = emptyList(),
) = ResultType(
    types = types,
)
