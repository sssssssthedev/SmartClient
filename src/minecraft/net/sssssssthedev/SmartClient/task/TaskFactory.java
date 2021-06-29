package net.sssssssthedev.SmartClient.task;

import net.sssssssthedev.SmartClient.task.basic.BasicTask;

import java.util.List;

public interface TaskFactory<T extends BasicTask> {

    void removeTask(String taskName);

    void removeTask(T task);

    List<T> getTasks();
}
