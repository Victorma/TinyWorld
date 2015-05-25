using UnityEngine;
using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Collections.Generic;
using System.Linq;

public class IcaroSocketImp : IcaroSocket {

    private UdpClient client;
    private List<string> messages = new List<string>();
    private IPEndPoint serverPoint;
    private IPEndPoint clientPoint;
    private Boolean started = false;
    private Thread startClient;

    private UTF8Encoding encoder;

    private int serverPort = 9877;
    private int clientPort = 9878;
    private string ipClient = "127.0.0.1";
    private string ipServer = "127.0.0.1";

    private static Mutex mutex = new Mutex();

    public IcaroSocketImp() {
        encoder = new UTF8Encoding();
    }

    public override bool isConnected() {
        return started;
    }

    public override void connect() {
        if (!started) {
            client = new UdpClient(clientPort);

            serverPoint = new IPEndPoint(IPAddress.Parse(ipServer), serverPort);
            clientPoint = new IPEndPoint(IPAddress.Parse(ipClient), clientPort);
            //clientPoint = new IPEndPoint(IPAddress.Any, 0);

            client.Connect(serverPoint);

            startClient = new Thread(new ThreadStart(StartClient));
            startClient.IsBackground = true;
            started = true;
            //client.BeginReceive(new AsyncCallback(bytesReceived), null);

            startClient.Start();
        }
    }

    public override void disconnect() {
        started = false;
        client.Close();
        while (startClient.IsAlive) { }
    }

    public override List<string> getMessages() {
        mutex.WaitOne();
        List<string> toGame = messages;
        if (messages.Count != 0) {
            messages = new List<string>();
        }
        mutex.ReleaseMutex();
        return toGame;

    }

    public override void sendMessage(string message) {
        if (started) {
            byte[] byteMsg = encoder.GetBytes(message);
            Debug.Log(">>>" + message);
            client.Send(byteMsg, byteMsg.Length);
        }
    }

    private void bytesReceived(IAsyncResult async) {
        string msg = encoder.GetString(client.EndReceive(async, ref clientPoint));
        mutex.WaitOne();
        messages.Add(msg);
        mutex.ReleaseMutex();
    }


    public void StartClient() {
        try {
            while (started) {
                byte[] recData = client.Receive(ref clientPoint);
                if (recData.Length != 0) {
                    string msg = encoder.GetString(recData);
                    Debug.Log("<<<" + msg);
                    mutex.WaitOne();
                    messages.Add(msg);
                    mutex.ReleaseMutex();
                }
            }
        } catch (Exception e) {
            Debug.Log(e.StackTrace);
        }
        Debug.Log("thread dead");
    }

}
