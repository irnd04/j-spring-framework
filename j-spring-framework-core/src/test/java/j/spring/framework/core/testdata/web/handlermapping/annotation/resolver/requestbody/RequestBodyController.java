package j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.requestbody;

import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.RequestBodyValue;
import j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.RequestBodyValue2;
import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.RequestBody;
import j.spring.framework.core.web.annotation.RequestMapping;

@Controller
@RequestMapping("/rb")
public class RequestBodyController {

    @RequestMapping("/one")
    public void onerb(@RequestBody RequestBodyValue requestBodyValue) {

    }

    @RequestMapping("/two")
    public void tworb(@RequestBody RequestBodyValue requestBodyValue,
                      @RequestBody RequestBodyValue2 requestBodyValue2 ) {

    }

    @RequestMapping("/stream-closed")
    public void streamClosed(@RequestBody RequestBodyValue rbv) {

    }

}
