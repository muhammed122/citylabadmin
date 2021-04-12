package com.muhammed.citylabadmin.helper;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class NetworkState {

    public Status status;
    public Object message;
    public Object data;

    public NetworkState(Status status, Object message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static NetworkState LOADING = new NetworkState(Status.RUNNING, null, null);

    public static NetworkState getLoaded(Object dataSuccess) {
        return new NetworkState(Status.SUCCESS, null, dataSuccess);
    }

    public static NetworkState getErrorMessage(Throwable throwable) {

        if (throwable.equals(new IOException()))
            return new NetworkState(Status.FAILED, "No Connection", null);
        if (throwable.equals(new SocketTimeoutException()))
            return new NetworkState(Status.FAILED, "Bad Connection", null);
        else
            return new NetworkState(Status.FAILED, "Error", null);

    }

    public static NetworkState getErrorMessage(String message) {
        return new NetworkState(Status.FAILED, message, null);

    }


    public enum Status {
        RUNNING, FAILED, SUCCESS
    }

}

