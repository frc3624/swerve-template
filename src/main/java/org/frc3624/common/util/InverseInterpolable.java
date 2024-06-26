package org.frc3624.common.util;

public interface InverseInterpolable<T> {
    double inverseInterpolate(T upper, T query);
}
