package com.example.lab4.filter;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;

@Component
public final class ResponseFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws ServletException, IOException {

        ResettableStreamHttpServletRequest wrappedRequest;
        ResettableStreamHttpServletResponse wrappedResponse;

        try{
            wrappedRequest = new ResettableStreamHttpServletRequest((HttpServletRequest) request);
            wrappedResponse = new ResettableStreamHttpServletResponse((HttpServletResponse) response);

            chain.doFilter(wrappedRequest, wrappedResponse);

            if (isXMLotHTMLResponse(wrappedRequest)){

                String[] split = wrappedRequest.getRequestURL().toString().split("/");

                if (split.length > 2){
                    String type = split[split.length - 2];
                    if (Objects.equals(type, "delete")){
                        type = split[split.length - 3];
                    }


                    System.out.println("Applying " + type + " XLS");

                    // In case if we want to serve rendered document
                    Source stylesheetSource = loadXLS("/static/xsl/" + type + ".xsl");
                    String rendered = transformXML(wrappedResponse, stylesheetSource);
                    //rewriteResponse(wrappedResponse, rendered, 200, "application/html");

                    // Serving XML with link to XLS
                    addXSLToResponse(wrappedResponse, type);

                    wrappedResponse.flushBuffer();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new IOException("Can't wrap response or load XLS!");
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new ServletException("Can't apply XLS transformations!");
        }
    }

    public boolean isXMLotHTMLResponse(HttpServletRequest request) {

        if (!request.getRequestURL().toString().contains("api")) return false;

        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);

            if (Objects.equals(key.toLowerCase(), "accept")
                    && value.toLowerCase().contains("application/xml")){
                return true;
            }
        }
        return false;
    }
    public String getResponseBody(ResettableStreamHttpServletResponse response){
        byte[] data = new byte[response.rawData.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = response.rawData.get(i);
        }

        return new String(data);
    }

    public Source loadXLS(String path) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(path);
        return new StreamSource(classPathResource.getFile());
    }

    public void rewriteResponse(ServletResponse response, String responseBody, Integer status, String contentType) throws IOException {
        response.resetBuffer();
        ((HttpServletResponse)response).setStatus(status);
        ((HttpServletResponse)response).setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        response.getOutputStream().print(responseBody);
    }

    public void addXSLToResponse(ResettableStreamHttpServletResponse wrappedResponse, String xslName) throws IOException {
        String xslUrl = "http://localhost:8080/xsl/" + xslName + ".xsl";
        String link = "<?xml-stylesheet type=\"text/xsl\" href=\"" + xslUrl + "\"?>";
        String responseBody = getResponseBody(wrappedResponse);

        rewriteResponse(wrappedResponse, link + "\r\n" + responseBody, 200, "application/xml");
    }

    public String transformXML(ResettableStreamHttpServletResponse wrappedResponse, Source stylesheetSource) throws TransformerException {
        String responseBody = getResponseBody(wrappedResponse);
        Source source = new StreamSource(new ByteArrayInputStream(responseBody.getBytes()));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(stylesheetSource);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(os);
        transformer.transform(source, result);

        return os.toString();
    }

    @Override
    public void destroy() {
    }

}
