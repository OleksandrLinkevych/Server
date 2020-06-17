package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Sort implements ITask<byte[]>, Serializable {
    byte[] byteArray;

    public Sort(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    void heapify(double arr[], int heapSize, int i)
    {
        int largest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;

        if (left < heapSize && arr[left] > arr[largest])
            largest = left;

        if (right < heapSize && arr[right] > arr[largest])
            largest = right;

        if (largest != i)
        {
            double swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;


            heapify(arr, heapSize, largest);
        }
    }

    public double[] sort(double arr[])
    {
        int size = arr.length;


        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(arr, size, i);


        for (int i=size-1; i>=0; i--)
        {

            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;


            heapify(arr, i, 0);
        }
        return arr;
    }

    @Override
    public byte[] execute() {
        String delimiter = " ";

        String content = new String(this.byteArray);
        String[] strArray = content.replace(System.lineSeparator(), delimiter).split(delimiter);
        double[] numberArray = Arrays.stream(strArray).mapToDouble(Double::parseDouble).toArray();

        double[] result = this.sort(numberArray);

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String strResult = Arrays.stream(result).mapToObj(decimalFormat::format).collect(Collectors.joining(delimiter));
        return strResult.getBytes();
    }
}
