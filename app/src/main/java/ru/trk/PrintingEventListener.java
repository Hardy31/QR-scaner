package ru.trk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class PrintingEventListener extends EventListener {
    private long callStartNanos;
    private void printEvent(String name) {
        long nowNanos = System.nanoTime();
        if (name.equals("callStart")) {
            callStartNanos = nowNanos;
        }
        long elapsedNanos = nowNanos - callStartNanos;
        System.out.printf("%.3f %s%n", elapsedNanos / 1000000000d, name);
    }

    @Override public void callStart(Call call) {
        printEvent("callStart");
    }

    @Override public void callEnd(Call call) {
        printEvent("callEnd");
    }

    @Override public void dnsStart(Call call, String domainName) {
        printEvent("dnsStart");
    }

    @Override public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        printEvent("dnsEnd");
    }

    @Override
    public void connectStart(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        printEvent("connectStart");
    }


    @Override
    public void connectEnd(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        printEvent("connectEnd");
    }

    @Override
    public void connectionAcquired(@NonNull Call call, @NonNull Connection connection) {
        super.connectionAcquired(call, connection);
        printEvent("connectionAcquired");
    }

    @Override
    public void requestHeadersStart(@NonNull Call call) {
        super.requestHeadersStart(call);
        printEvent("requestHeadersStart");
    }

    @Override
    public void requestHeadersEnd(@NonNull Call call, @NonNull Request request) {
        super.requestHeadersEnd(call, request);
        printEvent("requestHeadersEnd");
    }

    @Override
    public void responseHeadersStart(@NonNull Call call) {
        super.responseHeadersStart(call);
        printEvent("responseHeadersStart");
    }

    @Override
    public void responseHeadersEnd(@NonNull Call call, @NonNull Response response) {
        super.responseHeadersEnd(call, response);
        printEvent("responseHeadersEnd");
    }

    @Override
    public void responseBodyStart(@NonNull Call call) {
        super.responseBodyStart(call);
        printEvent("responseBodyStart");
    }

    @Override
    public void responseBodyEnd(@NonNull Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        printEvent("responseBodyEnd");
    }

    @Override
    public void connectionReleased(@NonNull Call call, @NonNull Connection connection) {
        super.connectionReleased(call, connection);
        printEvent("connectionReleased");
    }

    @Override
    public void connectFailed(@NonNull Call call, @NonNull InetSocketAddress inetSocketAddress, @NonNull Proxy proxy, @Nullable Protocol protocol, @NonNull IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        printEvent("connectFailed");
    }
}
