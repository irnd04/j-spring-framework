package j.spring.framework.core.testdata.web.handlermapping.annotation.methodhandler.method;

import j.spring.framework.core.web.annotation.*;

@Controller
public class MethodRequestMappingFinderTestController {

    @RequestMapping("/test")
    public void allMethod() {

    }

    @RequestMapping(value = "/getOrPost", method = { RequestMethod.GET, RequestMethod.POST })
    public void postTest() {

    }

    @RequestMapping(value = "/test/test/1")
    public void testTest1() {

    }

    @RequestMapping(value = "/test/test/{number}")
    public void testTestNumber(@PathVariable int number) {

    }

    @RequestMapping(value = "/gg/{var}")
    public void testAmbiguous(@PathVariable int var) {

    }

    @RequestMapping(value = "/{var2}/gg")
    public void testAmbiguous2(@PathVariable int var2) {

    }

    @RequestMapping(value = "/responsebody")
    @ResponseBody
    public void responseBody() {

    }

}
