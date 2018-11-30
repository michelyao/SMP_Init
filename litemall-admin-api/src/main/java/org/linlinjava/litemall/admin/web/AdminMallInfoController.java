package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.admin.annotation.LoginAdmin;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallMallinfo;
import org.linlinjava.litemall.db.service.LitemallMallInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/mallinfo")
@Validated
public class AdminMallInfoController {
    private final Log logger = LogFactory.getLog(AdminMallInfoController.class);

    @Autowired
    private LitemallMallInfoService mallInfoService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String id, String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallMallinfo> brandList = mallInfoService.querySelective(id, name, page, limit, sort, order);
        int total = mallInfoService.countSelective(id, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", brandList);

        return ResponseUtil.ok(data);
    }

    private Object validate(LitemallMallinfo mallInfo) {
        String name = mallInfo.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String desc = mallInfo.getDesc();
        if (StringUtils.isEmpty(desc)) {
            return ResponseUtil.badArgument();
        }

        Integer iExrate = mallInfo.getExrate();
        if (iExrate == 0){
            return ResponseUtil.badArgument();
        }

        String mallInterface = mallInfo.getMallInterface();
        if (StringUtils.isEmpty(mallInterface)) {
            return ResponseUtil.badArgument();
        }

        String userName = mallInfo.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return ResponseUtil.badArgument();
        }

        String userPassword = mallInfo.getUserPassword();
        if (StringUtils.isEmpty(userPassword)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody LitemallMallinfo mallInfo) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(mallInfo);
        if (error != null) {
            return error;
        }
        mallInfoService.add(mallInfo);
        return ResponseUtil.ok(mallInfo);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, @NotNull Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        LitemallMallinfo brand = mallInfoService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody LitemallMallinfo mallInfo) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(mallInfo);
        if (error != null) {
            return error;
        }
        if (mallInfoService.updateById(mallInfo) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(mallInfo);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody LitemallMallinfo mallInfo) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Integer id = mallInfo.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        mallInfoService.deleteById(id);
        return ResponseUtil.ok();
    }

}
