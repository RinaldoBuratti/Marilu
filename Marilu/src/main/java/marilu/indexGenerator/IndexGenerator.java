package marilu.indexGenerator;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static org.elasticsearch.common.xcontent.XContentFactory.*;


public class IndexGenerator {
	private File pathFile;
	private File f;
	private String path;
	
	public IndexGenerator(String path) {
		this.path = path;
	}
	
	public void run() {
		this.pathFile = new File(this.path);
		File[] files = this.pathFile.listFiles();
		
		
		try {
	        
			for(int i=0; i<=files.length; i++) {
				this.f = files[i];
				String id = "" + i + "";
				
				Settings settings = Settings.settingsBuilder()
		                .put("cluster.name", "elasticsearch").build();
		        Client client = TransportClient.builder().settings(settings).build()
		                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
		        
		        BulkRequestBuilder bulkRequest = client.prepareBulk();
		        
				indexHTML(client, bulkRequest, f, id);
				client.close();
			}
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void indexHTML(Client c, BulkRequestBuilder bulk, File f, String id) {
		
		try {
			Document html = Jsoup.parse(f, "UTF-8");
			bulk.add(c.prepareIndex("pages", "html", id)
					.setSource(jsonBuilder()
							.startObject()
							 .field("title", html.title())
							 .field("text", html.body().text())
							 .field("html", html.html())
							 .endObject()
							)
					);
			BulkResponse response = bulk.get();
			
		} catch(IOException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}
