package marilu.retrieve;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.UnknownHostException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SimpleGetter {

	public SimpleGetter() {

	}

	public void run() {

		try {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", "elasticsearch").build();
		Client client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
			    .add("pages", "html", "2")           
			    .add("pages", "html", "3", "5")        
			    .get();

			for (MultiGetItemResponse itemResponse : multiGetItemResponses) { 
			    GetResponse response = itemResponse.getResponse();
			    if (response.isExists()) {                      
			        String json = response.getSourceAsString(); 
			        Document result = Jsoup.parseBodyFragment(json);
			        System.out.println(result.toString());
			    }
			}
		
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
