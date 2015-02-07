package com.orfr.feedservice;

import com.orfr.feedutil.Feed;
import com.orfr.feedutil.FeedMessage;
import com.orfr.feedutil.RSSFeedParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Sampath Thennakoon
 * @date 07.02.2014
 * @version 0.1
 */
public class FindFeeds {

    /**
     * Returns list of Feed Messages and url argument must specify an
     * absolute {@link com.ofrf.feedservice.main.Main.FEED_ONE}.
     * <p>
     * This method collects the RSS feed data
     *
     * @param url an absolute RSS feed URL
     * @return the list of Feed Messages for given RSS feed
     */

    public List<FeedMessage> findFeeds(String url) {
        List<FeedMessage> feedList = new ArrayList<FeedMessage>();
        try {
            RSSFeedParser parser = new RSSFeedParser(url);
            Feed feed = parser.readFeed();
            Iterator nextItem = feed.getEntries().iterator();
            do {
                if (!nextItem.hasNext()) {
                    break;
                }
                FeedMessage message = (FeedMessage) nextItem.next();
                feedList.add(message);
            } while (true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedList;
    }

}
