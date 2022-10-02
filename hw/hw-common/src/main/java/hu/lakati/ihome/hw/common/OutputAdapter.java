package hu.lakati.ihome.hw.common;

import hu.lakati.ihome.common.Command;

public interface OutputAdapter<T> {
    T convertToLowLevelData(Command command);
}