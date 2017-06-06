package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by aibol on 6/6/17.
 */

public class ErrorEvent {

    public String errorMessage;

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
