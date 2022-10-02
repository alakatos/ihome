package hu.lakati.ihome.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovingAverage {
    private final int maxSize;
    private final List<Double> values = new LinkedList();

    private double sum;

    public void add(double value) {
        if (values.size()>=maxSize) {
            double removedValue = values.remove(0);
            sum -= removedValue;
        }
        values.add(value);
        sum += value;
    }

    public double getAvg() {
        return values.isEmpty() ? 0 : sum/values.size();
    }
    
}
