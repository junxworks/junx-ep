/*
 ***************************************************************************************
 * EP for web developers.Supported By Junxworks
 * @Title:  HTMLFilter.java   
 * @Package io.github.junxworks.ep.core.security.xss   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2020-7-19 12:18:36   
 * @version V1.0 
 * @Copyright: 2020 Junxworks. All rights reserved. 
 * 注意：
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.security.xss;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {类的详细说明}.
 *
 * @ClassName:  HTMLFilter
 * @author: Michael
 * @date:   2020-7-19 12:18:36
 * @since:  v1.0
 */
public final class HTMLFilter {

    /** 常量 REGEX_FLAGS_SI. */
    private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
    
    /** 常量 P_COMMENTS. */
    private static final Pattern P_COMMENTS = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
    
    /** 常量 P_COMMENT. */
    private static final Pattern P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
    
    /** 常量 P_TAGS. */
    private static final Pattern P_TAGS = Pattern.compile("<(.*?)>", Pattern.DOTALL);
    
    /** 常量 P_END_TAG. */
    private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
    
    /** 常量 P_START_TAG. */
    private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
    
    /** 常量 P_QUOTED_ATTRIBUTES. */
    private static final Pattern P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
    
    /** 常量 P_UNQUOTED_ATTRIBUTES. */
    private static final Pattern P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
    
    /** 常量 P_PROTOCOL. */
    private static final Pattern P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
    
    /** 常量 P_ENTITY. */
    private static final Pattern P_ENTITY = Pattern.compile("&#(\\d+);?");
    
    /** 常量 P_ENTITY_UNICODE. */
    private static final Pattern P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");
    
    /** 常量 P_ENCODE. */
    private static final Pattern P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");
    
    /** 常量 P_VALID_ENTITIES. */
    private static final Pattern P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");
    
    /** 常量 P_VALID_QUOTES. */
    private static final Pattern P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
    
    /** 常量 P_END_ARROW. */
    private static final Pattern P_END_ARROW = Pattern.compile("^>");
    
    /** 常量 P_BODY_TO_END. */
    private static final Pattern P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");
    
    /** 常量 P_XML_CONTENT. */
    private static final Pattern P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");
    
    /** 常量 P_STRAY_LEFT_ARROW. */
    private static final Pattern P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");
    
    /** 常量 P_STRAY_RIGHT_ARROW. */
    private static final Pattern P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");
    
    /** 常量 P_AMP. */
    private static final Pattern P_AMP = Pattern.compile("&");
    
    /** 常量 P_QUOTE. */
    private static final Pattern P_QUOTE = Pattern.compile("<");
    
    /** 常量 P_LEFT_ARROW. */
    private static final Pattern P_LEFT_ARROW = Pattern.compile("<");
    
    /** 常量 P_RIGHT_ARROW. */
    private static final Pattern P_RIGHT_ARROW = Pattern.compile(">");
    
    /** 常量 P_BOTH_ARROWS. */
    private static final Pattern P_BOTH_ARROWS = Pattern.compile("<>");

    /** 常量 P_REMOVE_PAIR_BLANKS. */
    // @xxx could grow large... maybe use sesat's ReferenceMap
    private static final ConcurrentMap<String,Pattern> P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap<String, Pattern>();
    
    /** 常量 P_REMOVE_SELF_BLANKS. */
    private static final ConcurrentMap<String,Pattern> P_REMOVE_SELF_BLANKS = new ConcurrentHashMap<String, Pattern>();

    /** v allowed. */
    private final Map<String, List<String>> vAllowed;
    
    /** v tag counts. */
    private final Map<String, Integer> vTagCounts = new HashMap<String, Integer>();

    /** v self closing tags. */
    private final String[] vSelfClosingTags;
    
    /** v need closing tags. */
    private final String[] vNeedClosingTags;
    
    /** v disallowed. */
    private final String[] vDisallowed;
    
    /** v protocol atts. */
    private final String[] vProtocolAtts;
    
    /** v allowed protocols. */
    private final String[] vAllowedProtocols;
    
    /** v remove blanks. */
    private final String[] vRemoveBlanks;
    
    /** v allowed entities. */
    private final String[] vAllowedEntities;
    
    /** strip comment. */
    private final boolean stripComment;
    
    /** encode quotes. */
    private final boolean encodeQuotes;
    
    /** v debug. */
    private boolean vDebug = false;
    
    /** always make tags. */
    private final boolean alwaysMakeTags;

    /**
     * 构造一个新的 HTML filter 对象.
     */
    public HTMLFilter() {
        vAllowed = new HashMap<>();

        final ArrayList<String> a_atts = new ArrayList<String>();
        a_atts.add("href");
        a_atts.add("target");
        vAllowed.put("a", a_atts);

        final ArrayList<String> img_atts = new ArrayList<String>();
        img_atts.add("src");
        img_atts.add("width");
        img_atts.add("height");
        img_atts.add("alt");
        vAllowed.put("img", img_atts);

        final ArrayList<String> no_atts = new ArrayList<String>();
        vAllowed.put("b", no_atts);
        vAllowed.put("strong", no_atts);
        vAllowed.put("i", no_atts);
        vAllowed.put("em", no_atts);

        vSelfClosingTags = new String[]{"img"};
        vNeedClosingTags = new String[]{"a", "b", "strong", "i", "em"};
        vDisallowed = new String[]{};
        vAllowedProtocols = new String[]{"http", "mailto", "https"}; // no ftp.
        vProtocolAtts = new String[]{"src", "href"};
        vRemoveBlanks = new String[]{"a", "b", "strong", "i", "em"};
        vAllowedEntities = new String[]{"amp", "gt", "lt", "quot"};
        stripComment = true;
        encodeQuotes = true;
        alwaysMakeTags = true;
    }

    /**
     * 构造一个新的 HTML filter 对象.
     *
     * @param debug the debug
     */
    public HTMLFilter(final boolean debug) {
        this();
        vDebug = debug;

    }

    /**
     * 构造一个新的 HTML filter 对象.
     *
     * @param conf the conf
     */
    public HTMLFilter(final Map<String,Object> conf) {

        assert conf.containsKey("vAllowed") : "configuration requires vAllowed";
        assert conf.containsKey("vSelfClosingTags") : "configuration requires vSelfClosingTags";
        assert conf.containsKey("vNeedClosingTags") : "configuration requires vNeedClosingTags";
        assert conf.containsKey("vDisallowed") : "configuration requires vDisallowed";
        assert conf.containsKey("vAllowedProtocols") : "configuration requires vAllowedProtocols";
        assert conf.containsKey("vProtocolAtts") : "configuration requires vProtocolAtts";
        assert conf.containsKey("vRemoveBlanks") : "configuration requires vRemoveBlanks";
        assert conf.containsKey("vAllowedEntities") : "configuration requires vAllowedEntities";

        vAllowed = Collections.unmodifiableMap((HashMap<String, List<String>>) conf.get("vAllowed"));
        vSelfClosingTags = (String[]) conf.get("vSelfClosingTags");
        vNeedClosingTags = (String[]) conf.get("vNeedClosingTags");
        vDisallowed = (String[]) conf.get("vDisallowed");
        vAllowedProtocols = (String[]) conf.get("vAllowedProtocols");
        vProtocolAtts = (String[]) conf.get("vProtocolAtts");
        vRemoveBlanks = (String[]) conf.get("vRemoveBlanks");
        vAllowedEntities = (String[]) conf.get("vAllowedEntities");
        stripComment =  conf.containsKey("stripComment") ? (Boolean) conf.get("stripComment") : true;
        encodeQuotes = conf.containsKey("encodeQuotes") ? (Boolean) conf.get("encodeQuotes") : true;
        alwaysMakeTags = conf.containsKey("alwaysMakeTags") ? (Boolean) conf.get("alwaysMakeTags") : true;
    }

    /**
     * Reset.
     */
    private void reset() {
        vTagCounts.clear();
    }

    /**
     * Debug.
     *
     * @param msg the msg
     */
    private void debug(final String msg) {
        if (vDebug) {
            Logger.getAnonymousLogger().info(msg);
        }
    }

    //---------------------------------------------------------------
    /**
     * Chr.
     *
     * @param decimal the decimal
     * @return the string
     */
    // my versions of some PHP library functions
    public static String chr(final int decimal) {
        return String.valueOf((char) decimal);
    }

    /**
     * Html special chars.
     *
     * @param s the s
     * @return the string
     */
    public static String htmlSpecialChars(final String s) {
        String result = s;
        result = regexReplace(P_AMP, "&amp;", result);
        result = regexReplace(P_QUOTE, "&quot;", result);
        result = regexReplace(P_LEFT_ARROW, "&lt;", result);
        result = regexReplace(P_RIGHT_ARROW, "&gt;", result);
        return result;
    }

    //---------------------------------------------------------------
    /**
     * Filter.
     *
     * @param input the input
     * @return the string
     */
    public String filter(final String input) {
        reset();
        String s = input;

        debug("************************************************");
        debug("              INPUT: " + input);

        s = escapeComments(s);
        debug("     escapeComments: " + s);

        s = balanceHTML(s);
        debug("        balanceHTML: " + s);

        s = checkTags(s);
        debug("          checkTags: " + s);

        s = processRemoveBlanks(s);
        debug("processRemoveBlanks: " + s);

        s = validateEntities(s);
        debug("    validateEntites: " + s);

        debug("************************************************\n\n");
        return s;
    }

    public boolean isAlwaysMakeTags(){
        return alwaysMakeTags;
    }

    public boolean isStripComments(){
        return stripComment;
    }

    /**
     * Escape comments.
     *
     * @param s the s
     * @return the string
     */
    private String escapeComments(final String s) {
        final Matcher m = P_COMMENTS.matcher(s);
        final StringBuffer buf = new StringBuffer();
        if (m.find()) {
            final String match = m.group(1); //(.*?)
            m.appendReplacement(buf, Matcher.quoteReplacement("<!--" + htmlSpecialChars(match) + "-->"));
        }
        m.appendTail(buf);

        return buf.toString();
    }

    /**
     * Balance HTML.
     *
     * @param s the s
     * @return the string
     */
    private String balanceHTML(String s) {
        if (alwaysMakeTags) {
            //
            // try and form html
            //
            s = regexReplace(P_END_ARROW, "", s);
            s = regexReplace(P_BODY_TO_END, "<$1>", s);
            s = regexReplace(P_XML_CONTENT, "$1<$2", s);

        } else {
            //
            // escape stray brackets
            //
            s = regexReplace(P_STRAY_LEFT_ARROW, "&lt;$1", s);
            s = regexReplace(P_STRAY_RIGHT_ARROW, "$1$2&gt;<", s);

            //
            // the last regexp causes '<>' entities to appear
            // (we need to do a lookahead assertion so that the last bracket can
            // be used in the next pass of the regexp)
            //
            s = regexReplace(P_BOTH_ARROWS, "", s);
        }

        return s;
    }

    /**
     * Check tags.
     *
     * @param s the s
     * @return the string
     */
    private String checkTags(String s) {
        Matcher m = P_TAGS.matcher(s);

        final StringBuffer buf = new StringBuffer();
        while (m.find()) {
            String replaceStr = m.group(1);
            replaceStr = processTag(replaceStr);
            m.appendReplacement(buf, Matcher.quoteReplacement(replaceStr));
        }
        m.appendTail(buf);

        s = buf.toString();

        // these get tallied in processTag
        // (remember to reset before subsequent calls to filter method)
        for (String key : vTagCounts.keySet()) {
            for (int ii = 0; ii < vTagCounts.get(key); ii++) {
                s += "</" + key + ">";
            }
        }

        return s;
    }

    /**
     * Process remove blanks.
     *
     * @param s the s
     * @return the string
     */
    private String processRemoveBlanks(final String s) {
        String result = s;
        for (String tag : vRemoveBlanks) {
            if(!P_REMOVE_PAIR_BLANKS.containsKey(tag)){
                P_REMOVE_PAIR_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?></" + tag + ">"));
            }
            result = regexReplace(P_REMOVE_PAIR_BLANKS.get(tag), "", result);
            if(!P_REMOVE_SELF_BLANKS.containsKey(tag)){
                P_REMOVE_SELF_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?/>"));
            }
            result = regexReplace(P_REMOVE_SELF_BLANKS.get(tag), "", result);
        }

        return result;
    }

    /**
     * Regex replace.
     *
     * @param regex_pattern the regex pattern
     * @param replacement the replacement
     * @param s the s
     * @return the string
     */
    private static String regexReplace(final Pattern regex_pattern, final String replacement, final String s) {
        Matcher m = regex_pattern.matcher(s);
        return m.replaceAll(replacement);
    }

    /**
     * Process tag.
     *
     * @param s the s
     * @return the string
     */
    private String processTag(final String s) {
        // ending tags
        Matcher m = P_END_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            if (allowed(name)) {
                if (!inArray(name, vSelfClosingTags)) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) - 1);
                        return "</" + name + ">";
                    }
                }
            }
        }

        // starting tags
        m = P_START_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            final String body = m.group(2);
            String ending = m.group(3);

            //debug( "in a starting tag, name='" + name + "'; body='" + body + "'; ending='" + ending + "'" );
            if (allowed(name)) {
                String params = "";

                final Matcher m2 = P_QUOTED_ATTRIBUTES.matcher(body);
                final Matcher m3 = P_UNQUOTED_ATTRIBUTES.matcher(body);
                final List<String> paramNames = new ArrayList<String>();
                final List<String> paramValues = new ArrayList<String>();
                while (m2.find()) {
                    paramNames.add(m2.group(1)); //([a-z0-9]+)
                    paramValues.add(m2.group(3)); //(.*?)
                }
                while (m3.find()) {
                    paramNames.add(m3.group(1)); //([a-z0-9]+)
                    paramValues.add(m3.group(3)); //([^\"\\s']+)
                }

                String paramName, paramValue;
                for (int ii = 0; ii < paramNames.size(); ii++) {
                    paramName = paramNames.get(ii).toLowerCase();
                    paramValue = paramValues.get(ii);

//          debug( "paramName='" + paramName + "'" );
//          debug( "paramValue='" + paramValue + "'" );
//          debug( "allowed? " + vAllowed.get( name ).contains( paramName ) );

                    if (allowedAttribute(name, paramName)) {
                        if (inArray(paramName, vProtocolAtts)) {
                            paramValue = processParamProtocol(paramValue);
                        }
                        params += " " + paramName + "=\"" + paramValue + "\"";
                    }
                }

                if (inArray(name, vSelfClosingTags)) {
                    ending = " /";
                }

                if (inArray(name, vNeedClosingTags)) {
                    ending = "";
                }

                if (ending == null || ending.length() < 1) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) + 1);
                    } else {
                        vTagCounts.put(name, 1);
                    }
                } else {
                    ending = " /";
                }
                return "<" + name + params + ending + ">";
            } else {
                return "";
            }
        }

        // comments
        m = P_COMMENT.matcher(s);
        if (!stripComment && m.find()) {
            return  "<" + m.group() + ">";
        }

        return "";
    }

    /**
     * Process param protocol.
     *
     * @param s the s
     * @return the string
     */
    private String processParamProtocol(String s) {
        s = decodeEntities(s);
        final Matcher m = P_PROTOCOL.matcher(s);
        if (m.find()) {
            final String protocol = m.group(1);
            if (!inArray(protocol, vAllowedProtocols)) {
                // bad protocol, turn into local anchor link instead
                s = "#" + s.substring(protocol.length() + 1, s.length());
                if (s.startsWith("#//")) {
                    s = "#" + s.substring(3, s.length());
                }
            }
        }

        return s;
    }

    /**
     * Decode entities.
     *
     * @param s the s
     * @return the string
     */
    private String decodeEntities(String s) {
        StringBuffer buf = new StringBuffer();

        Matcher m = P_ENTITY.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.decode(match).intValue();
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENTITY_UNICODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16).intValue();
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENCODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16).intValue();
            m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
        }
        m.appendTail(buf);
        s = buf.toString();

        s = validateEntities(s);
        return s;
    }

    /**
     * Validate entities.
     *
     * @param s the s
     * @return the string
     */
    private String validateEntities(final String s) {
        StringBuffer buf = new StringBuffer();

        // validate entities throughout the string
        Matcher m = P_VALID_ENTITIES.matcher(s);
        while (m.find()) {
            final String one = m.group(1); //([^&;]*)
            final String two = m.group(2); //(?=(;|&|$))
            m.appendReplacement(buf, Matcher.quoteReplacement(checkEntity(one, two)));
        }
        m.appendTail(buf);

        return encodeQuotes(buf.toString());
    }

    /**
     * Encode quotes.
     *
     * @param s the s
     * @return the string
     */
    private String encodeQuotes(final String s){
        if(encodeQuotes){
            StringBuffer buf = new StringBuffer();
            Matcher m = P_VALID_QUOTES.matcher(s);
            while (m.find()) {
                final String one = m.group(1); //(>|^)
                final String two = m.group(2); //([^<]+?)
                final String three = m.group(3); //(<|$)
                m.appendReplacement(buf, Matcher.quoteReplacement(one + regexReplace(P_QUOTE, "&quot;", two) + three));
            }
            m.appendTail(buf);
            return buf.toString();
        }else{
            return s;
        }
    }

    /**
     * Check entity.
     *
     * @param preamble the preamble
     * @param term the term
     * @return the string
     */
    private String checkEntity(final String preamble, final String term) {

        return ";".equals(term) && isValidEntity(preamble)
                ? '&' + preamble
                : "&amp;" + preamble;
    }

    /**
     * 返回布尔值 valid entity.
     *
     * @param entity the entity
     * @return 返回布尔值 valid entity
     */
    private boolean isValidEntity(final String entity) {
        return inArray(entity, vAllowedEntities);
    }

    /**
     * In array.
     *
     * @param s the s
     * @param array the array
     * @return true, if successful
     */
    private static boolean inArray(final String s, final String[] array) {
        for (String item : array) {
            if (item != null && item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Allowed.
     *
     * @param name the name
     * @return true, if successful
     */
    private boolean allowed(final String name) {
        return (vAllowed.isEmpty() || vAllowed.containsKey(name)) && !inArray(name, vDisallowed);
    }

    /**
     * Allowed attribute.
     *
     * @param name the name
     * @param paramName the param name
     * @return true, if successful
     */
    private boolean allowedAttribute(final String name, final String paramName) {
        return allowed(name) && (vAllowed.isEmpty() || vAllowed.get(name).contains(paramName));
    }
}