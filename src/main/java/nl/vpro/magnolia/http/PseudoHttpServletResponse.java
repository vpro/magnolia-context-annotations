package nl.vpro.magnolia.http;

import info.magnolia.module.site.Site;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.Supplier;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.google.common.annotations.Beta;

/**
 * @author Michiel Meeuwissen
 * @since 1.1
 */
@Beta
public class PseudoHttpServletResponse implements HttpServletResponse {

    private final Supplier<Site> site;

    public PseudoHttpServletResponse(Supplier<Site> site) {
        this.site = site;
    }


    protected Site getSite() {
        return site.get();
    }


    @Override
    public void addCookie(Cookie cookie) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsHeader(String name) {
        return false;
    }

    @Override
    public String encodeURL(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String encodeRedirectURL(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public String encodeUrl(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public String encodeRedirectUrl(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendError(int sc, String msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendError(int sc) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendRedirect(String location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDateHeader(String name, long date) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addDateHeader(String name, long date) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addHeader(String name, String value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addIntHeader(String name, int value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setStatus(int sc) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void setStatus(int sc, String sm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getStatus() {
        return 200;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public Collection<String> getHeaders(String name) {
        return Collections.emptyList();
    }

    @Override
    public Collection<String> getHeaderNames() {
        return Collections.emptyList();
    }

    @Override
    public String getCharacterEncoding() {
        return "UTF-8";
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PrintWriter getWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentLength(int len) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentLengthLong(long len) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setContentType(String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBufferSize(int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
