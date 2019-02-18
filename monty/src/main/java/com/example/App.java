package com.example;

import java.foreign.Scope;
import java.foreign.memory.Pointer;

import static org.python.Python_h.*;
import static org.python.pylifecycle_h.*;
import static org.python.pythonrun_h.*;

public class App {
    public static void main(String[] args) {
        Py_Initialize();
        try (Scope s = Scope.newNativeScope()) {
            PyRun_SimpleStringFlags(s.allocateCString("print(sum([23, 15, 4, 16, 42, 8]))\n"), Pointer.nullPointer());
        }
        Py_Finalize();
    }
}
