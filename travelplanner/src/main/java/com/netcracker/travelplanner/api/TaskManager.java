package com.netcracker.travelplanner.api;

import com.netcracker.travelplanner.entities.Edge;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class TaskManager implements TaskManagerInterface {

    private YandexExecutor yandexExecutor;
    private YandexParserExecutor yandexParserExecutor;
    private KiwiExecutor kiwiExecutor;

    @Override
    public List<Future<List<Edge>>> executeTask(List<Callable<List<Edge>>> list) {
        List<Future<List<Edge>>> resultList = new ArrayList<>();

        resultList.addAll(yandexExecutor.execute(list));
        resultList.addAll(yandexParserExecutor.execute(list));
        resultList.addAll(kiwiExecutor.execute(list));

        return resultList;
    }

    public void setYandexExecutor(YandexExecutor yandexExecutor) {
        this.yandexExecutor = yandexExecutor;
    }

    public void setYandexParserExecutor(YandexParserExecutor yandexParserExecutor) {
        this.yandexParserExecutor = yandexParserExecutor;
    }

    public void setKiwiExecutor(KiwiExecutor kiwiExecutor) {
        this.kiwiExecutor = kiwiExecutor;
    }
}
