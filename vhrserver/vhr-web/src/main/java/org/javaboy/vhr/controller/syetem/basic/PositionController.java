package org.javaboy.vhr.controller.syetem.basic;

import org.javaboy.vhr.model.Position;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {
    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if (positionService.addPosition(position) == 1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if (positionService.updatePosition(position) == 1 ) {
            return RespBean.ok("更新成功");
        }
        return RespBean.ok("更新失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteByPositionId(@PathVariable Integer id){
        if (positionService.deleteByPositionId(id) == 1){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        if (positionService.deleteByPositionIds(ids) == ids.length){
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败");
    }

}
