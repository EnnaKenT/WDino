package com.vlad.wdino.background;

public interface IBackgroundCallback<T> {
    void doOnSuccess(T result);
    void doOnError(Exception e);
}
