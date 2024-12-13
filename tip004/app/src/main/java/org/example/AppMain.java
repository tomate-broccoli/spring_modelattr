package org.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@RestController
@SpringBootApplication
public class AppMain {

    public static class Hoge {
        private Date p1;

        public void setP1(Date p1){
            this.p1 = p1;
        }
        public Date getP1(){
            return this.p1;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // ISO 8601 フォーマットに合わせた SimpleDateFormat
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppMain.class);
        ConfigurableApplicationContext ctx = app.run(args);
    }

    @RequestMapping("/")
    Date home(@ModelAttribute Hoge hoge){
        return hoge.getP1() == null ? new Date() : hoge.getP1();
    }

}
