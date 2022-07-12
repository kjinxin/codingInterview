package databricks.design;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
public class WebCrawlerSingleThread {
        public List<String> crawl(String startUrl, HtmlParser htmlParser) {

            LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

            ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();
            visited.put(startUrl, true);

            String startURLHostName = getHostName(startUrl);

            ExecutorService executor = Executors.newFixedThreadPool( 10, r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });

            queue.offer(startUrl);
            AtomicInteger threadCnt = new AtomicInteger(0);
            while(!queue.isEmpty() || threadCnt.get() > 0) {
                String url = queue.poll();
                if (url != null) {
                    threadCnt.incrementAndGet();
                    executor.submit(() -> {
                        // fetch html
                        // executor.submit(() -> save(url, html));
                        // executor.submit(() -> for (String url: parse(html)) {
                        //
                        //

                    for(String returnedUrl : htmlParser.getUrls(url)) {
                        if(getHostName(returnedUrl).equals(startURLHostName)) {
                            visited.computeIfAbsent(returnedUrl, k -> {
                                queue.offer(returnedUrl);
                                return true;});
                        }
                    }
                    threadCnt.decrementAndGet();
                });
            }
        }

        return visited.keySet().stream().collect(Collectors.toList());
    }

    private Callable<List<String>> getCallableTask(HtmlParser htmlParser, String url) {
        Callable<List<String>> callableTask = () -> {
            return htmlParser.getUrls(url);
        };
        return callableTask;
    }

    private String getHostName(String url) {
        url = url.substring(7);
        String[] parts = url.split("/");
        return parts[0];
    }

}
