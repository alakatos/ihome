package hu.lakati.ihome.hw.common.net;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

@RunWith(MockitoJUnitRunner.class)
public class TCPServerTest {

	@Mock
	ServerSocketFactory ssfMock;
	
	@Mock
	ServerSocket serverSocketMock;
	
	@Mock
	SocketAcceptor socketAcceptorMock;
	
	@Mock
	private Socket socketMock;

	
	private TCPServer tcpServer;
	private final int port = 8888;

	
	
	@Before
	public void setUp() throws Exception {
		when(ssfMock.createServerSocket(port)).thenReturn(serverSocketMock);
		tcpServer = new TCPServer(port, socketAcceptorMock, ssfMock);
	}

	@Test
	public void testAccept() throws IOException, InterruptedException, ProtocolException {
		when(serverSocketMock.accept()).thenAnswer((i) ->  {
			tcpServer.stop(); //quit from server while loop
			return socketMock;
		});

		tcpServer.run();
		verify(socketAcceptorMock).acceptSocket(socketMock);
	}

	@Test
	public void testSocketTimeout() throws IOException, InterruptedException, ProtocolException {
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
		verify(socketAcceptorMock, times(maxCalls-1)).acceptSocket(socketMock);
	}

	@Test
	public void testUnexpectedExceptionThrown() throws IOException, InterruptedException, ProtocolException {
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
		verify(socketAcceptorMock, times(maxCalls-1)).acceptSocket(socketMock);
	}

	@Test
	public void testServerSocketClosed() throws IOException, InterruptedException, ProtocolException {
		when(serverSocketMock.accept()).thenThrow(new IOException("test"));

		tcpServer.run();
		
		verify(serverSocketMock).accept();
		verify(socketAcceptorMock, never()).acceptSocket(socketMock);
	}
	@After
	public void tearDown() throws IOException {
		tcpServer.stop();
		tcpServer.shutdown();
	}
}
