package net.sssssssthedev.SmartClient.event;

import net.sssssssthedev.SmartClient.Main;

import java.lang.reflect.InvocationTargetException;

public abstract class Event {

    private boolean cancelled;

    public enum State {
        PRE("PRE", 0), POST("POST", 1);
        State(String string, int number) {
        }
    }

    public Event call() {
        this.cancelled = false;
        call(this);
        return this;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {

        this.cancelled = cancelled;
    }

    private static void call(Event event) {
        ArrayHelper<EventData> dataList = Main.instance.eventManager.get(event.getClass());
        if (dataList != null) {
            for (EventData data : dataList) {
                try {
                    data.target.invoke(data.source, event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
