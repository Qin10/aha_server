package cn.hdustea.aha_server.controller.test;

import cn.hdustea.aha_server.annotation.PassAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cygao
 * @date 2021/4/3
 **/
@Slf4j
@RequestMapping(path = "/test")
@RestController
public class TestController {

  @PassAuthentication
  @GetMapping(value = "/1")
  public String test() {
    log.info("Are u ok?");
    return "I'm ready";
  }
}
