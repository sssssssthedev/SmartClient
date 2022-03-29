package net.sssssssthedev.SmartClient.annotations.modules;

import net.sssssssthedev.SmartClient.module.Category;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IModule {

    /***
     * Name of the module
     * @return module name
     */
    String name();

    /***
     * Key of the module
     * @return module key
     */
    int key();

    /***
     * Category of the module
     * @return module category
     */
    Category category();

}
