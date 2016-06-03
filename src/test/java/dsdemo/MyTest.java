package dsdemo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

import com.ds.service.MessageService;


public class MyTest {

	@Test
	public void myTest() throws UnknownHostException {
		MessageService service = new MessageService();
		String ipAndPort = InetAddress.getLocalHost().getHostAddress() + ":8080";
		service.sendEmail("hjz", "hjzgg", "2570230521@qq.com", "http://" + ipAndPort + "/dsdemo/");
	}
}