package com.bytedance.practice5.socket;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketThread extends Thread {
    public ClientSocketThread(SocketActivity.SocketCallback callback) {
        this.callback = callback;
    }

    private SocketActivity.SocketCallback callback;

    //head请求内容
    private static String content = "HEAD / HTTP/1.1\r\nHost:www.zju.edu.cn\r\n\r\n";

    @Override
    public void run() {
        // TODO 6 用socket实现简单的HEAD请求（发送content）
        //  将返回结果用callback.onresponse(result)进行展示
        Log.d("socket", "客户端线程start ");
        try {
            Socket socket = new Socket("10.203.6.122", 80); //服务器IP及端口

            BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
            BufferedInputStream is = new BufferedInputStream(socket.getInputStream());
            // 读文件
            byte[] data = new byte[1024 * 5];//每次读取的字节数

            if (socket.isConnected()) {
                if (!content.isEmpty()) {
                    Log.d("socket", "客户端发送 " + content);
                    os.write(content.getBytes());
                    os.flush();
                    int reciveLen = is.read(data);
                    if (reciveLen!=-1){
                        String receive = new String(data, 0, reciveLen);
                        Log.d("socket", "客户端收到 " + receive);
                        callback.onResponse(receive);
                    }else {
                        Log.d("socket", "客户端收到-1");
                    }

                }
                sleep(300);
            }
            Log.d("socket", "客户端断开 ");
            os.flush();
            os.close();
            socket.close(); //关闭socket

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}