package org.frc3624.common.util;

public interface Interpolable<T> {
    T interpolate(T other, double t);
}
