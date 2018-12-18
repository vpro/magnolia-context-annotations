package nl.vpro.magnolia;

import info.magnolia.cms.beans.runtime.MultipartForm;
import info.magnolia.cms.core.AggregationState;
import info.magnolia.context.JCRSessionPerThreadSystemContext;
import info.magnolia.context.SystemContext;
import info.magnolia.context.WebContext;
import info.magnolia.module.site.ExtendedAggregationState;
import info.magnolia.module.site.Site;

import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import nl.vpro.magnolia.http.PseudoHttpServletRequest;
import nl.vpro.magnolia.http.PseudoHttpServletResponse;

/**
 * To work around https://jira.magnolia-cms.com/browse/MGNLIMG-176
 * @author Michiel Meeuwissen
 * @since 1.1
 */
public class SystemWebContext  extends JCRSessionPerThreadSystemContext implements WebContext, SystemContext {

    private PageContext pageContext = null;
    private final HttpServletRequest servletRequest;

    private final HttpServletResponse servletResponse;
    private ServletContext servletContext;

    private final ExtendedAggregationState state;

    public SystemWebContext(@Nonnull Site site) {
        state = new ExtendedAggregationState();
        state.setSite(site);
        this.servletRequest = new PseudoHttpServletRequest(state::getSite);
        this.servletResponse = new PseudoHttpServletResponse(state::getSite);

    }

    @Override
    public void init(
        HttpServletRequest request,
        HttpServletResponse response,
        ServletContext servletContext) {
        throw new IllegalStateException();
    }

    public void mockInit() {
        state.getDomainName();
    }

    @Override
    public AggregationState getAggregationState() {
        return state;

    }

    @Override
    public void resetAggregationState() {

    }

    @Override
    public MultipartForm getPostedForm() {
        return null;

    }

    @Override
    public String getParameter(String name) {
        return servletRequest.getParameter(name);
    }

    @Override
    public Map<String, String> getParameters() {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> paramEnum = servletRequest.getParameterNames();
        while (paramEnum.hasMoreElements()) {
            final String name = paramEnum.nextElement();
            map.put(name, servletRequest.getParameter(name));
        }
        return map;

    }

    @Override
    public String getContextPath() {
        return "";

    }

    @Override
    public HttpServletRequest getRequest() {
        return servletRequest;

    }

    @Override
    public HttpServletResponse getResponse() {
        return servletResponse;

    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;

    }

    @Override
    public void include(String path, Writer out) {
        throw new UnsupportedOperationException();

    }

    @Override
    public PageContext getPageContext() {
        return pageContext;
    }

    @Override
    public void push(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void pop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String[] getParameterValues(String name) {
        return new String[0];

    }

    @Override
    public void setPageContext(PageContext pageContext) {
        this.pageContext = pageContext;

    }
}
