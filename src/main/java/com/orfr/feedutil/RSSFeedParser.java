/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orfr.feedutil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Sampath Thennakoon
 * @date 07.02.2014
 * @version 0.1
 */

public class RSSFeedParser {

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String LAST_BUILD_DATE = "lastBuildDate";
    static final String GUID = "guid";
    private static URL url = null;
    HttpURLConnection urlConn = null;

    /**
     * Returns list of Feed Messages and feedUrl argument must specify an
     * absolute {@link com.ofrf.feedservice.main.Main.FEED_ONE}.
     * <p>
     * This method check the RSS url status
     *
     * @param feedUrl an absolute RSS feed URL
     * @return the list of Feed Messages for given RSS feed
     */

    public RSSFeedParser(String feedUrl) {
        try {
            url = new URL(feedUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(10 * 1000);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a Feed object reading the xml content
     * <p>
     * This method collects the RSS feed data
     *
     * @param
     * @return a feed object
     */

    public Feed readFeed() {
        Feed feed = null;
        try {
            boolean isFeedHeader = true;
            String description = "";
            String title = "";
            String language = "";
            String copyright = "";
            String pubdate = "";
            String lastBuildDate = "";
            String guid = "";
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            do {
                if (!eventReader.hasNext()) {
                    break;
                }
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("item") : "item" == null) {
                        if (isFeedHeader) {
                            isFeedHeader = false;
                            feed = new Feed(title, description, language, copyright, lastBuildDate, guid);
                        }
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("title") : "title" == null) {
                        event = eventReader.nextEvent();
                        title = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("description") : "description" == null) {
                        event = eventReader.nextEvent();
                        description = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("guid") : "guid" == null) {
                        event = eventReader.nextEvent();
                        guid = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("language") : "language" == null) {
                        event = eventReader.nextEvent();
                        language = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("pubDate") : "pubDate" == null) {
                        event = eventReader.nextEvent();
                        pubdate = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("copyright") : "copyright" == null) {
                        event = eventReader.nextEvent();
                        copyright = event.asCharacters().getData();
                    } else if (event.asStartElement().getName().getLocalPart() != null ? event.asStartElement().getName().getLocalPart().equals("lastBuildDate") : "lastBuildDate" == null) {
                        event = eventReader.nextEvent();
                        lastBuildDate = event.asCharacters().getData();
                    }
                } else if (event.isEndElement() && (event.asEndElement().getName().getLocalPart() != null ? event.asEndElement().getName().getLocalPart().equals("item") : "item" == null)) {
                    FeedMessage message = new FeedMessage();
                    message.setDescription(description);
                    message.setGuid(guid);
                    message.setPubDate(pubdate);
                    message.setTitle(title);
                    message.setLastBuild(lastBuildDate);
                    feed.getEntries().add(message);
                }
            } while (true);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        return feed;
    }

    /**
     * Returns url input stream
     * <p>
     * This method read the RSS url
     *
     * @param
     * @return url stream
     */

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
