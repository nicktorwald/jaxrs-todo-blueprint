package org.nicktorwald.todo.helper;

import java.util.concurrent.CompletionException;
import java.util.function.Function;

public class CompletionStageHelper {
    public static <R> Function<Throwable, R> unfold(Function<Throwable, R> fn) {
        return (throwable) ->
            fn.apply(throwable instanceof CompletionException
                                ? throwable.getCause()
                                : throwable);
    }
}
