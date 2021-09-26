package j.spring.framework.core.web.handlermapping.annotation;

import com.google.common.collect.ImmutableList;
import j.spring.framework.core.PathMatcher;
import j.spring.framework.core.exception.WebServerErrorCode;
import j.spring.framework.core.exception.WebServerException;
import j.spring.framework.core.ioc.ApplicationContext;
import j.spring.framework.core.web.annotation.RequestMethod;
import j.spring.framework.core.web.handlermapping.AntPatternComparator;
import j.spring.framework.core.web.handlermapping.HandlerExecutionChain;
import j.spring.framework.core.web.handlermapping.HandlerMapping;
import j.spring.framework.core.web.interceptor.HandlerInterceptorManager;
import j.spring.framework.core.web.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class RequestMappingHandlerMapping implements HandlerMapping {

    private final List<RequestMappingMethod> requestMappingMethods;
    private final PathMatcher pathMatcher = new PathMatcher();
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandlerMapping.class);
    private final HandlerInterceptorManager handlerInterceptorManager;

    public RequestMappingHandlerMapping(ApplicationContext applicationContext) {
        RequestMappingAnnotationScanner scanner = new RequestMappingAnnotationScanner(applicationContext);
        List<RequestMappingMethod> methods = scanner.scan();
        checkForDuplicateMapping(methods);
        this.requestMappingMethods = ImmutableList.copyOf(methods);
        this.handlerInterceptorManager = new HandlerInterceptorManager(applicationContext);
    }

    private void checkForDuplicateMapping(List<RequestMappingMethod> requestMappingMethods) {
        Set<CheckForDuplicateRequestMapping> checks = new HashSet<>();
        for (RequestMappingMethod requestMappingMethod : requestMappingMethods) {
            for (RequestMethod requestMethod : requestMappingMethod.getRequestMethods()) {
                checks.add(new CheckForDuplicateRequestMapping(requestMethod, requestMappingMethod.getMappingUrl()));
                logger.debug(requestMethod + " " + requestMappingMethod.getMappingUrl() + " registered ..");
            }
        }
    }

    private static class CheckForDuplicateRequestMapping {
        private final RequestMethod requestMethod;
        private final String mappingUrl;

        CheckForDuplicateRequestMapping(RequestMethod requestMethod, String mappingUrl) {
            this.requestMethod = requestMethod;
            this.mappingUrl = mappingUrl;
        }

        @Override
        public boolean equals(Object o) {
            boolean result = eq(o);
            if (result) {
                throw WebServerException.of(
                        WebServerErrorCode.DUPLICATE_REQUEST_MAPPING, this.toString(), o.toString());
            }
            return false;
        }

        @Override
        public String toString() {
            return requestMethod.name() + " " + mappingUrl;
        }

        private boolean eq(Object o) {
            if (this == o) return true;
            if (!(o instanceof CheckForDuplicateRequestMapping)) return false;
            CheckForDuplicateRequestMapping that = (CheckForDuplicateRequestMapping) o;
            return requestMethod == that.requestMethod && Objects.equals(mappingUrl, that.mappingUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(requestMethod, mappingUrl);
        }
    }

    public RequestMappingMethod find(RequestMethod requestMethod, String requestUrl) {

        List<RequestMappingMethod> matchPaths = new ArrayList<>();

        for (RequestMappingMethod method : requestMappingMethods) {
            if (!method.containsRequestMethod(requestMethod)) {
                continue;
            }
            if (pathMatcher.match(method.getMappingUrl(), requestUrl)) {
                matchPaths.add(method);
                continue;
            }
            if (!method.getMappingUrl().endsWith("/") &&
                    pathMatcher.match(method.getMappingUrl() + "/", requestUrl)) {
                matchPaths.add(method);
            }
        }

        if (CollectionUtils.isNullOrEmpty(matchPaths)) {
            return null;
        }

        AntPatternComparator comparator = new AntPatternComparator(requestUrl);
        matchPaths.sort((a, b) -> comparator.compare(a.getMappingUrl(), b.getMappingUrl()));

        RequestMappingMethod matchedRequestMapping = matchPaths.get(0);

        if (matchPaths.size() > 1) {
            int mappingCompare = comparator.compare(matchedRequestMapping.getMappingUrl(),
                    matchPaths.get(1).getMappingUrl());
            if (mappingCompare == 0) {
                throw WebServerException.of(WebServerErrorCode.AMBIGUOUS_REQUEST_MAPPING,
                        matchedRequestMapping.getInstance().getClass().getName(),
                        matchedRequestMapping.getMethod().getName(),
                        matchedRequestMapping.getMappingUrl());
            }
        }

        return matchPaths.get(0);
    }


    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
        RequestMappingMethod requestMappingMethod =
                find(requestMethod, request.getRequestURI());
        if (requestMappingMethod == null) {
            return null;
        }
        return new HandlerExecutionChain(requestMappingMethod, handlerInterceptorManager);
    }

}
