package com.ofrf.feedservice.main;

import com.orfr.feedservice.FindFeeds;
import com.orfr.feedutil.FeedMessage;

import java.util.List;

/**
 * @author Sampath Thennakoon
 * @date 07.02.2014
 * @version 0.1
 */

public class Main {

    /**
     * <p>
     * A Java command line tool , uses the open rss reader to access RSS feeds
     *
     * @param args a user input - no need user inputs
     */

    private static String FEED_ONE = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml";
    private static String FEED_TWO = "http://feeds.reuters.com/reuters/scienceNews?format=xml";

    public static void main(String[] args){
        FindFeeds findFeeds = new FindFeeds();
        List<FeedMessage> feedOneList = findFeeds.findFeeds(FEED_ONE);
        for (FeedMessage feed : feedOneList){
            System.out.println(feed.getTitle() +" - " + feed.getPubDate() +" \n"+feed.getDescription()+"\n\n");
        }
        List<FeedMessage> feedTwoList = findFeeds.findFeeds(FEED_TWO);
        for (FeedMessage feed : feedTwoList){
            System.out.println(feed.getTitle() +" - " + feed.getPubDate() +" \n"+feed.getDescription()+"\n\n");
        }
    }

}