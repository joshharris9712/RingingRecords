package sample;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

	private String url;
	private String downloadLocation;

	public Scraper(String url, String downloadLocation) {
		this.downloadLocation = downloadLocation;
		this.url = url;
	}

	public void scrape() {
		URL u;
		InputStreamReader is = null;
		BufferedReader b;

		try{

			u = new URL(url);
			is = new InputStreamReader(u.openStream());
			b = new BufferedReader(is);

			String inputLine;
			List<String> list = new ArrayList<String>();
			while((inputLine = b.readLine())!=null){
				if(inputLine.contains("href=\"view.php?id=")){
					String id = inputLine.substring(inputLine.indexOf("id=")+3, inputLine.lastIndexOf('"'));
					list.add(id);
					//System.out.println(id);
				}
			}

			b.close();
			is.close();

			String pdfUrl = "https://bb.ringingworld.co.uk/pdf.php?id=";
			for(String id : list){
				URL pdf = new URL(pdfUrl + id);
				File f = new File(downloadLocation + "/" + id + ".pdf");

				ReadableByteChannel rbc = Channels.newChannel(pdf.openStream());
				FileOutputStream fos = new FileOutputStream(f);

				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			}

		} catch (Exception e){
			e.printStackTrace();

		}

	}
}
