package com.example.zzt.whyfi.model;

import com.example.zzt.whyfi.common.BytesSetting;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by zzt on 5/10/16.
 * <p/>
 * Usage:
 * The abstraction of content send/receive between devices
 */
public class Message {

    private final Device device;
    private final String message;
    private final String time;

    public Message(Device device, String message) {
        this.device = device;
        this.message = message;
        time = new Date().toString();
    }

    public Device getDevice() {
        return device;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public static Message getFromBytes(byte[] bytes) throws UnsupportedEncodingException {
        int i;
        for (i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] == BytesSetting.SPLIT_BYTE) {
                break;
            }
        }
        Device device = Device.getFromBytes(Arrays.copyOf(bytes, i));
        String msg = new String(bytes, i, bytes.length - i, BytesSetting.UTF_8);
        return new Message(device, msg);
    }

    public byte[] toBytes() throws UnsupportedEncodingException {
        byte[] bytes = device.toBytes();
        int devLen = bytes.length;
        byte[] res = new byte[devLen + 1 + message.length()];
        System.arraycopy(bytes, 0, res, 0, devLen);
        res[devLen] = BytesSetting.SPLIT_BYTE;
        System.arraycopy(message.getBytes(BytesSetting.UTF_8), 0, res, devLen + 1, message.length());
        return res;
    }
}