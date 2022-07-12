package databricks.design;
import java.util.*;

public class WebCrawler {
    private Set<String> crawledUrls = new HashSet<>();

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        String hostname = getHostname(startUrl);
        crawledUrls.add(startUrl);
        helper(hostname, startUrl, htmlParser);
        return new ArrayList<>(crawledUrls);
    }

    public void helper(String hostname, String currentUrl, HtmlParser htmlParser) {
        List<Thread> threads = new ArrayList<>();
        for(String url : htmlParser.getUrls(currentUrl)) {
            if(getHostname(url).equals(hostname)) {
                synchronized(crawledUrls) {
                    if(!crawledUrls.contains(url)) {
                        crawledUrls.add(url);
                        Thread thread = new Thread(() -> {
                            helper(hostname, url, htmlParser);
                        });
                        threads.add(thread);
                        thread.start();
                    }
                }
            }
        }
        try {
            for(Thread thread : threads) thread.join();
        } catch (Exception e) {

        }
    }

    public static String getHostname(String url) {
        url = url.substring(7);
        int last = url.indexOf("/");
        return url.substring(0, last == -1 ? url.length() : last);
    }
}
