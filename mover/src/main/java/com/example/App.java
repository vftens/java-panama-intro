package com.example;

import java.foreign.Library;
import java.foreign.Libraries;
import java.foreign.Scope;
import java.foreign.annotations.NativeHeader;
import java.foreign.annotations.NativeFunction;
import java.foreign.annotations.NativeStruct;
import java.foreign.annotations.NativeGetter;
import java.foreign.memory.LayoutType;
import java.foreign.memory.Pointer;
import java.foreign.memory.Struct;
import java.lang.invoke.MethodHandles;

public class App {
    @NativeStruct("[i32(x) i32(y)](Point)")
    interface Point extends Struct<Point> {
        @NativeGetter("x")
        int x();
        @NativeGetter("y")
        int y();
    }

    @NativeHeader
    interface Mover {
        @NativeFunction("(u64:[i32 i32])v")
        void move(Pointer<Point> point);
	}

    public static void main(String[] args) {
        Library lib = Libraries.loadLibrary(MethodHandles.lookup(), "mover");
        Mover mover = Libraries.bind(Mover.class, lib);

        try (Scope scope = Scope.newNativeScope()) {
            Pointer<Point> point = scope.allocate(LayoutType.ofStruct(Point.class));
            mover.move(point);
            System.out.printf("X: %d Y: %d%n", point.get().x(), point.get().y());
        }
    }
}
