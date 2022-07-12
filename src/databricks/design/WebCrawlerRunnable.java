package databricks.design;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
interface HtmlParser {
    public List<String> getUrls(String url);
 }
public class WebCrawlerRunnable {
    class UrlProcess implements Runnable {
        String startUrl;
        HtmlParser htmlParser;
        Set<String> visitedSet;
        String hostName;
        List<Thread> threads;

        public UrlProcess(String startUrl, HtmlParser htmlParser, Set<String> visitedSet, String hostName) {
            this.startUrl = startUrl;
            this.htmlParser = htmlParser;
            this.visitedSet = visitedSet;
            this.hostName = hostName;
            threads = new ArrayList();
        }

        public void run() {
            if(!startUrl.contains(hostName)) return;
            visitedSet.add(startUrl);
            List<String> newUrls = htmlParser.getUrls(startUrl);
            for(String newUrl: newUrls) {
                // System.out.println("got new Url:" + newUrl);
                if(!visitedSet.contains(newUrl)) {
                    Runnable r = new UrlProcess(newUrl, htmlParser, visitedSet, hostName);
                    Thread thread = new Thread(r);
                    thread.start();
                    threads.add(thread);
                }
            }

            for(Thread t: threads) {
                try {
                    t.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        // create concurrent hash set to record visited URLs
        ConcurrentHashMap<String, Integer> visitedMap = new ConcurrentHashMap();
        Set<String> visitedSet = visitedMap.newKeySet();
        String hostName = startUrl.split("/")[2];
        // System.out.println("hostname:" + hostName);
        List<Thread> threads = new ArrayList();

        Runnable r = new UrlProcess(startUrl, htmlParser, visitedSet, hostName);
        Thread initialThread = new Thread(r);
        initialThread.start();
        try {
            initialThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println("visitedSet:" + visitedSet);
        return new ArrayList(visitedSet);
    }
}
