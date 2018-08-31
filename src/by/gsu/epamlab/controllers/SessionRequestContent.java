package by.gsu.epamlab.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SessionRequestContent {
    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    public SessionRequestContent() {
        this.requestAttributes = new HashMap<>();
        this.requestParameters = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
    }

    public SessionRequestContent(HttpServletRequest request) {
        this();
        extractValues(request);
    }

    public void extractValues(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        extractSessionAttributes(request);

    }

    public void insertAttributes(HttpServletRequest request) {

    }

    public String[] getParameters(String key) {
        return requestParameters.get(key);
    }

    public String getParameter(String key) {
        String parameter = null;
        if (requestParameters.containsKey(key))
            parameter = requestParameters.get(key)[0];
        return parameter;
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    private void extractSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String attributeName = sessionNames.nextElement();
            sessionAttributes.put(attributeName, session.getAttribute(attributeName));
        }
    }
}
