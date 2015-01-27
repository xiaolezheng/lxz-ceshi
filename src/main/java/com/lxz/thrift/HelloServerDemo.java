package com.lxz.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * blog http://www.micmiu.com
 *
 * @author Michael
 *
 */
public class HelloServerDemo {
    private static final Logger logger = LoggerFactory.getLogger(HelloServerDemo.class);

	public static final int SERVER_PORT = 8090;

	public void startServer() {
		try {
            logger.info("HelloWorld TSimpleServer start ....");
            // 简单的单线程服务模型，一般用于测试
            TServer server = createNonblockingServer();

            server.serve();

		} catch (Exception e) {
		    logger.error("",e);
		}
	}

    public TServer createSimpleServer() throws Exception{
        TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                new HelloWorldImpl());
        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
        TServer.Args tArgs = new TServer.Args(serverTransport);
        tArgs.processor(tprocessor);
        tArgs.protocolFactory(new TBinaryProtocol.Factory());
        // tArgs.protocolFactory(new TCompactProtocol.Factory());
        // tArgs.protocolFactory(new TJSONProtocol.Factory());

        // 简单的单线程服务模型，一般用于测试
        TServer server = new TSimpleServer(tArgs);

        return server;
    }

    public TServer createThreadPoolServer() throws Exception{
        TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                new HelloWorldImpl());

        TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
        TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(
                serverTransport);
        ttpsArgs.processor(tprocessor);
        ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());

        // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
        TServer server = new TThreadPoolServer(ttpsArgs);

        return server;
    }

    public TServer createNonblockingServer() throws Exception{
        TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(
                new HelloWorldImpl());

        TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(
                SERVER_PORT);
        TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(
                tnbSocketTransport);
        tnbArgs.processor(tprocessor);
        tnbArgs.transportFactory(new TFramedTransport.Factory());
        tnbArgs.protocolFactory(new TCompactProtocol.Factory());

        // 使用非阻塞式IO，服务端和客户端需要指定TFramedTransport数据传输的方式
        TServer server = new TNonblockingServer(tnbArgs);

        return server;
    }



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HelloServerDemo server = new HelloServerDemo();
		server.startServer();
	}

}
