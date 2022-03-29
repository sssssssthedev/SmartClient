package net.sssssssthedev.SmartClient.annotations.version;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IVersion {

    /***
     * Name of the client
     * @return client name
     */
    String name() default "";

    /***
     * Version of the client
     * @return client version
     */
    String version() default "";

    /***
     * Environment of the client (Production/Development)
     * @return client environment
     */
    String build() default "";

    /***
     * Commit of the client
     * @return client commit
     */
    String commit() default "";

}
