package com.hfxy.video;

import com.hfxy.video.Mapper.ResultMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMapper {
    @Resource
    ResultMapper resultMapper;
    @Test
    public void add(){
        System.out.println(resultMapper.selectCount(1));;
    }
    @Test
    public void Testin(){
        System.out.println(resultMapper.updateCount(1111,1));
    }
}
