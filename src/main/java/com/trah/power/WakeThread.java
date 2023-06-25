package com.trah.power;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class WakeThread extends Thread {
    String ip = null;
    String macAdder = null;

    public WakeThread(String ip, String macAddr) {
        this.ip = ip;
        this.macAdder = macAddr;
    }

    @Override
    public void run() {
        super.run();
        wakeOnLan(ip, macAdder);
    }

    public void wakeOnLan(String ip, String macAdder) {
        DatagramSocket datagramSocket = null;
        try {
            byte[] mac = getMacBytes(macAdder);
            byte[] magic = new byte[6 + 16 * mac.length];
            //1.写入6个FF
            for (int i = 0; i < 6; i++) {
                magic[i] = (byte) 0xff;
            }
            //2.写入16次mac地址
            for (int i = 6; i < magic.length; i += mac.length) {
                System.arraycopy(mac, 0, magic, i, mac.length);
            }
            datagramSocket = new DatagramSocket();
            DatagramPacket datagramPacket = new DatagramPacket(magic, magic.length, InetAddress.getByName(ip), 9);
            datagramSocket.send(datagramPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null)
                datagramSocket.close();
        }
    }

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-|\\.)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("无效mac地址");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效mac地址");
        }
        return bytes;
    }
}
