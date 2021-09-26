package j.spring.framework.core.testdata.web.handlermapping.annotation.resolver.pathvariable;

import j.spring.framework.core.web.annotation.Controller;
import j.spring.framework.core.web.annotation.PathVariable;
import j.spring.framework.core.web.annotation.RequestMapping;

@Controller
public class PathVariableController {

    @RequestMapping(value = "/test")
    public String test() {
        return "/test";
    }


    @RequestMapping(value = "/test/{number}")
    public String pathVariableTest1(@PathVariable int number) {
        return "/test/" + number;
    }

    @RequestMapping(value = "/test/{numberA}/{numberB}")
    public String pathVariableTest2(@PathVariable int numberA, @PathVariable int numberC) {
        return "/test/" + numberA + "/" + numberC;
    }

    @RequestMapping(value = "/test/test")
    public String test3() {
        return "/test/test";
    }

}
