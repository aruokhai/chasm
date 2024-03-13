(module
  (import "env" "memory" (memory 1))
  (import "env" "println" (func $println (param i32)))

  (data (i32.const 0) "Hello World!\00")

  (func $host_function
    (call $println (i32.const 0))
  )

  (export "host_function" (func $host_function))
)
