package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SortTask implements ITask<byte[]>, Serializable {
    byte[] byteArray;

    public SortTask(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    // Процедура для преобразования в двоичную кучу поддерева с корневым узлом i, что является
// индексом в arr[]. n - размер кучи
    void heapify(double arr[], int heapSize, int i)
    {
        int largest = i; // Инициализируем наибольший элемент как корень
        int left = 2*i + 1; // левый = 2*i + 1
        int right = 2*i + 2; // правый = 2*i + 2

        // Если левый дочерний элемент больше корня
        if (left < heapSize && arr[left] > arr[largest])
            largest = left;

        // Если правый дочерний элемент больше, чем самый большой элемент на данный момент
        if (right < heapSize && arr[right] > arr[largest])
            largest = right;
        // Если самый большой элемент не корень
        if (largest != i)
        {
            double swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Рекурсивно преобразуем в двоичную кучу затронутое поддерево
            heapify(arr, heapSize, largest);
        }
    }

    public double[] sort(double arr[])
    {
        int size = arr.length;

        // Построение кучи (перегруппируем массив)
        for (int i = size / 2 - 1; i >= 0; i--)
            heapify(arr, size, i);

        // Один за другим извлекаем элементы из кучи
        for (int i=size-1; i>=0; i--)
        {
            // Перемещаем текущий корень в конец
            double temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Вызываем процедуру heapify на уменьшенной куче
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
