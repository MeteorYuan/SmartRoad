package com.hfxy.video;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @program: Vid
 * @description:
 * @author: Mr.Yqy
 * @create: 2019-04-06 01:15
 **/
public class JavaScriptTest {
    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
    }
}