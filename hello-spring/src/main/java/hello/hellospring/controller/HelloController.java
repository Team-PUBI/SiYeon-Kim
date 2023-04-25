package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적컨텐츠 방식
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute ( "data", "hello!!" );
        return "hello";
    }

    // MVC 방식
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        // @RequestParam = Url?name="넣고 싶은 값."
        model.addAttribute ( "name", name );
        return "hello-template";
    }

    // API 방식
    @GetMapping("hello-string")
    // http의 body에 return 데이터를 직접 넣어주겠다는 의미
    // view가 없어도 됨.
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // ex : hello Spring
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello ();
        hello.setName ( name );

        // JSON 방식으로 표시됨.
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
