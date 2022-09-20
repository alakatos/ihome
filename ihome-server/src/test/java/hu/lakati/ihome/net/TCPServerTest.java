package hu.lakati.ihome.net;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ServerSocketFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hu.lakati.ihome.net.protocol.EHomeProtocolException;

@RunWith(MockitoJUnitRunner.class)
public class TCPServerTest {

	@Mock
	ServerSocketFactory ssfMock;
	
	@Mock
	ServerSocket serverSocketMock;
	
	@Mock
	ConnectionHandler connectionHandlerMock;
	
	@Mock
	private Socket socketMock;

	
	private TCPServer tcpServer;
	private final int port = 8888;

	
	
	@Before
	public void setUp() throws Exception {
		when(ssfMock.createServerSocket(port)).thenReturn(serverSocketMock);
		tcpServer = new TCPServer(port, connectionHandlerMock, ssfMock);
	}

	@Test
	public void testAccept() throws IOException, InterruptedException, EHomeProtocolException {
		when(serverSocketMock.accept()).thenAnswer((i) ->  {
			tcpServer.stop(); //quit from server while loop
			return socketMock;
		});

		tcpServer.run();
		verify(connectionHandlerMock).socketCreated(socketMock);
	}

	@Test
	public void testSocketTimeout() throws IOException, InterruptedException, EHomeProtocolException {
		final int maxCalls = 3;
		AtomicInteger callCount = new AtomicInteger(0);
		when(serverSocketMock.accept()).thenAnswer((i) ->  {
			if (callCount.incrementAndGet() == maxCalls) {
				Thread.sleep(100);
				tcpServer.stop(); //quit from server while loop
				throw new SocketTimeoutException("test");
			}
			return socketMock;
		});

		tcpServer.run();
		verify(serverSocketMock, times(maxCalls)).accept();
		verify(connectionHandlerMock, times(maxCalls-1)).socketCreated(socketMock);
	}

	@Test
	public void testUnexpectedExceptionThrown() throws IOException, InterruptedException, EHomeProtocolException {
		final int maxCalls = 3;
		AtomicInteger callCount = new AtomicInteger(0);
		when(serverSocketMock.accept()).thenAnswer((i) ->  {
			if (callCount.incrementAndGet() == 1) {
				throw new RuntimeException("test");
			}
			if (callCount.get() == maxCalls) { 
				tcpServer.stop(); //quit from server while loop
			}
			
			return socketMock;
		});

		tcpServer.run();
		verify(serverSocketMock, times(maxCalls)).accept();
		verify(connectionHandlerMock, times(maxCalls-1)).socketCreated(socketMock);
	}

	@Test
	public void testServerSocketClosed() throws IOException, InterruptedException, EHomeProtocolException {
		when(serverSocketMock.accept()).thenThrow(new IOException("test"));

		tcpServer.run();
		
		verify(serverSocketMock).accept();
		verify(connectionHandlerMock, never()).socketCreated(socketMock);
	}
	@After
	public void tearDown() throws IOException {
		tcpServer.stop();
		tcpServer.shutdown();
	}
}
