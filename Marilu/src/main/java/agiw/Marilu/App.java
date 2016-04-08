package agiw.Marilu;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import agiw.Marilu.indexGenerator.IndexGenerator;
import agiw.retrieve.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
    	/*IndexGenerator ig = new IndexGenerator("/Volumes/My Passport/Dataset/docs Rinaldo");
    	ig.run();*/
    	SimpleGetter sg = new SimpleGetter();
    	sg.run();
    }
}
