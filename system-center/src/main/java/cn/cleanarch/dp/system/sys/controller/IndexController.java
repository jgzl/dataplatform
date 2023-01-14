package cn.cleanarch.dp.system.sys.controller;

import cn.cleanarch.dp.common.core.model.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
@AllArgsConstructor
@RestController
public class IndexController {

    /**
     * 根节点跳转
     *
     * @return
     */
    @GetMapping("/")
    public R<String> index() {
        return R.success("success");
    }

}