/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.core.controller.derby;

import cl.rworks.comar.core.util.RunSqlScript;
import java.io.InputStream;

/**
 *
 * @author aplik
 */
public class RunSqlScriptTest {

    public static void main(String[] args) throws Exception {
        InputStream is = RunSqlScriptTest.class.getResourceAsStream("/db.sql");
        RunSqlScript runner = new RunSqlScript();
        runner.run(is);
    }
}
