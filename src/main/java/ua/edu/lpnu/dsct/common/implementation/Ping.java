package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;

public class Ping implements ITask<Integer>, Serializable {
    @Override
    public Integer execute() {
        return 0;
    }
}
