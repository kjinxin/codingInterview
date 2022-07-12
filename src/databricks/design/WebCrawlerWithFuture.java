package databricks.design;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class WebCrawlerWithFuture {
    public List<String> crawl(String startUrl, HtmlParser htmlParser) {

        Queue<Future<List<String>>> queue = new LinkedList<>();

        ConcurrentHashMap<String, Boolean> visited = new ConcurrentHashMap<>();
        visited.put(startUrl, true);

        String startURLHostName = getHostName(startUrl);

        ExecutorService executor = Executors.newFixedThreadPool( 10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        ExecutorService executorParse = Executors.newFixedThreadPool( 10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        ExecutorService executorSave = Executors.newFixedThreadPool( 10, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });

        Future<List<String>> future = executor.submit(getCallableTask(htmlParser, startUrl));
        queue.offer(future);

        while(true) {

            if(queue.isEmpty()) {
                break;
            }
            try {
                future = queue.poll();
                List<String> returnedUrls = future.get();
                for(String returnedUrl : returnedUrls) {
                    if(getHostName(returnedUrl).equals(startURLHostName)) {
                        visited.computeIfAbsent(returnedUrl, k -> {
                            queue.offer(executor.submit(getCallableTask(htmlParser, returnedUrl)));
                            return true;});
                    }
                }
            } catch(InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

        }

        return visited.keySet().stream().collect(Collectors.toList());
    }

    private Callable<List<String>> getCallableTask(HtmlParser htmlParser, String url) {
        Callable<List<String>> callableTask = () -> {
            // HTML fetch(url)
            // save (url, HTML)
            // parse(html)
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
