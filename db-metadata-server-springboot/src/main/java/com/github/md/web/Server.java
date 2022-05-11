package com.github.md.web;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author pengxg
 * @date 2022/5/11 3:37 下午
 */
@Configuration
public class Server implements ApplicationListener<WebServerInitializedEvent> {
    private static String ip;
    private static int port;

    public static String getUrl() {
        return "http://" + getIp() + ":" + port;
    }

    public static String getIp() {
        if (ip == null) {
            synchronized (Server.class) {
                if (ip == null) {
                    InetAddress address;
                    try {
                        address = InetAddress.getLocalHost();
                        ip = address.getHostAddress();
                    } catch (UnknownHostException e) {
                        throw new WebException("无法获取当前服务id");
                    }
                }
            }
        }

        return ip;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }
}
