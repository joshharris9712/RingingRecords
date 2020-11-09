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
		this.url = url.replace("http://", "https://");
	}

	public void scrape() throws IOException {

		boolean morePages;
		int pageNumber = 1;

		URL u;
		u = new URL(url);
		InputStreamReader is = null;
		BufferedReader b;
		List<String> list = new ArrayList<String>();
		System.out.println("Starting to loop through");
		do {
			morePages = false;
			u = new URL(url + (pageNumber>1? "&page=" + pageNumber : ""));
			is = new InputStreamReader(u.openStream());
			b = new BufferedReader(is);

			String inputLine;

			while ((inputLine = b.readLine()) != null) {
				System.out.println(inputLine);
				if (inputLine.contains("href=\"/view.php?id=")) {
					System.out.println("Found Performance");
					String id = inputLine.substring(inputLine.indexOf("id=") + 3, inputLine.lastIndexOf('"'));
					list.add(id);
					System.out.println("List++");
				}

				if (inputLine.contains("page=")) {
					String numStr = inputLine.substring(inputLine.indexOf("page=") + 5, inputLine.lastIndexOf('"'));
					System.out.println(numStr);
					try {
						if (Integer.valueOf(numStr) > pageNumber) {
							morePages = true;
							pageNumber++;
							System.out.println("Page++");
						}
					}catch (Exception e){
						// Lazy way of circumventing error on multiple pages
					}
				}
			}
			System.out.println("Closing Buffer Stream");
			is.close();
			b.close();
			System.out.println("Finishing if no more pages");
		}while(morePages);

		System.out.println("Found " + list.size() + " performances");


		String pdfUrl = "https://bb.ringingworld.co.uk/pdf.php?id=";
		for(String id : list){
			System.out.println("Downloading " + id);
			URL pdf = new URL(pdfUrl + id);
			File f = new File(downloadLocation + "/" + id + ".pdf");

			ReadableByteChannel rbc = Channels.newChannel(pdf.openStream());
			FileOutputStream fos = new FileOutputStream(f);

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();

		}


	}
}
